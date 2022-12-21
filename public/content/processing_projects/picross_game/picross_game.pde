Table table;

boolean scaling_enabled = false;
float current_scale = 1.0;
float scale_step = 0.9;

boolean dragging_enabled = false;
PVector drag_offset;

int cell_width = 32;
int cell_height = 32;
int cell_border_size = 1;
float cell_padding = 4.0;
float crossed_line_size = 3.0;

PFont hint_font;

int hint_font_size = 24;
int divider_line_size = 3;

color color_bg = color(30);
color color_border = color(0, 0, 0);
color color_hint_bg = color(233, 227, 178);
color color_hint_text = color(0, 0, 0);
color color_cell_bg = color(255, 255, 255);
color color_cell_fill = color(0, 0, 0);
color color_divider_line = color(0, 0, 0);

void setup() {
	//fullScreen(SPAN);
	//fullScreen();
	size(640, 640);
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

void draw() {
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

void mouseWheel(MouseEvent event) {
	if(!scaling_enabled) {
		return;
	}
	
	float amt = event.getCount();
	
	if(amt < 0) {
		// scrolled up
		current_scale *= 1.1;
	} else {
		// scrolled down
		current_scale *= 0.9;
	}
}

void mouseDragged() {
	if(!dragging_enabled) {
		return;
	}
	
	int diff_x = (mouseX - pmouseX);
	int diff_y = (mouseY - pmouseY);
	
	drag_offset.add(diff_x, diff_y);
}