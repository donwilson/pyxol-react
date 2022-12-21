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

public class triangle_squares extends PApplet {

int framesPerRevolution = 90;

float shape_width = 100;
float shape_height = 200;

public void setup() {
	
}

public void draw() {
	background(30);
	
	for(int y = (0 - PApplet.parseInt(shape_height)); y <= (height + PApplet.parseInt(shape_height)); y += shape_height) {
		for(int x = (0 - PApplet.parseInt(shape_width)); x <= (width + PApplet.parseInt(shape_width)); x += shape_width) {
			drawFigure(PApplet.parseFloat(x), PApplet.parseFloat(y));
			
			// offset
			drawFigure(
				(PApplet.parseFloat(x) + (shape_width * 0.5f)),
				(PApplet.parseFloat(y) + (shape_height * 0.25f))
			);
		}
	}
	
	if(frameCount == 50) { save("preview.png"); }
}

public void drawFigure(float offset_x, float offset_y) {
	float center_x = (shape_width * 0.5f);
	
	pushMatrix();
	pushStyle();
	
	translate((offset_x + center_x), offset_y);
	
	noStroke();
	//fill(color(255, 255, 255));
	fill(color(map(offset_x, 0, width, 0, 255), map(offset_y, 0, height, 0, 255), 255));
	
	beginShape();
	
	vertex(0, 0);
	
	float at_pct = map((frameCount % framesPerRevolution), 0, framesPerRevolution, 0, 2);
	
	if(at_pct > 1) {
		//at_pct = (2 - at_pct);
		at_pct = (1 - (at_pct - 1));
	}
	
	
	float easing_amt = 1;
	
	//if(at_pct > 0.5) {
	//	easing_amt = Easing.easeInCubic(((at_pct - 0.5) * 2));
	//} else {
	//	easing_amt = Easing.easeInCubic((at_pct * 2));
	//}
	
	vertex(
		((0 - center_x) + (shape_width * at_pct * easing_amt)),
		(shape_height * 0.25f)
	);
	
	// midpoint
	vertex(0, (shape_height * 0.5f));
	
	vertex(
		(center_x - (shape_width * at_pct * easing_amt)),
		(shape_height * 0.75f)
	);
	
	
	vertex(0, shape_height);
	
	endShape(CLOSE);
	
	popStyle();
	popMatrix();
}
static class Easing {
	// no easing, no acceleration
	public static float linear(float t) { return t; }
	
	// accelerating from zero velocity
	public static float easeInQuad(float t) { return t*t; }
	
	// decelerating to zero velocity
	public static float easeOutQuad(float t) { return t*(2-t); }
	
	// acceleration until halfway, then deceleration
	public static float easeInOutQuad(float t) { return t<.5f ? 2*t*t : -1+(4-2*t)*t; }
	
	// accelerating from zero velocity 
	public static float easeInCubic(float t) { return t*t*t; }
	
	// decelerating to zero velocity 
	public static float easeOutCubic(float t) { return (--t)*t*t+1; }
	
	// acceleration until halfway, then deceleration 
	public static float easeInOutCubic(float t) { return t<.5f ? 4*t*t*t : (t-1)*(2*t-2)*(2*t-2)+1; }
	
	// accelerating from zero velocity 
	public static float easeInQuart(float t) { return t*t*t*t; }
	
	// decelerating to zero velocity 
	public static float easeOutQuart(float t) { return 1-(--t)*t*t*t; }
	
	// acceleration until halfway, then deceleration
	public static float easeInOutQuart(float t) { return t<.5f ? 8*t*t*t*t : 1-8*(--t)*t*t*t; }
	
	// accelerating from zero velocity
	public static float easeInQuint(float t) { return t*t*t*t*t; }
	
	// decelerating to zero velocity
	public static float easeOutQuint(float t) { return 1+(--t)*t*t*t*t; }
	
	// acceleration until halfway, then deceleration 
	public static float easeInOutQuint(float t) { return t<.5f ? 16*t*t*t*t*t : 1+16*(--t)*t*t*t*t; }
}
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "triangle_squares" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
