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

public class perlin_circle extends PApplet {

boolean SAVE_FRAMES = false;

int off = 0;
float radius = 300;
float aa = 0.01f;
int num_lines = 300;

int near = color(60, 31, 15);
int far = color(28, 10, 1);

public void setup() {
	
}

public void draw() {
	background(0);
	
	for(int f = 0; f < num_lines; f++) {
		float amt = 0.0f;
		float angle_increase = 1;
		float step = (aa * PApplet.parseFloat((f + off)));
		int col = lerpColor(near, far, (1 - norm(f, 0, num_lines)));
		
		float pn_init = noise(step, amt);
		
		float zoom = max(0.1f, (7 * norm(f, 0, num_lines)));
		
		pushMatrix();
		pushStyle();
		
		beginShape();
		
		stroke(col);
		strokeWeight( map(f, 0, (num_lines - 1), 3, 10) );
		noFill();
		
		translate(width/2, height/2);
		
		for(float angle = 0; angle <= 360; angle += angle_increase) {
			float pn = noise(step, amt);
			
			if(angle > 345) {
				pn = map(angle, 346, 360, pn, pn_init);
			} else if(angle < 15) {
				pn = map(angle, 0, 14, pn_init, pn);
			}
			
			float px = ((radius * pn * zoom) * cos(radians(angle - 90)));
			float py = ((radius * pn * zoom) * sin(radians(angle - 90)));
			
			vertex(px, py);
			
			amt = (amt + aa);
		}
		
		endShape();
		
		popStyle();
		popMatrix();
	}
	
	off++;
	
	if(SAVE_FRAMES) {
		saveFrame("frames/line-######.jpg");
	}
	
	if(frameCount == 50) { save("preview.png"); }
}
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "perlin_circle" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
