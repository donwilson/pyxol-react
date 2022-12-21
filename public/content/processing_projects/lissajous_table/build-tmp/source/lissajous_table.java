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

public class lissajous_table extends PApplet {

float angle = 0;
int w = 32;
int h = w;
int cols = 0;
int rows = 0;

Curve[][] curves;

public void setup() {
	//fullScreen(P3D);
	
	cols = ((width / w) - 1);
	rows = ((height / h) - 1);
	
	curves = new Curve[ rows ][ cols ];
	
	for(int y = 0; y < rows; y++) {
		for(int x = 0; x < cols; x++) {
			curves[ y ][ x ] = new Curve();
		}
	}
}

public void draw() {
	background(0);
	
	float d = w - 4;
	float r = (d / 2);
	
	int dot_size = 4;
	
	noFill();
	
	for(int i = 0; i < cols; i++) {
		float cx = (w + i * w + w / 2);
		float cy = (w / 2);
		
		stroke(255);
		strokeWeight(1);
		ellipse(cx, cy, d, d);
		
		float x = (r * cos(angle * (i + 1) - HALF_PI));
		float y = (r * sin(angle * (i + 1) - HALF_PI));
		
		strokeWeight(dot_size);
		point(cx + x, cy + y);
		
		stroke(255, 50);
		strokeWeight(1);
		
		line((cx + x), (cy + y), (cx + x), height);
		
		for(int j = 0; j < rows; j++) {
			curves[ j ][ i ].setX((cx + x));
		}
	}
	
	for(int i = 0; i < rows; i++) {
		float cx = (h / 2);
		float cy = (h + i * h + h / 2);
		
		stroke(255);
		strokeWeight(1);
		ellipse(cx, cy, d, d);
		
		float x = (r * cos(angle * (i + 1) - HALF_PI));
		float y = (r * sin(angle * (i + 1) - HALF_PI));
		
		strokeWeight(dot_size);
		point(cx + x, cy + y);
		
		stroke(255, 50);
		strokeWeight(1);
		
		line((cx + x), (cy + y), width, (cy + y));
		
		for(int j = 0; j < cols; j++) {
			curves[ i ][ j ].setY((cy + y));
		}
	}
	
	for(int y = 0; y < rows; y++) {
		for(int x = 0; x < cols; x++) {
			curves[ y ][ x ].addPoint();
			curves[ y ][ x ].show();
		}
	}
	
	angle -= 0.02f;
	
	if(angle < -TWO_PI) {
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < cols; x++) {
				curves[ y ][ x ].reset();
			}
		}
		
		angle = 0;
	}
	
	if(frameCount == 50) { save("preview.png"); }
}
class Curve {
	ArrayList<PVector> path;
	PVector current;
	
	Curve() {
		path = new ArrayList<PVector>();
		current = new PVector();
	}
	
	public void setX(float x_) {
		current.x = x_;
	}
	
	public void setY(float y_) {
		current.y = y_;
	}
	
	public void addPoint(float x, float y) {
		path.add(new PVector(x, y));
	}
	
	public void addPoint() {
		path.add(current);
		current = new PVector();
	}
	
	public void show() {
		stroke(255, 90);
		strokeWeight(1);
		noFill();
		beginShape();
		
		for(PVector v : path) {
			vertex(v.x, v.y);
		}
		
		endShape();
	}
	
	public void reset() {
		path = new ArrayList<PVector>();
	}
}
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "lissajous_table" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
