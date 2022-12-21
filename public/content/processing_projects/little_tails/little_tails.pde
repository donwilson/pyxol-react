Tail tails;

void setup() {
	size(640, 640);
	tails = new Tail();
}

void draw() {
	background(30);
	
	tails.update();
	tails.draw();
	
	if(frameCount == 350) { save("preview.png"); }
}