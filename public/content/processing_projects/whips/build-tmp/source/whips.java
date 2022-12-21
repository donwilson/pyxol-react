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

public class whips extends PApplet {

float angleMin = 0;
float angleMax = 270;

class Line {
	float radius;
	int lineColor;
	int points;
	float lineWidth;
	float angle = 0.0f;
	
	float anglePerFrame;
	boolean reversing = false;
	
	Line(float radius_, int color_, int points_, float lineWidth_, float startingAngle_, float anglePerFrame_) {
		radius = radius_;
		lineColor = color_;
		points = 360;//points = points_;
		lineWidth = lineWidth_;
		angle = startingAngle_;
		anglePerFrame = anglePerFrame_;
	}
	
	public void update() {
		if(reversing) {
			angle -= anglePerFrame;
			
			if(angle < angleMin) {
				angle = angleMin;
				reversing = !reversing;
			}
		} else {
			angle += anglePerFrame;
			
			if(angle > angleMax) {
				angle = angleMax;
				reversing = !reversing;
			}
		}
	}
	
	public void draw() {
		push();
		
		//translate(0, 0);
		
		//beginShape();
		//noFill();
		//stroke(lineColor);
		//strokeWeight(lineWidth);
		
		noStroke();
		fill(lineColor);
		
		//float angle = 270;
		
		float angleStep = map(1, 1, points, angle, 0);
		float ax = (sin(radians(angleStep)) * radius);
		float ay = (cos(radians(angleStep)) * radius);
		
		for(int j = 0; j < points; j++) {
			angleStep = map(j, 0, (points - 1), angleMax, angle);
			
			ax = (sin(radians(angleStep)) * radius);
			ay = (cos(radians(angleStep)) * radius);
			
			//vertex(ax, ay);
			ellipse(ax, ay, lineWidth, lineWidth);
		}
		
		//curveVertex(ax, ay);
		//endShape();
		
		pop();
	}
}


float thickness = 7;
float spacing = 3;
float speed = 6;   // in angles per frame
Line[] lines;

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
	
	lines = new Line[21];
	
	for(int i = 1; i <= 21; i++) {
		float radius = ((thickness + spacing) * i);
		int col = color(map(i, 0, 21, 0, 80), 100, 100);   // map starts at 0 since lines doesn't contain center dot
		int points;
		
		if(i < 6) {
			points = 10;
		} else if(i < 15) {
			points = 35;
		} else {
			points = 50;
		}
		
		float startAngle = map(i, 1, 21, angleMax, angleMin);
		
		lines[ (i - 1) ] = new Line(radius, col, points, thickness, startAngle, speed);
	}
}

public void draw() {
	background(color(0, 0, 100));//background(10);
	
	translate(width/2, height/2);
	
	// center dot
	push();
	
	translate(0, 0);
	
	//strokeWeight(thickness);
	noStroke();
	noFill();
	ellipseMode(CENTER);
	fill(color(0, 100, 100));
	ellipse(0, 0, thickness, thickness);
	
	pop();
	
	for(Line l : lines) {
		l.draw();
		l.update();
	}
	
	if(frameCount == 50) { save("preview.png"); }
}
  public void settings() { 	size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "whips" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
