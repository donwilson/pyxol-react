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

public class angled_loop extends PApplet {

float box_size = 128;

public void setup() {
	
}

public void draw() {
	background(30);
	
	PGraphics pg = createGraphics(PApplet.parseInt(box_size), PApplet.parseInt(box_size));
	
	int col_black = color(30);
	int col_green = color(6, 229, 78);
	int col_blue = color(21, 107, 193);
	
	pg.beginDraw();
	pg.background(col_black);
	
	int num_stripes = 4;
	float box_size_diag = sqrt( ((box_size * box_size) + (box_size * box_size)) );
	float stripe_size = (box_size_diag / (num_stripes * 2));
	float frames_per_iteration = 120;
	float offset = ((stripe_size * 4) * ((frameCount % frames_per_iteration) / frames_per_iteration));
	
	pg.rotate(radians(-45));
	
	for(int i = 0; i <= (num_stripes + 1); i++) {
		float frame_offset = (offset + (stripe_size * (i * 2))) - (stripe_size * 2);
		
		pg.noStroke();
		
		if((i % 2) == 1) {
			pg.fill(col_blue);
		} else {
			pg.fill(col_green);
		}
		
		pg.rect(
			-box_size,
			frame_offset - stripe_size,
			(box_size + box_size),
			stripe_size
		);
	}
	
	
	pg.endDraw();
	
	
	float centerX = (width / 2);
	float centerY = (width / 2);
	
	drawSquaresAtXY(pg, centerX, centerY);
	drawSquaresAtXY(pg, centerX - (box_size * 2), centerY);
	drawSquaresAtXY(pg, centerX + (box_size * 2), centerY);
	drawSquaresAtXY(pg, centerX, centerY - (box_size * 2));
	drawSquaresAtXY(pg, centerX, centerY + (box_size * 2));
	
	drawSquaresAtXY(pg, centerX - (box_size * 2), centerY - (box_size * 2));
	drawSquaresAtXY(pg, centerX + (box_size * 2), centerY - (box_size * 2));
	drawSquaresAtXY(pg, centerX - (box_size * 2), centerY + (box_size * 2));
	drawSquaresAtXY(pg, centerX + (box_size * 2), centerY + (box_size * 2));
	
	
	// noFill();
	// stroke(color(255, 0, 0));
	// line((width / 2), 0, (width / 2), height);
	// line(0, (height / 2), width, (height / 2));
	
	if(frameCount == 50) {
		save("preview.png");
	}
}


public void drawSquaresAtXY(PGraphics pg, float x, float y) {
	pushMatrix();
	pushStyle();
	
	translate(x, y);
	
	// TL
	pushMatrix();
	pushStyle();
	
	image(pg, -box_size, -box_size);
	
	popStyle();
	popMatrix();
	
	
	// TR
	pushMatrix();
	pushStyle();
	
	translate(0, -box_size);
	rotate(radians(90));
	image(pg, 0, -box_size);
	
	popStyle();
	popMatrix();
	
	
	// BR
	pushMatrix();
	pushStyle();
	
	translate(box_size, 0);
	rotate(radians(180));
	image(pg, 0, -box_size);
	
	popStyle();
	popMatrix();
	
	
	// BL
	pushMatrix();
	pushStyle();
	
	//translate(box_size, 0);
	rotate(radians(270));
	image(pg, -box_size, -box_size);
	
	popStyle();
	popMatrix();
	
	popStyle();
	popMatrix();
}
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "angled_loop" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
