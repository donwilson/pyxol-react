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

public class threed_box_array extends PApplet {

int num_boxes = 10;

float box_size_init = 32.0f;
float box_size_spacing = 32.0f;
float box_stroke_weight = 3.0f;
float rotate_speed = 0.3f;

int col_start = color(21, 107, 193);
int col_end = color(6, 229, 78);

float frames_per_full_scene = 180;

float aa = 0.0f;

public void setup() {
	
	//colorMode(HSB, 100);
	ortho();
}

public void draw() {
	background(10);
	
	float raw_box_degrees, box_degrees;
	raw_box_degrees = ((frameCount / frames_per_full_scene) * 360);
	box_degrees = (raw_box_degrees % 360);
	
	translate((width / 2), (height / 2));
	
	float raw_rotate_percent = (raw_box_degrees % (360 * 3));
	float rotate_percent = (box_degrees / 359);
	
	if(raw_rotate_percent >= 720) {
		rotateY(radians(-box_degrees * 0.25f * Easing.easeOutCubic(rotate_percent)));
		rotateZ(radians(-box_degrees * 0.25f * Easing.easeOutCubic(rotate_percent)));
	} else if(raw_rotate_percent >= 360) {
		rotateY(radians(box_degrees * 0.25f * Easing.linear(rotate_percent)));
	}
	
	float degrees_per_box_count = (360 / num_boxes);
	
	for(int i = 0; i < num_boxes; i++) {
		pushMatrix();
		pushStyle();
		
		strokeWeight(box_stroke_weight);
		noFill();
		
		float this_box_size = (box_size_init + (box_size_spacing * i));
		float min_num_degrees = (degrees_per_box_count * i);
		
		float percent = constrain(map(box_degrees, min_num_degrees, (min_num_degrees + degrees_per_box_count), 0, 1), 0, 1);
		
		float size_increase;
		
		if(raw_rotate_percent >= 720) {
			size_increase = (box_size_spacing * percent);
		} else if(raw_rotate_percent >= 360) {
			size_increase = (box_size_spacing * Easing.easeInOutCubic(percent));
		} else {
			size_increase = (box_size_spacing * Easing.easeInQuad(percent));
		}
		
		this_box_size += size_increase;
		
		stroke(col_start);
		
		if((percent > 0.0f) && (percent < 1.0f)) {
			stroke(col_end);
			strokeWeight((box_stroke_weight * 1.5f));
			//stroke(lerpColor(col_start, col_end, percent));
			
			if(i == (num_boxes - 1)) {
				this_box_size += (size_increase * 5);
				
				stroke(color(red(col_end), green(col_end), blue(col_end), (10 * (1 - percent))));
			}
		}
		
		box(this_box_size, this_box_size, this_box_size);
		
		popStyle();
		popMatrix();
	}
	
	aa += rotate_speed;
	
	if(frameCount == 440) { save("preview.png"); }
}

public void scrolledDown() {
	rotate_speed -= 0.1f;
	println("rotate_speed="+ rotate_speed);
}

public void scrolledUp() {
	rotate_speed += 0.1f;
	println("rotate_speed="+ rotate_speed);
}

public void mouseWheel(MouseEvent event) {
	int amount = event.getCount();
	
	if(amount > 0) {
		scrolledDown();
	} else if(amount < 0) {
		scrolledUp();
	}
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
  public void settings() { 	size(640, 640, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "threed_box_array" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
