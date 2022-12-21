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

public class reaction_diffusion extends PApplet {

float dA = 1.0f;
float dB = 0.5f;
float feed = 0.055f;
float k = 0.062f;

Cell[][] grid;
Cell[][] prev;

public void setup() {
	
	
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

public void draw() {
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



public void update() {
	for(int y = 1; y < (height - 1); y++) {
		for(int x = 1; x < (width - 1); x++) {
			Cell spot = prev[ y ][ x ];
			Cell newspot = grid[ y ][ x ];
			
			float a = spot.a;
			float b = spot.b;
			
			float laplaceA = 0;
			laplaceA += (a * -1);
			laplaceA += (prev[ y ][ (x + 1) ].a * 0.2f);
			laplaceA += (prev[ y ][ (x - 1) ].a * 0.2f);
			laplaceA += (prev[ (y + 1) ][ x ].a * 0.2f);
			laplaceA += (prev[ (y - 1) ][ x ].a * 0.2f);
			laplaceA += (prev[ (y - 1) ][ (x - 1) ].a * 0.05f);
			laplaceA += (prev[ (y - 1) ][ (x + 1) ].a * 0.05f);
			laplaceA += (prev[ (y + 1) ][ (x - 1) ].a * 0.05f);
			laplaceA += (prev[ (y + 1) ][ (x + 1) ].a * 0.05f);
			
			float laplaceB = 0;
			laplaceB += (b * -1);
			laplaceB += (prev[ y ][ (x + 1) ].b * 0.2f);
			laplaceB += (prev[ y ][ (x - 1) ].b * 0.2f);
			laplaceB += (prev[ (y + 1) ][ x ].b * 0.2f);
			laplaceB += (prev[ (y - 1) ][ x ].b * 0.2f);
			laplaceB += (prev[ (y - 1) ][ (x - 1) ].b * 0.05f);
			laplaceB += (prev[ (y - 1) ][ (x + 1) ].b * 0.05f);
			laplaceB += (prev[ (y + 1) ][ (x - 1) ].b * 0.05f);
			laplaceB += (prev[ (y + 1) ][ (x + 1) ].b * 0.05f);
			
			//newspot.a = (a + (((dA * laplaceA) - (a * b * b) + (feed * (1 - a))) * 1));
			//newspot.b = (b + (((dB * laplaceB) - (a * b * b) + ((k + feed) * b)) * 1));
			newspot.a = a + (dA*laplaceA - a*b*b + feed*(1-a))*1;
			newspot.b = b + (dB*laplaceB + a*b*b - (k+feed)*b)*1;
			
			newspot.a = constrain(newspot.a, 0, 1);
			newspot.b = constrain(newspot.b, 0, 1);
		}
	}
}

public void swap() {
	Cell[][] temp = prev;
	prev = grid;
	grid = temp;
}
class Cell {
	float a;
	float b;
	
	Cell(float a_, float b_) {
		a = a_;
		b = b_;
	}
}
  public void settings() { 	size(320, 320); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "reaction_diffusion" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
