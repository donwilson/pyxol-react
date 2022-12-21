float[][] current;
float[][] previous;

float dampening = 0.95;

void setup() {
	size(600, 600);
	
	current = new float[ width ][ height ];
	previous = new float[ width ][ height ];
	
	// for(int x = 1; x < (width - 1); x++) {
	// 	for(int y = 1; y < (height - 1); y++) {
	// 		current[x][y] = 100;
	// 		previous[x][y] = 100;
	// 	}
	// }
	
	current[100][100] = 255;
}

void mouseDragged() {
	//previous[ mouseX ][ mouseY ] = 255;
	
	if((mouseX > 0) && (mouseX < width) && (mouseY > 0) && (mouseY < height)) {
		current[ (mouseX - 1) ][ (mouseY - 1) ] = 255;
	}
}

void mousePressed() {
	//previous[ mouseX ][ mouseY ] = 255;
	
	if((mouseX > 0) && (mouseX < width) && (mouseY > 0) && (mouseY < height)) {
		current[ (mouseX - 1) ][ (mouseY - 1) ] = 255;
	}
}

void draw() {
	background(0);
	loadPixels();
	
	if(random(0, 1) <= 0.2) {
		current[ floor(random((width - 2))) ][ floor(random((height - 2))) ] = 255;
	}
	
	for(int x = 1; x < (width - 1); x++) {
		for(int y = 1; y < (height - 1); y++) {
			current[x][y] = (previous[ x - 1 ][ y ] + previous[ x + 1 ][ y ] + previous[ x ][ y - 1 ] + previous[ x ][ y + 1 ]) / 2 - current[ x ][ y ];
			
			current[x][y] = (current[x][y] * dampening);
			
			int index = (x + (y * width));
			
			//pixels[ index ] = color(0, 0, (255 * current[x][y]));
			pixels[ index ] = color(0, 0, map((255 * current[x][y]), 0, 255, 200, 255));
		}
	}
	
	updatePixels();
	
	float[][] temp = previous;
	previous = current;
	current = temp;
	
	if(frameCount == 150) { save("preview.png"); }
}