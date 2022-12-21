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

public class rotating_planes extends PApplet {

float plane_size = 60;
int num_planes = 0;
float offset = 0;

float aa = 0.0f;

public void setup() {
	
	
	colorMode(HSB, 100);
	//ortho();
	
	num_planes = (ceil( (width / plane_size) ) + 1);
	offset = ((width % plane_size)  / 2);
	println("offset: "+ offset);
}

public void draw() {
	background(0);
	
	translate(-offset, -offset);
	
	for(int y = 0; y < num_planes; y++) {
		for(int x = 0; x < num_planes; x++) {
			pushMatrix();
			pushStyle();
			
			float xx = ((float)plane_size * (float)x);
			float yy = ((float)plane_size * (float)y);
			
			translate(xx, yy);
			
			float distance = map(
				dist(xx, yy, width/2, height/2),
				0.0f,
				dist(0, 0, width/2, height/2),
				0.0f,
				100.0f
			);
			float distance_angle = map(
				distance,
				0.0f,
				100.0f,
				0.0f,
				360.0f
			);
			float angle = (distance_angle - aa);
			angle = (abs(angle) % 360);
			
			float col = map(angle, 0.0f, 360.0f, 100.0f, 0.0f);
			
			rotateX(radians(angle));
			//rotateY(radians(angle));
			//rotate(radians(angle), radians(angle), 0.0);
			
			noStroke();
			fill(color(col, 100, 100));
			
			box(plane_size, plane_size, plane_size/3);
			//float plane_mult = map(angle, 0.0, 360.0, 1.3, 1.0);
			//float calc_plane = ((float)plane_size * plane_mult);
			//box(calc_plane, calc_plane, calc_plane);
			
			popStyle();
			popMatrix();
		}
	}
	
	aa += 1;
	
	if(frameCount == 50) { save("preview.png"); }
}
  public void settings() { 	size(600, 600, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "rotating_planes" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
