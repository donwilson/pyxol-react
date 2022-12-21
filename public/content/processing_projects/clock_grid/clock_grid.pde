Clock clocks[][];
int clock_size = 60;
int num_ys = 10;
int num_xs = 10;

void setup() {
	size(640, 640);
	
	int offset_x = 2;
	int offset_y = 2;
	int spacer = 4;
	
	clocks = new Clock[ num_ys ][ num_xs ];
	
	for(int y = 0; y < num_ys; y++) {
		int yy = (offset_y + (y * clock_size) + (y * spacer));
		
		for(int x = 0; x < num_xs; x++) {
			int xx = (offset_x + (x * clock_size) + (x * spacer));
			
			clocks[ y ][ x ] = new Clock(xx, yy, clock_size);
		}
	}
}

void draw() {
	background(30);
	
	for(int y = 0; y < num_ys; y++) {
		for(int x = 0; x < num_xs; x++) {
			clocks[ y ][ x ].update();
			clocks[ y ][ x ].draw();
		}
	}
	
	if(frameCount == 50) { save("preview.png"); }
}