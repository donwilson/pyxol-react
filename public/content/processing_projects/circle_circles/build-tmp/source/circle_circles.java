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

public class circle_circles extends PApplet {

PVector c1;
PVector c2;

float aa = 0.0f;
float bb = 0.0f;

float r_c1 = 200;
float r_c2 = (r_c1 / 2);



public void setup() {
	
	background(30);
	
	c1 = new PVector();
	c2 = new PVector();
}

public void draw() {
	//background(30, 100);
	
	translate((width / 2), (height / 2));
	
	stroke(255);
	strokeWeight(2);
	noFill();
	beginShape();
	
	for(int i = 0; i < 10; i++) {
		c1.x = (cos(aa) * r_c1);
		c1.y = (sin(aa) * r_c1);
		
		c2.x = (c1.x + (cos(bb) * r_c2));
		c2.y = (c1.y + (sin(bb) * r_c2));
		
		
		curveVertex(c2.x, c2.y);
		
		bb += 0.01f;
		bb %= 360;
	}
	
	endShape();
	
	aa += 0.02f;
	
	aa %= 360;
	
	if(frameCount == 150) { save("preview.png"); }
}
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "circle_circles" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
