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

public class threedrings extends PApplet {

int rings = 15;
int thickness = 9;
int spacing = 32;

float[] ring_at;
float[] ring_size;
float[] ring_step;
int[] ring_colors;

public void generateRand() {
	for(int i = 0; i < rings; i++) {
		ring_at[ i ] = random(0, 360);
		ring_size[ i ] = random(45, 200);
		ring_step[ i ] = random(1, 6);
		
		if(random(0, 1) <= 0.5f) {
			ring_step[ i ] *= -1;
		}
		
		ring_colors[ i ] = color(random(0, 100), 80, 100);
	}
}

public void setup() {
	
	
	colorMode(HSB, 100);
	
	ring_at = new float[15];
	ring_size = new float[15];
	ring_step = new float[15];
	ring_colors = new int[15];
	
	generateRand();
}

public void draw() {
	background(color(0, 0, 10));
	
	push();
	
	translate((width / 2), (height / 2));
	
	for(int i = 0; i < rings; i++) {
		noFill();
		//stroke(color(map(i, 0, (rings - 1), 0, 100), 100, 100));
		stroke(ring_colors[ i ]);
		strokeWeight(thickness);
		
		float arc_diam = (32 + ((thickness + spacing) * i));
		
		arc(0, 0,
			arc_diam, arc_diam,
			radians( (-90 + ring_at[ i ]) ),
			radians( (-90 + ring_at[ i ] + ring_size[ i ]) )
		);
		
		ring_at[ i ] += ring_step[ i ];
	}
	
	pop();
	
	if(frameCount == 50) { save("preview.png"); }
}

public void mouseReleased() {
	generateRand();
}












// utils
public void push() { pushMatrix(); pushStyle(); }
public void pop() { popStyle(); popMatrix(); }
	
	class Blob {
		float cx;
		float cy;
		
		Blob(float cx_, float cy_) {
			cx = cx_;
			cy = cy_;
			
			
		}
		
		public void update() {
					}
		
		public void render() {
			push();
			
			
			
			pop();
		}
	}
	
  public void settings() { 	size(640, 640, P3D); 	smooth(8); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "threedrings" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
