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

public class barnsley_fern extends PApplet {



public void setup() {
	
	background(255);
	
	createFern(640, 640);
	
	save("preview.png");
}

public void createFern(int w, int h) {
	float x = 0;
	float y = 0;
	
	loadPixels();
	
	for(int i = 0; i < 200000; i++) {
		float tmpx, tmpy;
		float r = random(1);
		
		if(r < 0.01f) {
			tmpx = 0;
			tmpy = (0.16f * y);
		} else if(r <= 0.08f) {
			tmpx = 0.2f * x - 0.26f * y;
			tmpy = 0.23f * x + 0.22f * y + 1.6f;
		} else if(r <= 0.15f) {
			tmpx = -0.15f * x + 0.28f * y;
			tmpy = 0.26f * x + 0.24f * y + 0.44f;
		} else {
			tmpx = 0.85f * x + 0.04f * y;
			tmpy = -0.04f * x + 0.85f * y + 1.6f;
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
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "barnsley_fern" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
