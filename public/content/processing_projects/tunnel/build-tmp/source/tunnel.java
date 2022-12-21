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

public class tunnel extends PApplet {

int num_layers = 12;
float yyy = 0.0f;

int col_green;
int col_blue;

public void setup() {
	
	frameRate(30);
	col_green = color(6, 229, 78);
	col_blue = color(21, 107, 193);
}

public void draw() {
	background(30);
	
	translate(width/2, height/2);
	
	for(int j = 0; j < num_layers; j++) {
		float radius = map(j, 0, num_layers, (width * 1.3f), 10);
		float x = (map(j, 0, (num_layers - 1), 0, 150) * cos(radians(((yyy + (j * 10)) % 360))));
		float y = (map(j, 0, (num_layers - 1), 0, 150) * sin(radians(((yyy + (j * 10)) % 360))));
		
		pushMatrix();
		pushStyle();
		
		translate(x, y);
		ellipseMode(CENTER);
		fill(fi(j));
		stroke(color(30));
		strokeWeight(j*2);
		//noStroke();
		ellipse(0, 0, (radius * 1.1f), (radius * 1.1f));
		
		popStyle();
		popMatrix();
	}
	
	yyy += 4;
	
	if(frameCount == 50) { save("preview.png"); }
}

public int fi(float i) {
	if(i == (num_layers - 1)) {
		return col_green;
	}
	
	return lerpColor(col_blue, col_green, norm(i, 0, (num_layers * 1.5f)));
	//return color(floor(map(i, 0, (num_layers - 1), 0, 255)));
}
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "tunnel" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
