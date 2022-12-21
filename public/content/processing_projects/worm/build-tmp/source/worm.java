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

public class worm extends PApplet {

float xx = 0.0f;
float yy = 0.0f;
float cc = 100.0f;
float xx_delta = 0.03f;
float yy_delta = 0.05f;
float container_radius_max = 200;
float container_radius_min = 60;
float blob_radius_max = 36;
float blob_radius_min = 8;
int num_blobs = 500;   // higher = better connection between each tail segment (smoother line)
float tail_length = 1.2f;   // 1.0 is mouth to tail

public void push() {
	pushMatrix();
	pushStyle();
}

public void pop() {
	popStyle();
	popMatrix();
}

public void setup() {
	
	colorMode(HSB, 100);
}

public void draw() {
	background(10);
	
	for(int i = num_blobs; i > 0; i--) {
		float b_xx = (xx - map(i, 1, num_blobs, 0, (TWO_PI * tail_length)));
		float b_yy = (yy - map(i, 1, num_blobs, 0, (TWO_PI * tail_length)));
		
		float x = (width / 2) + (sin(b_xx) * container_radius_max);
		float y = (height / 2) + (sin(b_yy) * map(i, 1, num_blobs, container_radius_max, container_radius_min));
		
		float r = map(i, 1, num_blobs, blob_radius_max, blob_radius_min);
		
		push();
		translate(x, y);
		
		noStroke();
		
		fill(color(((cc - map(i, 1, num_blobs, 0, 100)) % 100), 100, 100));
		ellipseMode(CENTER);
		ellipse(0, 0, r*2, r*2);
		pop();
	}
	
	xx += xx_delta;
	yy += yy_delta;
	cc += 0.1f;
	
	if(frameCount == 50) { save("preview.png"); }
}
  public void settings() { 	size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "worm" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
