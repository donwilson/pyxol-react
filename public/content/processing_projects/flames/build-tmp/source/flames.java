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

public class flames extends PApplet {

int xsize;
int ysize;
int c;
int p;

float noise_size = 0.008f;
float noise_vertical_speed = 0.03f;
float dampening = 0.2f;
float aa = 0.0f;

float[][] grid;
float[][] new_grid;
float[][] cooling_map;
float[][] touched_area;

public void setup() {
	
	//colorMode(HSB, 100);
	
	grid = new float[ (height + 1) ][ width ];
	new_grid = new float[ (height + 1) ][ width ];
	cooling_map = new float[ (height + 1) ][ width ];
	
	for(int y = 0; y < (height + 1); y++) {
		grid[ y ] = new float[ width ];
		new_grid[ y ] = new float[ width ];
		
		for(int x = 0; x < width; x++) {
			grid[ y ][ x ] = 0.0f;
			new_grid[ y ][ x ] = 0.0f;
			
			// cooling map
			//cooling_map[ y ][ x ] = map(noise((x * noise_size), (y * noise_size)), 0, 1, 0, 0.03);
			cooling_map[ y ][ x ] = noise((x * noise_size), (y * noise_size));
		}
	}
	
	touched_area = new float[ height ][ width ];
	
	for(int y = 0; y < height; y++) {
		touched_area[ y ] = new float[ width ];
		
		for(int x = 0; x < width; x++) {
			touched_area[ y ][ x ] = 0.0f;
		}
	}
}

public void draw() {
	background(0);
	
	if(mousePressed) {
		fillTouchedArea(mouseX, mouseY);
	}
	
	update();
	
	// draw
	loadPixels();
	
	for(int y = 0; y < height; y++) {
		for(int x = 0; x < width; x++) {
			int index = (x + (y * width));
			float val = new_grid[ y ][ x ];
			
			if(val == 0.000f) {
				pixels[ index ] = color(0, 0, 0);
			} else {
				int col = floor(map(val, 0, 1, 0, 200));
				//int col = floor(map((1 - log(val)), 0, 1, 0, 200));
				
				
				pixels[ index ] = lerpColor(
					color(0),
					color(255, col, 50),
					val
				);
			}
			
			//pixels[ (x + (y * width)) ] = color(100, floor(map(val, 0, 1, 100, 0)), 0);
		}
	}
	
	updatePixels();
	
	swap();
	scroll_cooling_map_up();
	
	println(frameRate);
	
	if(frameCount == 50) { save("preview.png"); }
}

public void fillTouchedArea(int mX, int mY) {
	int max_dist = 5;
	
	for(int y = (mY - max_dist); y <= (mY + max_dist); y++) {
		for(int x = (mX - max_dist); x <= (mX + max_dist); x++) {
			if((x < 0) || (x > (width - 1)) || (y < 0) || (y > (height - 1)) || (dist(x, y, mX, mY) > max_dist)) {
				continue;
			}
			
			touched_area[ y ][ x ] = 1.0f;
			touched_area[ y ][ x ] = 1.0f;
		}
	}
}

public void mousePressed() {
	clearTouchedArea();
	fillTouchedArea(mouseX, mouseY);
}

public void mouseDragged() {
	fillTouchedArea(mouseX, mouseY);
}

public void clearTouchedArea() {
	for(int y = 0; y < height; y++) {
		for(int x = 0; x < width; x++) {
			touched_area[ y ][ x ] = 0.0f;
		}
	}
}

public void update() {
	// fill in touched area first
	for(int y = 0; y < height; y++) {
		for(int x = 0; x < width; x++) {
			if(touched_area[ y ][ x ] > 0) {
				grid[ y ][ x ] = touched_area[ y ][ x ];
			}
		}
	}
	
	// logic
	for(int y = 1; y < (height + 1); y++) {
		for(int x = 0; x < width; x++) {
			float n_sum = 0.0f;
			float n_i = 0;
			
			if((x + 1) < width) {
				n_sum += grid[ y ][ (x + 1) ];
				n_i++;
			}
			
			if((x - 1) >= 0) {
				n_sum += grid[ y ][ (x - 1) ];
				n_i++;
			}
			
			if((y + 1) < height) {
				n_sum += grid[ (y + 1) ][ x ];
				n_i++;
			}
			
			if((y - 1) >= 0) {
				n_sum += grid[ (y - 1) ][ x ];
				n_i++;
			}
			
			//float c = cooling_map[ y ][ x ];
			float c = noise((x * noise_size), (y * noise_size), (aa + (y * noise_size)));
			//float c = noise((x * noise_size), (y * noise_size));
			//c = (c + 0.2);
			
			c = (c * c * c);
			c *= dampening;
			
			//c *= dampening;
			
			//c = c * c * c * c;
			//c *= dampening;
			
			float p = 0.0f;
			
			if(n_i > 0) {
				p = (n_sum / n_i);
			}
			
			p = (p - c);
			
			if(p < 0) {
				p = 0;
			}
			
			new_grid[ (y - 1) ][ x ] = p;
		}
	}
	
	aa += noise_vertical_speed;
}

public void swap() {
	//grid = new_grid;
	
	// for(int y = 0; y < (height + 1); y++) {
	// 	for(int x = 0; x < width; x++) {
	// 		grid[ y ][ x ] = new_grid[ y ][ x ];
	// 	}
	// }
	
	float[][] temp = grid;
	grid = new_grid;
	new_grid = temp;
}

public void scroll_cooling_map_up() {
	float[] first_row = cooling_map[0];
	
	for(int y = 0; y < (height - 1); y++) {
		cooling_map[ y ] = cooling_map[ (y + 1) ];
	}
	
	cooling_map[ (height - 1) ] = first_row;
}
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "flames" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
