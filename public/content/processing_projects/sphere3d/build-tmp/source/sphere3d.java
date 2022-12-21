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

public class sphere3d extends PApplet {

Cell[] cells = new Cell[1500];
float aa;

public void setup() {
	
	
	
	for(int i = 0; i < cells.length; i++) {
		cells[ i ] = new Cell(i, cells.length);
	}
}

public void draw() {
	background(30);
	ortho();
	lights();
	//ambientLight(255, 255, 255, 0, -1, 0);
	
	translate((width / 2), (height / 2), 0);
	
	// debug
	//push();
	////fill(255, 0, 0);
	//noStroke();
	//sphere(2);
	//pop();
	
	for(Cell cell : cells) {
		//cell.update();
		cell.draw();
	}
	
	aa += 0.01f;
	
	if(frameCount == 50) { save("preview.png"); }
}

public void push() { pushMatrix(); pushStyle(); }
public void pop() { popStyle(); popMatrix(); }
class Cell {
	float a_x = 0;
	float a_z = 0;
	float radius = 218;
	float size = 1;
	float walk_amt = 5;
	boolean debug = false;
	//PVector[] trails = new PVector[20];
	ArrayList<PVector> trails = new ArrayList<PVector>();
	int max_trail_length = 20;
	
	Cell(int i_, int max_) {
		a_x = random(0, 360);
		a_z = map(i_, 0, max_, 0, 180);
	}
	
	public void enableDebug() { debug = true; }
	public void disableDebug() { debug = false; }
	
	public void update() {
		// a_x = ((a_x + 0.7) % 360);
		// a_z = ((a_z + (noise((a_x * 0.1), aa) * 0.9)) % 180);
		a_x = ((a_x + 0.7f) % 360);
		a_z = ((a_z + (noise((a_x * 0.1f), aa) * 0.4f)) % 180);
		
		float x = (radius * cos(radians(a_x)) * sin(radians(a_z)));
		float y = (radius * sin(radians(a_x)) * sin(radians(a_z)));
		float z = (radius * cos(radians(a_z)));
		
		trails.add(0, new PVector(x, y, z));
		
		if(trails.size() > max_trail_length) {
			trails.remove((trails.size() - 1));
		}
	}
	
	public void draw() {
		update();
		
		//push();
		
		//float x = (radius * cos(radians(a_x)) * sin(radians(a_z)));
		//float y = (radius * sin(radians(a_x)) * sin(radians(a_z)));
		//float z = (radius * cos(radians(a_z)));
		//translate(x, z, y);
		
		/*PVector trail = trails.get(0);
		
		translate(trail.x, trail.z, trail.y);*/
		
		int i = 0;
		
		for(PVector dot : trails) {
			push();
			
			translate(dot.x, dot.z, dot.y);
			
			if((dot.y < 0) || (i > 0)) {
				int dot_col = color(255, 255, 255);
				
				if(i > 0) {
					dot_col = lerpColor(color(150, 150, 150), color(50, 50, 50), norm(i, 1, (max_trail_length - 1)));
				}
				
				if(dot.y < 0) {
					dot_col = lerpColor(
						dot_col,
						color(red(dot_col), green(dot_col), blue(dot_col), 0.9f),
						norm(dot.y, -radius, radius)
					);
				}
				
				noFill();
				stroke(dot_col);
				
				point(0, 0, 0);
			} else {
				noStroke();
				sphereDetail(6);
				sphere(size);
			}
			
			pop();
			
			i++;
		}
		
		
		
		
		//if(debug) {
		//	push();
		//	
		//	translate(trail.x, trail.z, trail.y);
		//	
		//	textSize(8);
		//	text("x: "+ radians(a_x) +"\nz: "+ radians(a_z), 5, -20, 0);
		//	
		//	pop();
		//}
		
		//pop();
	}
}
  public void settings() { 	size(640, 640, P3D); 	noSmooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "sphere3d" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
