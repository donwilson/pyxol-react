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

public class line_rainbow extends PApplet {

float aa = 0.0f;

public void setup() {
	
	colorMode(HSB, 100);
}

public void draw() {
	background(10);
	
	float margin = 20;
	
	int num_dots = 11;
	float spacing = ((width - (margin + margin)) / (num_dots - 1));
	
	float inner_width = (width - (margin + margin));
	
	for(int i = 0; i < num_dots; i++) {
		float x = (((margin + (spacing * i)) + aa) % inner_width);
		
		int col = color(map(i, 0, (num_dots - 1), 0, 100), 100, 100);
		
		stroke(col);
		strokeWeight(2);
		
		for(int j = 0; j < num_dots; j++) {
			float y = ((margin + (spacing * j)) - aa);
			
			while(y < margin) {
				y += inner_width;
			}
			
			line(
				(x + margin),
				margin,
				y,
				(height - margin)
			);
		}
	}
	
	// ellipseMode(CENTER);
	// fill(11, 100, 100);
	// noStroke();
	// for(int k = 0; k < num_dots; k++) {
	// 	ellipse((margin + (spacing * k)), margin, 4.0, 4.0);
	// 	ellipse((margin + (spacing * k)), (height - margin), 4.0, 4.0);
	// }
	
	aa += 1;
	
	if(frameCount == 50) { save("preview.png"); }
}
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "line_rainbow" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
