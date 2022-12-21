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

public class meta_equations extends PApplet {

float aa = 0.0f;

public void setup() {
	
	colorMode(HSB, 100);
}

public void draw() {
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

public int getPixelColor(int x, int y) {
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
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "meta_equations" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
