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

public class truchet extends PApplet {

ArrayList<Truchet> tiles;

float tile_size = 24;

public void setup() {
	
	
	tiles = new ArrayList<Truchet>();
	
	int num_y = ceil( (height / tile_size) );
	int num_x = ceil( (width / tile_size) );
	
	for(int y = 0; y < num_y; y++) {
		for(int x = 0; x < num_x; x++) {
			Truchet tile = new Truchet(
				(x * tile_size),
				(y * tile_size)
			);
			
			tiles.add(tile);
		}
	}
}

public void draw() {
	background(30);
	
	for(Truchet tile : tiles) {
		tile.update();
		tile.draw();
	}
	
	if(frameCount == 50) { save("preview.png"); }
}
class Truchet {
	float x;
	float y;
	int state;
	
	Truchet(float x_, float y_) {
		x = x_;
		y = y_;
		
		state = round(random(1));
	}
	
	public void update() {
		
	}
	
	public void draw() {
		pushMatrix();
		pushStyle();
		
		translate(x, y);
		
		fill(color(255));
		noStroke();
		rect(0, 0, tile_size, tile_size);
		
		noFill();
		stroke(color(0));
		strokeWeight( floor((tile_size * 0.1f)) );
		
		if(state == 1) {
			arc(
				tile_size, 0,
				tile_size, tile_size,
				HALF_PI, PI
			);
			
			arc(
				0, tile_size,
				tile_size, tile_size,
				(TWO_PI - HALF_PI), TWO_PI
			);
		} else {
			arc(
				tile_size, tile_size,
				tile_size, tile_size,
				PI, (TWO_PI - HALF_PI)
			);
			
			arc(
				0, 0,
				tile_size, tile_size,
				0, HALF_PI
			);
		}
		
		popStyle();
		popMatrix();
	}
}
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "truchet" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
