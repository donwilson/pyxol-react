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
	
	void render() {
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