

void setup() {
	size(640, 640);
	background(255);
	
	createFern(640, 640);
	
	save("preview.png");
}

void createFern(int w, int h) {
	float x = 0;
	float y = 0;
	
	loadPixels();
	
	for(int i = 0; i < 200000; i++) {
		float tmpx, tmpy;
		float r = random(1);
		
		if(r < 0.01) {
			tmpx = 0;
			tmpy = (0.16 * y);
		} else if(r <= 0.08) {
			tmpx = 0.2 * x - 0.26 * y;
			tmpy = 0.23 * x + 0.22 * y + 1.6;
		} else if(r <= 0.15) {
			tmpx = -0.15 * x + 0.28 * y;
			tmpy = 0.26 * x + 0.24 * y + 0.44;
		} else {
			tmpx = 0.85 * x + 0.04 * y;
			tmpy = -0.04 * x + 0.85 * y + 1.6;
		}
		
		x = tmpx;
		y = tmpy;
		
		int px = floor(w / 2 + x * w / 11);
		int py = floor(h - y * h / 11);
		
		int index = (px + (py * w));
		
		pixels[ index ] = color(0, 255, 0);
	}
	
	updatePixels();
}