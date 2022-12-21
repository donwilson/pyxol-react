float aa = 0.0;

void setup() {
	size(640, 640);
	colorMode(HSB, 100);
}

void draw() {
	loadPixels();
	
	for(int y = 0; y < height; y++) {
		for(int x = 0; x < width; x++) {
			int index = (x + (y * width));
			
			pixels[ index ] = getPixelColor(x, y);
		}
	}
	
	updatePixels();
	
	aa += 1;
	
	if(frameCount == 50) { save("preview.png"); }
}

color getPixelColor(int x, int y) {
	int pointX = mouseX;
	int pointY = mouseY;
	
	float distance = dist(x, y, pointX, pointY);
	//float max_distance = dist(0, 0, (width / 2), (height / 2));
	//return color(map(distance, 0, max_distance, 0, 100), 100, 100);
	
	return color(
		//(abs(distance - aa) % 100),
		min(100, max(0, distance)),
		100,
		100
	);
}