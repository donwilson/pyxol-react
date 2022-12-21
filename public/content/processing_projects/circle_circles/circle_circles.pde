PVector c1;
PVector c2;

float aa = 0.0;
float bb = 0.0;

float r_c1 = 200;
float r_c2 = (r_c1 / 2);



void setup() {
	size(640, 640);
	background(30);
	
	c1 = new PVector();
	c2 = new PVector();
}

void draw() {
	//background(30, 100);
	
	translate((width / 2), (height / 2));
	
	stroke(255);
	strokeWeight(2);
	noFill();
	beginShape();
	
	for(int i = 0; i < 10; i++) {
		c1.x = (cos(aa) * r_c1);
		c1.y = (sin(aa) * r_c1);
		
		c2.x = (c1.x + (cos(bb) * r_c2));
		c2.y = (c1.y + (sin(bb) * r_c2));
		
		
		curveVertex(c2.x, c2.y);
		
		bb += 0.01;
		bb %= 360;
	}
	
	endShape();
	
	aa += 0.02;
	
	aa %= 360;
	
	if(frameCount == 150) { save("preview.png"); }
}