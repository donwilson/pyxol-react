// num_x = 38
// num_y = 33

PFont font;
FloatList stack = new FloatList();
Spot[][] spots;

boolean did_debug = false;

int font_size = 21;
int color_max = 360;

int num_x = 38;   // num_x and num_y are hard-coded for ease
int num_y = 33;
float x_off;
float y_off;

float aa = 0.0;
float noise_del = 0.01;

int[] split_y = new int[33];

// based on colorMode(360):
String getLetterForColorVal(int color_val) {
	if((color_val >= 17) && (color_val < 41)) {
		return "O";
	} else if((color_val >= 41) && (color_val < 76)) {
		return "Y";
	} else if((color_val >= 76) && (color_val < 165)) {
		return "G";
	} else if((color_val >= 165) && (color_val < 254)) {
		return "B";
	} else if((color_val >= 254) && (color_val < 280)) {
		return "I";
	} else if((color_val >= 280) && (color_val < 335)) {
		return "V";
	}
	
	return "R";
}

void setup() {
	size(640, 640);
	colorMode(HSB, color_max);
	
	frameRate(30);
	
	/*
		split_y[0] = 19;//17;
		split_y[1] = 19;//16;
		split_y[2] = 19;//15;
		split_y[3] = 19;//14;
		split_y[4] = 19;//13;
		split_y[5] = 19;//13;
		split_y[6] = 19;//12;
		split_y[7] = 19;//12;
		split_y[8] = 19;//12;
		split_y[9] = 19;//12;
		split_y[10] = 19;//12;
		split_y[11] = 19;//12;
		split_y[12] = 19;//15;
		split_y[13] = 19;//15;
		split_y[14] = 19;//16;
		split_y[15] = 19;//17;
		split_y[16] = 19;//18;
		split_y[17] = 19;//19;
		split_y[18] = 19;//20;
		split_y[19] = 19;//21;
		split_y[20] = 19;//22;
		split_y[21] = 19;//22;
		split_y[22] = 19;//22;
		split_y[23] = 19;//22;
		split_y[24] = 19;//22;
		split_y[25] = 19;//22;
		split_y[26] = 19;//22;
		split_y[27] = 19;//20;
		split_y[28] = 19;//19;
		split_y[29] = 19;//18;
		split_y[30] = 19;//18;
		split_y[31] = 19;//18;
		split_y[32] = 19;//17;
	*/
	
	//for(int i = 0; i < num_y; i++) {
	//	split_y[ i ] = round((((num_x - 1) - i)) - ceil((num_x - num_y) / 2));
	//}
	
	
	
	font = createFont("SourceCodePro-Bold.ttf", font_size);
	
	spots = new Spot[33][38];
	
	for (int y = 0; y < spots.length; y++) {
		for (int x = 0; x < spots[ y ].length; x++) {
			spots[ y ][ x ] = new Spot(x, y, font, font_size, noise_del, color_max);
			
			//if(x >= split_y[ y ]) {
			//	spots[ y ][ x ].enableConnections();
			//}
			
			/*if((x > (num_x / 2)) && (y < (num_y / 2))) {
				spots[ y ][ x ].enableConnections();
			} else if((x < (num_x / 2)) && (y > (num_y / 2))) {
				spots[ y ][ x ].enableConnections();
			}*/
			
			int margin = 6;
			
			if((x >= margin) && (x < (38 - margin)) && (y >= margin) && (y < (33 - margin))) {
				spots[ y ][ x ].enableConnections();
			}
			
			//spots[ y ][ x ].enableConnections();
		}
	}
	
	x_off = ((width - (num_x * spots[0][0].letter_width)) * 0.5);
	y_off = -2;//float y_off = ((height - (num_y * spots[0][0].letter_height)) * 0.5);
}

void draw() {
	background(10);
	
	translate(x_off, y_off);
	
	// update spots
	for (int j = 0; j < spots.length; j++) {
		for (int k = 0; k < spots[ j ].length; k++) {
			spots[ j ][ k ].update(aa);
		}
	}
	
	// draw spots
	for (int j = 0; j < spots.length; j++) {
		for (int k = 0; k < spots[ j ].length; k++) {
			spots[ j ][ k ].draw(aa);
		}
	}
	
	
	
	//// @TMP
	//for (int j = 0; j < spots.length; j++) {
	//	for (int k = 0; k < spots[ j ].length; k++) {
	//		noStroke();
	//		fill(color_max, 0, color_max);
	//		ellipseMode(CENTER);
	//		ellipse(
	//			spots[ j ][ k ].center.x,
	//			spots[ j ][ k ].center.y,
	//			2,
	//			2
	//		);
	//	}
	//}
	
	
	
	
	aa += (noise_del * 0.5);
	//aa += noise_del;
	
	//println(frameRate);
	
	/*
		// WORKS:::
		
		background(10);
		
		float letter_width = (font_size * 0.8);
		float letter_height = (font_size * 0.92);
		
		int num_x = floor( (width / letter_width) );
		int num_y = floor( (height / letter_height) );
		
		if(!did_debug) {
			println("num_x = "+ num_x);
			println("num_y = "+ num_y);
			
			did_debug = true;
		}
		
		float x_off = ((width - (num_x * letter_width)) * 0.5);
		float y_off = -2;//float y_off = ((height - (num_y * letter_height)) * 0.5);
		
		translate(x_off, y_off);
		
		// noStroke();
		// fill(100, 100, 100);
		// rect(0, 0, letter_width, letter_height);
		
		for(int y = 0; y < num_y; y++) {
			for(int x = 0; x < num_x; x++) {
				float noise_val = noise((x * noise_del), (y * noise_del), aa);
				
				noise_val = map(noise_val, 0.3, 0.7, 0, 1);
				//noise_val = map(noise_val, 0.3, 0.7, 0, 1);
				
				int color_value = round( (noise_val * color_max) );
				
				
				noStroke();
				fill(color_value, color_max, color_max);
				
				textAlign(CENTER, CENTER);
				textFont(font);
				textSize(font_size);
				textLeading(0);
				text(
					getLetterForColorVal(color_value),
					((x * letter_width) + (letter_width * 0.5)),
					((y * letter_height) + (letter_height * 0.5))
				);
			}
		}
		
		aa += (noise_del * 0.5);
		//aa += noise_del;
	*/
	
	if(frameCount == 50) { save("preview.png"); }
}

//void mouseWheel(MouseEvent event) {
//	float e = event.getCount();
//	
//	if(e > 0) {
//		font_size--;
//	} else {
//		font_size++;
//	}
//	
//	println("new font_size: "+ font_size);
//}