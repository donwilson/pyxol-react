Liner[] liner;

void setup() {
	size(640, 640);
	
	liner = new Liner[48];
	
	for(int i = 0; i < liner.length; i++) {
		liner[ i ] = new Liner();
	}
}

void draw() {
	background(30);
	
	for(int i = 0; i < liner.length; i++) {
		liner[ i ].update();
		liner[ i ].draw();
	}
	
	for(int i = 0; i < liner.length; i++) {
		liner[ i ].drawForeground();
	}
	
	if(frameCount == 50) { save("preview.png"); }
}