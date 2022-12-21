

void setup() {
	size(640, 640);
}

void draw() {
	background(0);
	
	for(int xx = 0; xx < 10; xx++) {
		for(int yy = 0; yy < 10; yy++) {
			float x = (float)xx;
			float y = (float)yy;
			
			drawPixel((width / 2) + (x * 30), (height / 2) + (y * 30), 10, random(0, 255), random(0, 255), random(0, 255));
		}
	}
	
	if(frameCount == 50) { save("preview.png"); }
}


void drawPixel(float x, float y, float scl, float r, float g, float b) {
	push();
	translate(x, y);
	//scale(scl);
	
	float yoff = 0;
	
	// r
	push();
	noStroke();
	fill(color(r, 0, 0));
	rect(0, 0, (1 * scl), (1 * scl));
	rect((1 * scl), 0, (1 * scl), (1 * scl));
	yoff += (1 * scl);
	pop();
	
	// g
	push();
	noStroke();
	fill(color(0, g, 0));
	rect(0, yoff, (1 * scl), (1 * scl));
	rect((1 * scl), yoff, (1 * scl), (1 * scl));
	yoff += (1 * scl);
	pop();
	
	// b
	push();
	noStroke();
	fill(color(0, 0, b));
	rect(0, yoff, (1 * scl), (1 * scl));
	rect((1 * scl), yoff, (1 * scl), (1 * scl));
	yoff += (1 * scl);
	pop();
	
	
	
	pop();
}



void push() { pushMatrix(); pushStyle(); }
void pop() { popStyle(); popMatrix(); }