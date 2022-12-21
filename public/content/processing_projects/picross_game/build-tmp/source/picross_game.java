import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class picross_game extends PApplet {

Table table;

boolean scaling_enabled = false;
float current_scale = 1.0f;
float scale_step = 0.9f;

boolean dragging_enabled = false;
PVector drag_offset;

int cell_width = 32;
int cell_height = 32;
int cell_border_size = 1;
float cell_padding = 4.0f;
float crossed_line_size = 3.0f;

PFont hint_font;

int hint_font_size = 24;
int divider_line_size = 3;

int color_bg = color(30);
int color_border = color(0, 0, 0);
int color_hint_bg = color(233, 227, 178);
int color_hint_text = color(0, 0, 0);
int color_cell_bg = color(255, 255, 255);
int color_cell_fill = color(0, 0, 0);
int color_divider_line = color(0, 0, 0);

public void setup() {
	//fullScreen(SPAN);
	//fullScreen();
	
	surface.setResizable(true);
	
	hint_font = createFont("Roboto", hint_font_size);
	
	// load table
	//table = new Table("house.json");
	table = new Table("swan.json");
	//table = new Table("swan-reverse.json");
	
	// draw offsets
	drag_offset = new PVector();
	
	surface.setSize(table.getWidth(), table.getHeight());
}

public void draw() {
	background(color_bg);
	
	if(scaling_enabled) {
		scale(current_scale);
	}
	
	if(scaling_enabled) {
		// not perfect
		translate(
			((width / 2) - (((table.getWidth() * current_scale) / 2) + drag_offset.x)),
			((height / 2) - (((table.getHeight() * current_scale) / 2) + drag_offset.y))
		);
	} else {
		translate(
			((width / 2) - (table.getWidth() / 2) + drag_offset.x),
			((height / 2) - (table.getHeight() / 2) + drag_offset.y)
		);
	}
	
	table.render();
	
	if(frameCount == 50) { save("preview.png"); }
}

public void mouseWheel(MouseEvent event) {
	if(!scaling_enabled) {
		return;
	}
	
	float amt = event.getCount();
	
	if(amt < 0) {
		// scrolled up
		current_scale *= 1.1f;
	} else {
		// scrolled down
		current_scale *= 0.9f;
	}
}

public void mouseDragged() {
	if(!dragging_enabled) {
		return;
	}
	
	int diff_x = (mouseX - pmouseX);
	int diff_y = (mouseY - pmouseY);
	
	drag_offset.add(diff_x, diff_y);
}
class Cell {
	final static int BLANK = 0;
	final static int MARKED = 1;
	final static int CROSSED_OUT = 2;
	
	int col;
	int row;
	
	boolean filled = false;
	int state = Cell.BLANK;   // which state the cell is marked as
	
	Cell(int col_, int row_) {
		col = col_;
		row = row_;
		
		state = Cell.CROSSED_OUT;
	}
	
	public void setFilled() {
		filled = true;
		state = Cell.MARKED;   // @TMP
	}
	
	public void setFilled(boolean filled_) {
		filled = filled_;
	}
	
	public boolean isFilled() {
		return filled;
	}
	
	public void setState(int state_) {
		state = state_;
	}
	
	public int getState() {
		return state;
	}
	
	public boolean isMarked() {
		return (state == Cell.MARKED);
	}
	
	public void nextState() {
		// set the state as the next state in the list
		if(state == Cell.BLANK) {
			state = Cell.MARKED;
		} else if(state == Cell.MARKED) {
			state = Cell.CROSSED_OUT;
		} else {
			// loop back
			state = Cell.BLANK;
		}
	}
	
	public void render() {
		pushMatrix();
		pushStyle();
		
		translate(
			(col * cell_width),
			(row * cell_height)
		);
		
		stroke(0);
		strokeWeight(cell_border_size);
		fill(color_cell_bg);
		rect(0, 0, cell_width, cell_height);
		
		if(state == Cell.MARKED) {
			// marked
			fill(color_cell_fill);
			rect(cell_padding, cell_padding, (cell_width - (cell_padding * 2)), (cell_height - (cell_padding * 2)));
		} else if(state == Cell.CROSSED_OUT) {
			// crossed out
			pushStyle();
			
			stroke(color_cell_fill);
			strokeWeight(crossed_line_size);
			noFill();
			
			// offset padding for line
			float line_offset = (cell_padding + (crossed_line_size / 2));
			
			// top left to bottom right
			line(
				line_offset,
				line_offset,
				(cell_width - line_offset),
				(cell_height - line_offset)
			);
			
			// bottom left to top right
			line(
				line_offset,
				(cell_height - line_offset),
				(cell_width - line_offset),
				line_offset
			);
			
			popStyle();
		}
		
		popStyle();
		popMatrix();
	}
}
class Hint {
	final static int TOP = 0;
	final static int LEFT = 1;
	
	int type;
	int col;
	int row;
	String hint;
	
	Hint(int type_, int col_, int row_, String hint_) {
		type = type_;
		col = col_;
		row = row_;
		hint = hint_;
	}
	
	public void render() {
		pushMatrix();
		pushStyle();
		
		translate(
			(col * cell_width),
			(row * cell_height)
		);
		
		stroke(0);
		strokeWeight(cell_border_size);
		fill(color_hint_bg);
		rect(0, 0, cell_width, cell_height);
		
		// display hint (if any)
		if(!hint.equals("")) {
			pushMatrix();
			pushStyle();
			
			translate(cell_border_size, cell_border_size);
			
			fill(color_hint_text);
			noStroke();
			textFont(hint_font);
			textSize(hint_font_size);
			textAlign(CENTER, BOTTOM);
			
			text(hint, (cell_width / 2), cell_height);
			
			popStyle();
			popMatrix();
		}
		
		popStyle();
		popMatrix();
	}
}
class Table {
	String name;
	int w;
	int h;
	
	Cell[][] cells;
	
	Hint[][] hints_top;
	Hint[][] hints_left;
	
	int max_hints_top = 0;   // max number of hint rows that show at the top
	int max_hints_left = 0;   // max number of hint columns that show on the left
	
	Table(String json_file) {
		loadJSON(json_file);
		buildStructure();
	}
	
	public void loadJSON(String file) {
		JSONObject data = loadJSONObject(file);
		
		JSONObject table_settings = data.getJSONObject("table");
		name = table_settings.getString("name");
		w = table_settings.getInt("width");
		h = table_settings.getInt("height");
		
		// fill table with empty data
		cells = new Cell[h][w];
		
		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x++) {
				cells[ y ][ x ] = new Cell(x, y);
			}
		}
		
		// load actual table data
		JSONArray table_rows = data.getJSONArray("data");
		
		for(int y = 0; y < table_rows.size(); y++) {
			if(y >= h) {
				break;
			}
			
			String row_data = table_rows.getString(y);
			
			for(int x = 0; x < row_data.length(); x++) {
				if(x >= w) {
					break;
				}
				
				if(row_data.substring(x, (x + 1)).equals("1")) {
					cells[ y ][ x ].setFilled();
				}
			}
		}
	}
	
	public void buildStructure() {
		// get column counts
		ArrayList<String> column_counts = new ArrayList<String>();
		
		for(int x = 0; x < w; x++) {
			String count_data = "";
			int running_tally = 0;
			int num_hints = 0;
			
			for(int y = 0; y < h; y++) {
				Cell cell = cells[ y ][ x ];
				
				if(cell.isFilled()) {
					// increment running tally
					running_tally++;
					
					continue;
				}
				
				// not filled
				if(running_tally == 0) {
					// no running tally, just go on
					continue;
				}
				
				if(!count_data.equals("")) {
					count_data += ",";
				}
				
				count_data += running_tally;
				
				running_tally = 0;
				num_hints++;
			}
			
			// trailing tally
			if(running_tally > 0) {
				if(!count_data.equals("")) {
					count_data += ",";
				}
				
				count_data += running_tally;
				num_hints++;
			}
			
			column_counts.add(count_data);
			
			if(num_hints > max_hints_top) {
				max_hints_top = num_hints;
			}
		}
		
		// build hints_top
		hints_top = new Hint[ max_hints_top ][ w ];
		
		for(int x = 0; x < w; x++) {
			String raw_data = column_counts.get(x);
			
			String[] bits = raw_data.split(",");
			
			int offset = (max_hints_top - bits.length);
			
			for(int y = 0; y < max_hints_top; y++) {
				if(y < offset) {
					// empty hint
					hints_top[ y ][ x ] = new Hint(Hint.TOP, x, y, "");
					
					continue;
				}
				
				hints_top[ y ][ x ] = new Hint(Hint.TOP, x, y, bits[ (y - offset) ]);
			}
		}
		
		// get row counts
		ArrayList<String> row_counts = new ArrayList<String>();
		
		for(int y = 0; y < h; y++) {
			String count_data = "";
			int running_tally = 0;
			int num_hints = 0;
			
			for(int x = 0; x < w; x++) {
				Cell cell = cells[ y ][ x ];
				
				if(cell.isFilled()) {
					// increment running tally
					running_tally++;
					
					continue;
				}
				
				// not filled
				if(running_tally == 0) {
					// no running tally, just go on
					continue;
				}
				
				if(!count_data.equals("")) {
					count_data += ",";
				}
				
				count_data += running_tally;
				
				running_tally = 0;
				num_hints++;
			}
			
			// trailing tally
			if(running_tally > 0) {
				if(!count_data.equals("")) {
					count_data += ",";
				}
				
				count_data += running_tally;
				num_hints++;
			}
			
			row_counts.add(count_data);
			
			if(num_hints > max_hints_left) {
				max_hints_left = num_hints;
			}
		}
		
		// build hints_left
		hints_left = new Hint[ h ][ max_hints_left ];
		
		for(int y = 0; y < h; y++) {
			String raw_data = row_counts.get(y);
			
			String[] bits = raw_data.split(",");
			
			int offset = (max_hints_left - bits.length);
			
			for(int x = 0; x < max_hints_left; x++) {
				if(x < offset) {
					// empty hint
					hints_left[ y ][ x ] = new Hint(Hint.LEFT, x, y, "");
					
					continue;
				}
				
				hints_left[ y ][ x ] = new Hint(Hint.LEFT, x, y, bits[ (x - offset) ]);
			}
		}
	}
	
	public int getWidth() {
		return ((max_hints_left * cell_width) + (w * cell_width));
	}
	
	public int getHeight() {
		return ((max_hints_top * cell_height) + (h * cell_height));
	}
	
	public void render() {
		render_preview();
		render_top_hints();
		render_left_hints();
		render_cells();
		render_dividing_lines();
	}
	
	public void render_preview() {
		// preview in top left of existing filled in data
		
		float preview_width = ((cell_width * cells[0].length));
		float preview_height = ((cell_height * cells.length));
		
		PGraphics preview = createGraphics(PApplet.parseInt(preview_width), PApplet.parseInt(preview_height));
		
		preview.beginDraw();
		
		
		// bg
		preview.fill(color_cell_bg);
		preview.noStroke();
		
		preview.rect(0, 0, preview_width, preview_height);
		
		
		// cell previews
		preview.fill(color_cell_fill);
		preview.noStroke();
		
		for(int j = 0; j < cells.length; j++) {
			for(int k = 0; k < cells[ j ].length; k++) {
				if(cells[ j ][ k ].isMarked()) {
					preview.rect(
						(cell_width * k),
						(cell_height * j),
						cell_width,
						cell_height
					);
				}
			}
		}
		
		preview.endDraw();
		
		
		// render preview
		pushMatrix();
		pushStyle();
		
		float preview_container_width = ((cell_width * hints_left[0].length) - (divider_line_size / 2));
		float preview_container_height = ((cell_height * hints_top.length) - (divider_line_size / 2));
		
		float preview_border_size = 4.0f;
		
		float max_sized_preview_width = preview_container_width;
		float max_sized_preview_height = preview_container_height;
		
		float sized_preview_width = 0;
		float sized_preview_height = 0;
		
		if(preview_height > preview_width) {
			// portrait, center x
			sized_preview_height = ((max_sized_preview_height * (preview_container_width / preview_container_height)) - (preview_border_size * 2));
			sized_preview_width = (preview_width * (sized_preview_height / preview_width));
		} else {
			// landscape or equal
			sized_preview_width = ((max_sized_preview_width * (preview_container_height / preview_container_width)) - (preview_border_size * 2));
			sized_preview_height = (preview_height * (sized_preview_width / preview_height));
		}
		
		translate(0, 0);
		
		// bg
		fill(color_cell_bg);
		noStroke();
		
		rect(0, 0, preview_container_width, preview_container_height);
		
		translate((preview_container_width / 2), (preview_container_height / 2));
		
		// preview
		image(
			preview,
			(-sized_preview_width / 2),
			(-sized_preview_height / 2),
			sized_preview_width,
			sized_preview_height
		);
		
		popStyle();
		popMatrix();
	}
	
	public void render_top_hints() {
		int offset = (max_hints_left * cell_width);
		
		pushMatrix();
		
		translate(offset, 0);
		
		for(int j = 0; j < hints_top.length; j++) {
			for(int k = 0; k < hints_top[ j ].length; k++) {
				hints_top[ j ][ k ].render();
			}
		}
		
		popMatrix();
	}
	
	public void render_left_hints() {
		int offset = (max_hints_top * cell_height);
		
		pushMatrix();
		
		translate(0, offset);
		
		for(int j = 0; j < hints_left.length; j++) {
			for(int k = 0; k < hints_left[ j ].length; k++) {
				hints_left[ j ][ k ].render();
			}
		}
		
		popMatrix();
	}
	
	public void render_cells() {
		int offset_x = (max_hints_left * cell_width);
		int offset_y = (max_hints_top * cell_height);
		
		pushMatrix();
		
		translate(offset_x, offset_y);
		
		for(int j = 0; j < cells.length; j++) {
			for(int k = 0; k < cells[ j ].length; k++) {
				cells[ j ][ k ].render();
			}
		}
		
		popMatrix();
	}
	
	public void render_dividing_lines() {
		int num_top_hints = hints_top.length;
		int num_left_hints = hints_left[0].length;
		int num_vertical_cells = cells.length;
		int num_horizontal_cells = cells[0].length;
		
		
		// horizontal lines
		int top_hint_offset = (cell_height * num_top_hints);
		int num_horizontal_lines = (1 + floor( (num_vertical_cells / 5) ));
		
		pushStyle();
		
		fill(color_divider_line);
		stroke(color_divider_line);
		strokeWeight(divider_line_size);
		
		for(int i = 0; i < num_horizontal_lines; i++) {
			int line_offset = (top_hint_offset + ((cell_height * 5) * i));
			
			line(0, line_offset, (cell_width * (num_left_hints + num_horizontal_cells)), line_offset);
		}
		
		popStyle();
		
		// vertical lines
		int left_hint_offset = (cell_width * num_left_hints);
		int num_vertical_lines = (1 + floor( (num_horizontal_cells / 5) ));
		
		pushStyle();
		
		fill(color_divider_line);
		stroke(color_divider_line);
		strokeWeight(divider_line_size);
		
		for(int i = 0; i < num_horizontal_lines; i++) {
			int line_offset = (left_hint_offset + ((cell_width * 5) * i));
			
			line(line_offset, 0, line_offset, (cell_height * (num_top_hints + num_vertical_cells)));
		}
		
		popStyle();
	}
}
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "picross_game" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
