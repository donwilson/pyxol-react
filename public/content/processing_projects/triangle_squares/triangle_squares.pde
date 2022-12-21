int framesPerRevolution = 90;

float shape_width = 100;
float shape_height = 200;

void setup() {
	size(640, 640);
}

void draw() {
	background(30);
	
	for(int y = (0 - int(shape_height)); y <= (height + int(shape_height)); y += shape_height) {
		for(int x = (0 - int(shape_width)); x <= (width + int(shape_width)); x += shape_width) {
			drawFigure(float(x), float(y));
			
			// offset
			drawFigure(
				(float(x) + (shape_width * 0.5)),
				(float(y) + (shape_height * 0.25))
			);
		}
	}
	
	if(frameCount == 50) { save("preview.png"); }
}

void drawFigure(float offset_x, float offset_y) {
	float center_x = (shape_width * 0.5);
	
	pushMatrix();
	pushStyle();
	
	translate((offset_x + center_x), offset_y);
	
	noStroke();
	//fill(color(255, 255, 255));
	fill(color(map(offset_x, 0, width, 0, 255), map(offset_y, 0, height, 0, 255), 255));
	
	beginShape();
	
	vertex(0, 0);
	
	float at_pct = map((frameCount % framesPerRevolution), 0, framesPerRevolution, 0, 2);
	
	if(at_pct > 1) {
		//at_pct = (2 - at_pct);
		at_pct = (1 - (at_pct - 1));
	}
	
	
	float easing_amt = 1;
	
	//if(at_pct > 0.5) {
	//	easing_amt = Easing.easeInCubic(((at_pct - 0.5) * 2));
	//} else {
	//	easing_amt = Easing.easeInCubic((at_pct * 2));
	//}
	
	vertex(
		((0 - center_x) + (shape_width * at_pct * easing_amt)),
		(shape_height * 0.25)
	);
	
	// midpoint
	vertex(0, (shape_height * 0.5));
	
	vertex(
		(center_x - (shape_width * at_pct * easing_amt)),
		(shape_height * 0.75)
	);
	
	
	vertex(0, shape_height);
	
	endShape(CLOSE);
	
	popStyle();
	popMatrix();
}