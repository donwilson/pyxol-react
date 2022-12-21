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

public class stars extends PApplet {

ArrayList<Star> stars = new ArrayList<Star>();

int frames = 0;
int frame_group = 30;
int edges = 4;

public void setup() {
	
}

public void draw() {
	background(30, 30, 30, (100 * 0.1f));
	
	// add another star every frame_group
	if((frames % frame_group) == 0) {
		stars.add(new Star(0.3f, 1.0f, edges));
	}
	
	// update stars
	int num_stars = stars.size();
	for(int i = (stars.size() - 1); i >= 0; i--) {
		Star star = stars.get(i);
		
		star.update(3, (frames % 360));
		star.render();
		
		if(star.reachedMax()) {
			stars.remove(i);
		}
	}
	
	frames++;
	
	if(frameCount == 450) { save("preview.png"); }
}
class Star {
	float r_min;
	float r_max;
	float rotation = 0.0f;
	float rotation_speed = 3.0f;
	int step;
	int num_edges = 4;
	float strokeW = 4.0f;
	float life_ratio = 0;   // 0-1 range of life of star
	int col_start = color(6, 229, 78);
	int col_mid = color(145, 11, 198);
	int col_end = color(21, 107, 193);
	
	Star(float r_min_, float r_max_, int num_edges_) {
		r_min = r_min_;
		r_max = r_max_;
		num_edges = num_edges_;
	}
	
	public void setStrokeWeight(float strokeW_) {
		strokeW = strokeW_;
	}
	
	public void setStrokeWeight(int strokeW_) {
		strokeW = PApplet.parseFloat(strokeW_);
	}
	
	public void tmp_setRotation(float rotation_) {
		rotation = rotation_;
	}
	
	public void tmp_setStep(int step_) {
		step = step_;
	}
	
	public void update(int step_amt, float rotation_) {
		step += step_amt;
		
		float n = (r_min * step);
		float d = (dist(0, 0, (width / 2), (height / 2)) + (strokeW / 2));
		
		life_ratio = (n / d);
		
		rotation = (rotation_ + map(life_ratio, 0, 1, 0, 30.0f));
	}
	
	public void render() {
		float step_min = (r_min * step);
		float step_max = (r_max * step);
		
		pushMatrix();
		pushStyle();
		
		translate((width / 2), (height / 2));
		
		noFill();
		
		float mid_point = 0.3f;
		float fade_up = 0.05f;
		
		if(life_ratio < 0.3f) {
			int col = lerpColor(
				col_start,
				col_mid,
				map(life_ratio, 0.0f, mid_point, 0, 1)
			);
			
			float col_alpha = 1.0f;
			
			if(life_ratio <= fade_up) {
				col_alpha = map(life_ratio, 0, fade_up, 0, 1);
			}
			
			stroke(col, (255 * col_alpha));
		} else {
			stroke(lerpColor(
				col_mid,
				col_end,
				map(life_ratio, mid_point, 1.0f, 0, 1)
			));
		}
		
		strokeWeight(strokeW);
		
		beginShape();
		
		float angle_per_i = (360.0f / (num_edges * 2));
		
		for(int i = 0; i < (num_edges * 2); i++) {
			float at_angle = (angle_per_i * i);
			
			if((i % 2) == 1) {
				// max
				vertex(
					angleX(at_angle, step_max),
					angleY(at_angle, step_max)
				);
			} else {
				// min
				vertex(
					angleX(at_angle, step_min),
					angleY(at_angle, step_min)
				);
			}
		}
		
		endShape(CLOSE);
		
		popStyle();
		popMatrix();
	}
	
	public float angleX(float angle, float rad) {
		return (cos(radians( (-90.0f + rotation + angle) )) * rad);
	}
	
	public float angleY(float angle, float rad) {
		return (sin(radians( (-90.0f + rotation + angle) )) * rad);
	}
	
	public boolean reachedMax() {
		return (life_ratio > 1);
	}
}
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "stars" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
