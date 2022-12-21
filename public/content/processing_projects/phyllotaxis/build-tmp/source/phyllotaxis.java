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

public class phyllotaxis extends PApplet {

boolean RECORDING = false;

int scene = 0;
int frame = 0;
int n = 0;
float c = 2;
int divides = 7;
int num_renders;
float rect_size;
float[] angles;
float offset = 0.0f;
float spacing = 4;

public void push() {
	pushMatrix();
	pushStyle();
}

public void pop() {
	popStyle();
	popMatrix();
}

public void resetBoard() {
	scene++;
	frame = 0;
	n = 0;
	
	background(color(0, 0, 5));
	
	for(int i = 0; i < num_renders; i++) {
		angles[ i ] = random(1.0f, 359.0f);
	}
}

public void setup() {
	
	colorMode(HSB, 100);
	
	num_renders = (divides * divides);
	rect_size = floor((width / divides));
	
	offset = ((width - (rect_size * divides)) / 2);
	
	angles = new float[ num_renders ];
	
	resetBoard();
}

public void draw() {
	boolean finished = true;
	
	for(int i = 0; i < 2; i++) {
		for(int yy = 0; yy < divides; yy++) {
			for(int xx = 0; xx < divides; xx++) {
				push();
				translate((rect_size * xx) + offset, (rect_size * yy) + offset);
				
				
				//float a = (n * radians(map((xx + (yy * xx)), 0, ((divides * divides) - 1), 137.1, 137.9)));
				float a = (n * radians(angles[ (xx + (yy * divides)) ]));
				float r = (c * sqrt(n));
				
				float x = ((r * cos(a)) + (rect_size / 2));
				float y = ((r * sin(a)) + (rect_size / 2));
				
				float spacing_half = (spacing / 2);
				
				if((x >= spacing_half) && (x <= (rect_size - spacing_half)) && (y >= spacing_half) && (y <= (rect_size - spacing_half))) {
					noStroke();
					
					float realx = ((rect_size * xx) + offset + x);
					float realy = ((rect_size * yy) + offset + y);
					
					// fill(color(
					// 	map(
					// 		dist(realx, realy, width/2, height/2),
					// 		0,
					// 		dist(0, 0, width/2, height/2),
					// 		90,
					// 		10
					// 	),
					// 	100,
					// 	100
					// ));
					
					fill(color(
						map((xx + (yy * divides)), 0, (num_renders - 1), 90, 10),
						100,//map((y + (rect_size * yy)), 0, height, 100, 20),
						100
					));
					
					ellipse(x, y, c, c);
					
					finished = false;
				}
				
				pop();
			}
		}
		
		n++;
	}
	
	if(RECORDING) {
		//save("scene_"+ scene +"_"+ String.format("%04d", frame) +".png");
		saveFrame("output/scene_"+ scene +"_####.png");
	}
	
	if(finished) {
		resetBoard();
		delay(1000);
	}
	
	if(frameCount == 50) { save("preview.png"); }
}
  public void settings() { 	size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "phyllotaxis" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
