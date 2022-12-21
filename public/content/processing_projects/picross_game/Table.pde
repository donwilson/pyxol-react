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
	
	void loadJSON(String file) {
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
	
	void buildStructure() {
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
	
	int getWidth() {
		return ((max_hints_left * cell_width) + (w * cell_width));
	}
	
	int getHeight() {
		return ((max_hints_top * cell_height) + (h * cell_height));
	}
	
	void render() {
		render_preview();
		render_top_hints();
		render_left_hints();
		render_cells();
		render_dividing_lines();
	}
	
	void render_preview() {
		// preview in top left of existing filled in data
		
		float preview_width = ((cell_width * cells[0].length));
		float preview_height = ((cell_height * cells.length));
		
		PGraphics preview = createGraphics(int(preview_width), int(preview_height));
		
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
		
		float preview_border_size = 4.0;
		
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
	
	void render_top_hints() {
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
	
	void render_left_hints() {
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
	
	void render_cells() {
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
	
	void render_dividing_lines() {
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