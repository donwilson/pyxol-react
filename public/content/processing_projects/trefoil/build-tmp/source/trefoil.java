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

public class trefoil extends PApplet {

float r = 75;
float rotation_y = 0;

// x = r * cos(phi) * cos(theta)
// y = r * cos(phi) * sin(theta)
// z = r * sin(phi)

// r(beta) = 0.8 + 1.6 * sin(6 * beta)
// theta(beta) = 2 * beta
// phi(beta) = 0.6 * pi * sin(12 * beta)

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
	background(0);
	
	/*float beta = 0;
	
	push();
	
	translate(width/2, height/2);
	rotateY(rotation_y);
	
	beginShape();
	noFill();
	stroke(35, 100, 100);
	
	while(beta < PI) {
		float r = ((0.8 + 1.6 * sin(6 * beta)) * 200);
		float theta = (2 * beta);
		float phi = (0.6 * PI * sin(12 * beta));
		
		float x = r * cos(phi) * cos(theta);
		float y = r * cos(phi) * sin(theta);
		float z = r * sin(phi);
		
		vertex(x, y, z);
		
		beta += 0.01;
	}
	
	endShape();
	
	pop();
	*/
	
	push();
	translate(width/2, height/2);
	rotateY(rotation_y);
	noFill();
	strokeWeight(r);
	stroke(35, 100, 100);
	beginShape();
	
	for(int t = 0; t <= 360; t++) {
		float rad = radians(t);
		
		float x = ((sin(rad) + (2 * sin((2 * rad)))) * r);
		float y = ((cos(rad) - (2 * cos((2 * rad)))) * r);
		float z = ((-sin((3 * rad))) * r);
		
		vertex(x, y, z);
	}
	
	endShape();
	pop();
	
	rotation_y += 0.01f;
	
	if(frameCount == 50) { save("preview.png"); }
}
  public void settings() { 	size(600, 600, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "trefoil" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
