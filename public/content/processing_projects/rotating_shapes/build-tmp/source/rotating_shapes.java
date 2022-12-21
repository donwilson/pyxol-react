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

public class rotating_shapes extends PApplet {

int num_points = 3;
float rotation_angle = 0.0f;
float rotate_angle_per_frame = 3.0f;
float radius = 240;

public void setup() {
	
}

public void draw() {
	background(10);
	
	translate((width / 2), (height / 2));
	
	rotateY(radians(-90));
	rotateY(radians(-rotation_angle));
	
	fill(color(255, 0, 0));
	noStroke();
	beginShape();
	
	float angle_per_point = (360.0f / (float)num_points);
	
	for(int pt = 1; pt <= num_points; pt++) {
		float angle = (angle_per_point * pt);
		
		float x = (cos(radians(-90 + angle)) * radius);
		float y = (sin(radians(-90 + angle)) * radius);
		
		vertex(x, y, 1);
	}
	
	endShape();
	
	rotation_angle += rotate_angle_per_frame;
	
	if(rotation_angle >= 180) {
		rotation_angle = 0;
		num_points++;
	}
	
	if(frameCount == 50) { save("preview.png"); }
}
  public void settings() { 	size(600, 600, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "rotating_shapes" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
