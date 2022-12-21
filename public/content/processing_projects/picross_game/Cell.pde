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
	
	void setFilled() {
		filled = true;
		state = Cell.MARKED;   // @TMP
	}
	
	void setFilled(boolean filled_) {
		filled = filled_;
	}
	
	boolean isFilled() {
		return filled;
	}
	
	void setState(int state_) {
		state = state_;
	}
	
	int getState() {
		return state;
	}
	
	boolean isMarked() {
		return (state == Cell.MARKED);
	}
	
	void nextState() {
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
	
	void render() {
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