float dA = 1.0;
float dB = 0.5;
float feed = 0.055;
float k = 0.062;

Cell[][] grid;
Cell[][] prev;

void setup() {
	size(320, 320);
	
	colorMode(HSB, 100);
	
	grid = new Cell[height][width];
	prev = new Cell[height][width];
	
	for(int y = 0; y < height; y++) {
		for(int x = 0; x < height; x++) {
			float a = 1;
			float b = 0;
			
			grid[ y ][ x ] = new Cell(a, b);
			prev[ y ][ x ] = new Cell(a, b);
		}
	}
	
	float a = 1;
	float b = 1;
	
	for(int n = 0; n < 10; n++) {
		// top left
		grid[ (10 + n) ][ (20 + n) ] = new Cell(a, b);
		prev[ (10 + n) ][ (20 + n) ] = new Cell(a, b);
		
		// top right
		grid[ ((width - 20) + n) ][ (20 + n) ] = new Cell(a, b);
		prev[ ((width - 20) + n) ][ (20 + n) ] = new Cell(a, b);
		
		// bottom left
		grid[ (10 + n) ][ ((height - 20) + n) ] = new Cell(a, b);
		prev[ (10 + n) ][ ((height - 20) + n) ] = new Cell(a, b);
		
		// bottom right
		grid[ ((width - 20) + n) ][ ((height - 20) + n) ] = new Cell(a, b);
		prev[ ((width - 20) + n) ][ ((height - 20) + n) ] = new Cell(a, b);
	}
	
	/*for(int n = 0; n < 25; n++) {
		int startx = int(random(20, (width - 20)));
		int starty = int(random(20, (height - 20)));
		
		for(int y = starty; y < starty + 10; y++) {
			for(int x = startx; x < startx + 10; x++) {
				float a = 1;
				float b = 1;
				
				grid[ y ][ x ] = new Cell(a, b);
				prev[ y ][ x ] = new Cell(a, b);
			}
		}
	}*/
}

void draw() {
	background(255);
	
	println(frameRate);
	
	for(int i = 0; i < 10; i++) {
		update();
		swap();
	}
	
	loadPixels();
	
	for(int y = 1; y < (height - 1); y++) {
		for(int x = 1; x < (width - 1); x++) {
			Cell spot = grid[ y ][ x ];
			
			float a = spot.a;
			float b = spot.b;
			
			int pos = (x + (y * width));
			
			pixels[ pos ] = color((a - b) * 100, 100, 100);
		}
	}
	
	updatePixels();
	
	if(frameCount == 450) { save("preview.png"); }
}



void update() {
	for(int y = 1; y < (height - 1); y++) {
		for(int x = 1; x < (width - 1); x++) {
			Cell spot = prev[ y ][ x ];
			Cell newspot = grid[ y ][ x ];
			
			float a = spot.a;
			float b = spot.b;
			
			float laplaceA = 0;
			laplaceA += (a * -1);
			laplaceA += (prev[ y ][ (x + 1) ].a * 0.2);
			laplaceA += (prev[ y ][ (x - 1) ].a * 0.2);
			laplaceA += (prev[ (y + 1) ][ x ].a * 0.2);
			laplaceA += (prev[ (y - 1) ][ x ].a * 0.2);
			laplaceA += (prev[ (y - 1) ][ (x - 1) ].a * 0.05);
			laplaceA += (prev[ (y - 1) ][ (x + 1) ].a * 0.05);
			laplaceA += (prev[ (y + 1) ][ (x - 1) ].a * 0.05);
			laplaceA += (prev[ (y + 1) ][ (x + 1) ].a * 0.05);
			
			float laplaceB = 0;
			laplaceB += (b * -1);
			laplaceB += (prev[ y ][ (x + 1) ].b * 0.2);
			laplaceB += (prev[ y ][ (x - 1) ].b * 0.2);
			laplaceB += (prev[ (y + 1) ][ x ].b * 0.2);
			laplaceB += (prev[ (y - 1) ][ x ].b * 0.2);
			laplaceB += (prev[ (y - 1) ][ (x - 1) ].b * 0.05);
			laplaceB += (prev[ (y - 1) ][ (x + 1) ].b * 0.05);
			laplaceB += (prev[ (y + 1) ][ (x - 1) ].b * 0.05);
			laplaceB += (prev[ (y + 1) ][ (x + 1) ].b * 0.05);
			
			//newspot.a = (a + (((dA * laplaceA) - (a * b * b) + (feed * (1 - a))) * 1));
			//newspot.b = (b + (((dB * laplaceB) - (a * b * b) + ((k + feed) * b)) * 1));
			newspot.a = a + (dA*laplaceA - a*b*b + feed*(1-a))*1;
			newspot.b = b + (dB*laplaceB + a*b*b - (k+feed)*b)*1;
			
			newspot.a = constrain(newspot.a, 0, 1);
			newspot.b = constrain(newspot.b, 0, 1);
		}
	}
}

void swap() {
	Cell[][] temp = prev;
	prev = grid;
	grid = temp;
}