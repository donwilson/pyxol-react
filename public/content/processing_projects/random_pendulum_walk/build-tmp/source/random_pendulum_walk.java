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

public class random_pendulum_walk extends PApplet {

Liner[] liner;

public void setup() {
	
	
	liner = new Liner[48];
	
	for(int i = 0; i < liner.length; i++) {
		liner[ i ] = new Liner();
	}
}

public void draw() {
	background(30);
	
	for(int i = 0; i < liner.length; i++) {
		liner[ i ].update();
		liner[ i ].draw();
	}
	
	for(int i = 0; i < liner.length; i++) {
		liner[ i ].drawForeground();
	}
	
	if(frameCount == 50) { save("preview.png"); }
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
class Liner {
	int lineSize = 4;
	
	float atAngle = 0;
	float nextAngle;
	float angleProgress = 0;
	float anglesPerUpdate = 3;
	float radius = 20;
	PVector atVec;
	
	int onOff;
	
	float pct;
	float angle;
	
	public void newNextAngle() {
		if(onOff == 1) {
			nextAngle = random((360 * 2), (360 * 6));
			anglesPerUpdate = 12;
		} else {
			nextAngle = random(45, 160);
			anglesPerUpdate = 3;
		}
		
		angleProgress = 0;
	}
	
	Liner() {
		atAngle = random(0, 359);
		
		onOff = round(random(1));
		
		newNextAngle();
		
		//atVec = new PVector((width * 0.5), (height * 0.5));
		atVec = new PVector(random(width), random(height));
		
		//radius = random(5, 25);
		//radius = 100;
		radius = (lineSize * 12);
	}
	
	public void update() {
		angleProgress += anglesPerUpdate;
		
		if(angleProgress >= nextAngle) {
			atAngle = (atAngle + nextAngle);
			
			atVec.set(
				(atVec.x + (cos( radians( atAngle ) ) * radius)),
				(atVec.y + (sin( radians( atAngle ) ) * radius))
			);
			
			if(atVec.x > width) {
				atVec.x -= width;
			} else if(atVec.x < 0) {
				atVec.x += width;
			}
			
			if(atVec.y > height) {
				atVec.y -= height;
			} else if(atVec.y < 0) {
				atVec.y += height;
			}
			
			atAngle = ((atAngle + 180) % 360);
			
			newNextAngle();
			
			pct = 0;
			angle = atAngle;
		} else {
			pct = (angleProgress / nextAngle);
			angle = (atAngle + (nextAngle * pct));
		}
	}
	
	public void draw() {
		// origin
		//float origin_pct = ((1 - pct) * Easing.easeOutQuad((1 - pct)));
		float origin_pct = (1 - pct);
		
		PVector originVec = new PVector(
			(cos( radians( atAngle ) ) * (radius * origin_pct)),
			(sin( radians( atAngle ) ) * (radius * origin_pct))
		);
		
		int fill_col = color(80);
		
		pushMatrix();
		
		
		translate(atVec.x, atVec.y);
		
		pushStyle();
		fill(fill_col);
		stroke(fill_col);
		strokeWeight(lineSize);
		
		line(0, 0, originVec.x, originVec.y);
		popStyle();
		
		pushStyle();
		noStroke();
		fill(fill_col);
		ellipseMode(CENTER);
		ellipse(originVec.x, originVec.y, (lineSize * 2), (lineSize * 2));
		popStyle();
		popMatrix();
	}
	
	public void drawForeground() {
		// progress
		//float progress_pct = (pct * Easing.easeOutQuad(pct));
		float progress_pct = pct;
		PVector progressVec;
		
		//color fill_col = color(255, 255, 255);
		int fill_col;
		
		if(onOff == 1) {
			fill_col = color(6, 229, 78);
			
			progressVec = new PVector(
				(cos( radians( angle ) ) * (radius * progress_pct)),
				(sin( radians( angle ) ) * (radius * progress_pct))
			);
		} else {
			fill_col = color(21, 107, 193);
			
			progressVec = new PVector(
				(cos( radians( ((atAngle + nextAngle) % 360) ) ) * (radius * progress_pct)),
				(sin( radians( ((atAngle + nextAngle) % 360) ) ) * (radius * progress_pct))
			);
		}
		
		
		
		pushMatrix();
		
		translate(atVec.x, atVec.y);
		
		pushStyle();
		fill(fill_col);
		stroke(fill_col);
		strokeWeight(lineSize);
		
		line(0, 0, progressVec.x, progressVec.y);
		popStyle();
		
		
		pushStyle();
		noStroke();
		fill(fill_col);
		ellipseMode(CENTER);
		ellipse(0, 0, (lineSize * 2), (lineSize * 2));
		popStyle();
		
		pushStyle();
		noStroke();
		fill(fill_col);
		ellipseMode(CENTER);
		ellipse(progressVec.x, progressVec.y, (lineSize * 2), (lineSize * 2));
		
		popStyle();
		
		
		popMatrix();
	}
}
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "random_pendulum_walk" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
