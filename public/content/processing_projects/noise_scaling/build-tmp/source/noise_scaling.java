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

public class noise_scaling extends PApplet {

float dd = 0.0f;
float step = 0.008f;

public void setup() {
	
}

public void draw() {
	background(0);
	
	int divider = 12;
	
	float[] noises_x = new float[12];
	float x_sum = 0.0f;
	
	for(int i = 0; i < noises_x.length; i++) {
		float xn = noise((dd + (i * step)), dd);
		//xn = map(xn, 0.2, 0.8, 0, 1);
		//xn *= xn;
		
		noises_x[ i ] = xn;
		x_sum += xn;
	}
	
	float lastx = (PApplet.parseFloat(divider) * 0.5f);
	
	for(int i = 0; i < noises_x.length; i++) {
		float xw = ((PApplet.parseFloat(width) - divider) * (noises_x[ i ] / x_sum));
		
		float[] noises_y = new float[12];
		float y_sum = 0.0f;
		
		for(int j = 0; j < noises_y.length; j++) {
			float yn = noise((dd + (i * step)), dd, (dd + (j * step)));
			//yn = map(yn, 0.2, 0.8, 0, 1);
			//yn *= yn;
			
			noises_y[ j ] = yn;
			y_sum += yn;
		}
		
		
		float lasty = (PApplet.parseFloat(divider) * 0.5f);
		
		for(int k = 0; k < noises_y.length; k++) {
			float yh = ((PApplet.parseFloat(height) - divider) * (noises_y[ k ] / y_sum));
			
			float sized_w = (xw - divider);
			float sized_h = (yh - divider);
			
			pushMatrix();
			
			translate(
				(lastx + (divider * 0.5f) + (sized_w * 0.5f)),
				(lasty + (divider * 0.5f) + (sized_h * 0.5f))
			);
			rotate(dd);
			
			noStroke();
			fill(color(255));
			
			rectMode(CENTER);
			
			rect(0, 0, sized_w*0.9f, sized_h*0.9f);
			
			/*rect(
				(lastx + (divider * 0.5)),
				(lasty + (divider * 0.5)),
				(xw - divider),
				(yh - divider)
			);*/
			
			popMatrix();
			
			lasty += yh;
		}
		
		lastx += xw;
	}
	
	
	/*float[] noises_x = new float[12];
	float x_sum = 0.0;
	
	for(int i = 0; i < noises_x.length; i++) {
		float xn = noise(step * i, 0, dd);
		xn = log(xn);
		
		noises_x[ i ] = xn;
		x_sum += xn;
	}
	
	int divider = 6;
	float lastx = (float(divider) * 0.5);
	
	for(int i = 0; i < noises_x.length; i++) {
		float xw = ((float(width) - divider) * (noises_x[ i ] / x_sum));
		
		noStroke();
		fill(color(255));
		rect(
			(lastx + (divider * 0.5)),
			divider,
			(xw - divider),
			(height - (divider * 2))
		);
		
		lastx += xw;
	}*/
	
	
	dd += step;//dd = (dd + step);
	
	if(frameCount == 50) { save("preview.png"); }
}
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "noise_scaling" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
