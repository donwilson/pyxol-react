Blob blob;

float cx;
float cy;

void push() { pushMatrix(); pushStyle(); }
void pop() { popStyle(); popMatrix(); }

color c_bg;
color c_shell;

void setup() {
	colorMode(HSB, 255);
	
	size(640, 640);
	
	cx = (width / 2);
	cy = (height / 2);
	
	c_bg = color(0, 0, 16);
	c_shell = color(255, 0, 255);
	
	blob = new Blob(new PVector(cx, cy), 16);
	blob.setBG(c_bg);
	blob.setShell(c_shell);
}

void draw() {
	background(c_bg);
	
	blob.update();
	blob.render();
	
	// debug: center X
	//debug__CenterX();
	
	//noLoop();
	
	if(frameCount == 50) { save("preview.png"); }
}

void mouseReleased() {
	loop();
}

void debug__CenterX() {
	// draw a red X at the center
	push();
	stroke(color(0, 255, 255));
	noFill();
	line((cx - 10), cy, (cx + 10), cy);
	line(cx, (cy - 10), cx, (cy + 10));
	pop();
}