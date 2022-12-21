float aa = 0.0;

void setup() {
	size(640, 640);
	colorMode(HSB, 100);
}

void draw() {
	background(10);
	
	float margin = 20;
	
	int num_dots = 11;
	float spacing = ((width - (margin + margin)) / (num_dots - 1));
	
	float inner_width = (width - (margin + margin));
	
	for(int i = 0; i < num_dots; i++) {
		float x = (((margin + (spacing * i)) + aa) % inner_width);
		
		color col = color(map(i, 0, (num_dots - 1), 0, 100), 100, 100);
		
		stroke(col);
		strokeWeight(2);
		
		for(int j = 0; j < num_dots; j++) {
			float y = ((margin + (spacing * j)) - aa);
			
			while(y < margin) {
				y += inner_width;
			}
			
			line(
				(x + margin),
				margin,
				y,
				(height - margin)
			);
		}
	}
	
	// ellipseMode(CENTER);
	// fill(11, 100, 100);
	// noStroke();
	// for(int k = 0; k < num_dots; k++) {
	// 	ellipse((margin + (spacing * k)), margin, 4.0, 4.0);
	// 	ellipse((margin + (spacing * k)), (height - margin), 4.0, 4.0);
	// }
	
	aa += 1;
	
	if(frameCount == 50) { save("preview.png"); }
}