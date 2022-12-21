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

public class triangle_morph extends PApplet {

float tri_size = 600;
int num_tris = 1;
float aa = 0.0f;
float aa_inc = 1.0f;
float aa_step = 60.0f;

public void setup() {
	
	colorMode(HSB, 100);
	
	//num_tris = 17;
}

public void draw() {
	background(10);
	
	float ow = tri_size;
	float oh = abs((sin(radians(-60)) * ow));
	
	float w = (tri_size / PApplet.parseFloat(num_tris));
	float h = (oh / PApplet.parseFloat(num_tris));
	
	PVector pos = new PVector(0.0f, 0.0f);
	
	pos.x += (width / 2);
	pos.y += ((height / 2) - (oh / 2));
	
	float step_ratio = ((aa % aa_step) / aa_step);
	
	for(int i = 1; i <= num_tris; i++) {
		PVector tri_pos = new PVector();
		tri_pos = pos.copy();
		
		for(int j = 0; j < i; j++) {
			beginShape();
			
			noStroke();
			fill(100);
			
			vertex(tri_pos.x, tri_pos.y);
			vertex(tri_pos.x - (w / 2), (tri_pos.y + h));
			vertex(tri_pos.x + (w / 2), (tri_pos.y + h));
			
			endShape(CLOSE);
			
			tri_pos.add(w, 0);
		}
		
		pos.add((w * -0.5f), h);
	}
	
	aa += aa_inc;
	
	if(aa >= aa_step) {
		aa = 0.0f;
		num_tris++;
		aa_step *= 0.9f;
	}
	
	if(frameCount == 150) { save("preview.png"); }
}
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "triangle_morph" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
