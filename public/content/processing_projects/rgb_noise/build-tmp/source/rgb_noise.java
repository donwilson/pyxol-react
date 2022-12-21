import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class rgb_noise extends PApplet {

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

float aa = 0.0f;
float noise_del = 0.01f;

int[] split_y = new int[33];

// based on colorMode(360):
public String getLetterForColorVal(int color_val) {
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

public void setup() {
	
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
	
	x_off = ((width - (num_x * spots[0][0].letter_width)) * 0.5f);
	y_off = -2;//float y_off = ((height - (num_y * spots[0][0].letter_height)) * 0.5);
}

public void draw() {
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
	
	
	
	
	aa += (noise_del * 0.5f);
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
class Spot {
	int x;
	int y;
	PFont font;
	int font_size;
	float letter_width;
	float letter_height;
	float noise_d;
	int color_scale;
	
	int color_val;
	int color_val_mid;   // mid point of color
	String letter;
	
	float connectionLineSize = 2.0f;
	
	PVector center;
	
	ArrayList<PVector> neighbors;
	boolean drawingConnections = false;
	
	Spot(int x_, int y_, PFont font_, int font_size_, float noise_d_, int color_scale_) {
		x = x_;
		y = y_;
		
		font = font_;
		font_size = font_size_;
		letter_width = (font_size * 0.8f);
		letter_height = (font_size * 0.92f);
		
		noise_d = noise_d_;
		color_scale = color_scale_;
		
		// calculate center point
		center = new PVector(
			((x * letter_width) + (letter_width * 0.5f)),
			((y * letter_height) + (letter_height * 0.5f))
		);
		
		calcNeighbors();
	}
	
	public void update(float d) {
		float noise_val = noise((x * noise_d), (y * noise_d), d);
		noise_val = map(noise_val, 0.3f, 0.7f, 0, 1);
		//noise_val = map(noise_val, 0.3, 0.7, 0, 1);
		
		color_val = round( (noise_val * color_scale) );
		
		updateLetter();
	}
	
	public void enableConnections() { drawingConnections = true; }
	public void disableConnections() { drawingConnections = false; }
	
	public boolean isDrawingConnections() {
		return drawingConnections;
	}
	
	public void draw(float d) {
		if(drawingConnections) {
			//drawConnections();
			drawComplexConnections();
		} else {
			drawLetter();
		}
	}
	
	public void drawLetter() {
		pushMatrix();
		pushStyle();
		
		noStroke();
		fill(color_val_mid, color_scale, color_scale);
		
		textAlign(CENTER, CENTER);
		textFont(font);
		textSize(font_size);
		textLeading(0);
		text(letter, center.x, center.y);
		
		popStyle();
		popMatrix();
	}
	
	public void drawConnections() {
		pushMatrix();
		pushStyle();
		
		int num_connections = 0;
		
		if(neighbors.size() > 0) {
			for(PVector neighbor_vector : neighbors) {
				Spot neighbor = spots[ PApplet.parseInt(neighbor_vector.y) ][ PApplet.parseInt(neighbor_vector.x) ];
				
				if(letter.equals(neighbor.letter)) {
					// same color letter, make connection
					if(neighbor.isDrawingConnections()) {
						// neighbor is accepting connections
						pushStyle();
						
						stroke(color_val_mid, color_scale, color_scale);
						fill(color_val_mid, color_scale, color_scale);
						
						strokeWeight(connectionLineSize);
						strokeCap(ROUND);
						
						line(
							center.x,
							(center.y - y_off),
							neighbor.center.x,
							(neighbor.center.y - y_off)
						);
						
						popStyle();
						
						num_connections++;
					}
				}
			}
		}
		
		if(num_connections == 0) {
			// no connections made, make a center dot
			pushStyle();
			
			ellipseMode(CENTER);
			
			noStroke();
			fill(color_val, color_scale, color_scale);
			
			ellipse(
				center.x,
				(center.y - y_off),
				(font_size / 3),
				(font_size / 3)
			);
			
			popStyle();
		}
		
		popStyle();
		popMatrix();
	}
	
	public void drawComplexConnections() {
		pushMatrix();
		pushStyle();
		
		ArrayList<PVector> connections = new ArrayList<PVector>();
		
		if(neighbors.size() > 0) {
			for(PVector neighbor_vector : neighbors) {
				Spot neighbor = spots[ PApplet.parseInt(neighbor_vector.y) ][ PApplet.parseInt(neighbor_vector.x) ];
				
				if(letter.equals(neighbor.letter)) {
					// same color letter, make connection
					if(neighbor.isDrawingConnections()) {
						// neighbor is accepting connections
						connections.add(neighbor.center);
					}
				}
			}
		}
		
		if(connections.size() > 2) {
			// complex shape
			noStroke();//strokeWeight(1);
			//stroke(color_val_mid, color_scale, color_scale);
			fill(color_val_mid, color_scale, color_scale);
			
			//strokeWeight(connectionLineSize);
			//strokeCap(ROUND);
			
			beginShape(TRIANGLE_STRIP);
			
			vertex(center.x, center.y - y_off + 3);
			
			for(PVector neighbor_center : connections) {
				vertex(neighbor_center.x, neighbor_center.y - y_off + 3);
			}
			
			endShape(CLOSE);
		} else if(connections.size() > 1) {
			// complex shape
			//noStroke();//strokeWeight(1);
			stroke(color_val_mid, color_scale, color_scale);
			fill(color_val_mid, color_scale, color_scale);
			
			//strokeWeight(connectionLineSize);
			//strokeCap(ROUND);
			
			beginShape();
			
			vertex(center.x, center.y - y_off + 3);
			
			for(PVector neighbor_center : connections) {
				vertex(neighbor_center.x, neighbor_center.y - y_off + 3);
			}
			
			endShape(CLOSE);
		} else/* if(connections.size() > 0) {
			// line
			stroke(color_val_mid, color_scale, color_scale);
			fill(color_val_mid, color_scale, color_scale);
			
			strokeWeight(connectionLineSize);
			strokeCap(ROUND);
			
			PVector neighbor = connections.get(0);
			
			line(
				center.x,
				(center.y - y_off),
				neighbor.x,
				(neighbor.y - y_off)
			);
		} else*/ {
			/*// no connections made, make a center dot
			ellipseMode(CENTER);
			
			noStroke();
			fill(color_val, color_scale, color_scale);
			
			ellipse(
				center.x,
				(center.y - y_off),
				(font_size * 0.25),
				(font_size * 0.25)
			);*/
		}
		
		popStyle();
		popMatrix();
	}
	
	public void calcNeighbors() {
		neighbors = new ArrayList<PVector>();
		
		// max x/y values possible for comparing neighbors
		int x_max = (floor( (width / letter_width) ) - 1);
		int y_max = (floor( (height / letter_height) ) - 1);
		
		// check left side
		if(x > 0) {
			// has left neighbor(s)
			
			if(y > 0) {
				// top left
				neighbors.add(new PVector((x - 1), (y - 1)));
			}
			
			// left
			neighbors.add(new PVector((x - 1), y));
			
			if(y < y_max) {
				// bottom left
				neighbors.add(new PVector((x - 1), (y + 1)));
			}
		}
		
		// above?
		if(y > 0) {
			// top
			neighbors.add(new PVector(x, (y - 1)));
		}
		
		// below?
		if(y < y_max) {
			// bottom
			neighbors.add(new PVector(x, (y + 1)));
		}
		
		// check right side
		if(x < x_max) {
			// has right neighbor(s)
			
			if(y > 0) {
				// top right
				neighbors.add(new PVector((x + 1), (y - 1)));
			}
			
			// right
			neighbors.add(new PVector((x + 1), y));
			
			if(y < y_max) {
				// bottom right
				neighbors.add(new PVector((x + 1), (y + 1)));
			}
		}
	}
	
	public void updateLetter() {
		if((color_val >= 17) && (color_val < 41)) {
			letter = "O";
			color_val_mid = (17 + (41 - 17));
		} else if((color_val >= 41) && (color_val < 76)) {
			letter = "Y";
			color_val_mid = (41 + (76 - 41));
		} else if((color_val >= 76) && (color_val < 165)) {
			letter = "G";
			color_val_mid = (76 + (165 - 76));
		} else if((color_val >= 165) && (color_val < 254)) {
			letter = "B";
			color_val_mid = (165 + (254 - 165));
		} else if((color_val >= 254) && (color_val < 280)) {
			letter = "I";
			color_val_mid = (254 + (280 - 254));
		} else if((color_val >= 280) && (color_val < 335)) {
			letter = "V";
			color_val_mid = (280 + (335 - 280));
		} else {
			letter = "R";
			color_val_mid = 5;
		}
	}
}
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "rgb_noise" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
