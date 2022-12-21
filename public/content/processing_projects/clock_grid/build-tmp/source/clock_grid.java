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

public class clock_grid extends PApplet {

Clock clocks[][];
int clock_size = 60;
int num_ys = 10;
int num_xs = 10;

public void setup() {
	
	
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

public void draw() {
	background(30);
	
	for(int y = 0; y < num_ys; y++) {
		for(int x = 0; x < num_xs; x++) {
			clocks[ y ][ x ].update();
			clocks[ y ][ x ].draw();
		}
	}
	
	if(frameCount == 50) { save("preview.png"); }
}
	
	class Clock {
		int top_y;
		int right_x;
		int bottom_y;
		int left_x;
		int size;
		
		PVector center;
		
		int hour;
		float minute;
		float hour_line_size;
		float minute_line_size;
		
		float speed;
		
		boolean DEBUG = false;
		
		Clock(int left_x_, int top_y_, int size_) {
			DEBUG = false;
			
			left_x = left_x_;
			top_y = top_y_;
			
			size = size_;
			
			right_x = (left_x + size);
			bottom_y = (top_y + size);
			
			center = new PVector(
				(left_x + (size / 2)),
				(top_y + (size / 2))
			);
			
			hour = ceil(random(0, 11));
			minute = ceil(random(0, 59));
			
			hour_line_size = (PApplet.parseFloat(size) * 0.5f * 0.7f);
			minute_line_size = (PApplet.parseFloat(size) * 0.5f * 0.9f);
			
			speed = random(1, 2.3f);
		}
		
		public void update() {
			minute += speed;
			
			if(minute >= 60) {
				hour += 1;
				minute %= 60;
				
				hour %= 11;
			}
		}
		
		public void draw() {
			pushMatrix();
			
			if(DEBUG) {
				pushStyle();
				
				stroke(255);
				noFill();
				rect(left_x, top_y, size, size);
				
				popStyle();
			}
			
			// clock face
			pushStyle();
			
			ellipseMode(CENTER);
			noFill();
			stroke(color(100));
			strokeWeight(2);
			ellipse(center.x, center.y, size, size);
			
			popStyle();
			
			//stroke(color(100));
			//strokeWeight(1);
			//fill(color(30));
			
			//rect(left_x, top_y, size, size);
			
			// minute
			pushStyle();
			stroke(color(150));
			strokeWeight(1);
			
			line(
				center.x,
				center.y,
				(center.x + (minute_line_size * cos(radians( minute * (360 / 60) )))),
				(center.y + (minute_line_size * sin(radians( minute * (360 / 60) ))))
			);
			popStyle();
			
			
			// hour
			pushStyle();
			stroke(color(255));
			strokeWeight(2);
			
			line(
				center.x,
				center.y,
				(center.x + (hour_line_size * cos(radians( hour * (360 / 12) )))),
				(center.y + (hour_line_size * sin(radians( hour * (360 / 12) ))))
			);
			
			popStyle();
			
			
			popMatrix();
		}
	}
	
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "clock_grid" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
