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

public class dot_bands extends PApplet {

ArrayList<Dot> dots;
PVector blackhole;

int num_dots = 24;
float box_size = 50;

public void setup() {
	
	
	dots = new ArrayList<Dot>();
	blackhole = new PVector(320, 620);
	
	for(int i = 0; i < num_dots; i++) {
		float rand_x = random(50, 590);
		float rand_y = random(50, 270);
		
		dots.add(new Dot(rand_x, rand_y, blackhole, box_size));
	}
}

public void draw() {
	background(30);
	
	for(Dot dot : dots) {
		dot.update();
		dot.draw();
	}
	
	// draw blackhole
	pushMatrix();
	pushStyle();
	
	
	int col_blue = color(21, 107, 193);
	int col_green = color(6, 229, 78);
	
	//fill(color(0));
	fill(color(30));
	noStroke();
	//stroke(col_green);
	//strokeWeight(2);
	ellipseMode(CENTER);
	ellipse(blackhole.x, blackhole.y, 12, 12);
	
	popStyle();
	popMatrix();
	
	if(frameCount == 250) { save("preview.png"); }
}
	
	class Dot {
		PVector center;
		
		PVector location;
		PVector velocity;
		PVector acceleration;
		
		float max_speed = 8;
		float max_force = 0.1f;
		
		PVector target;
		
		ArrayList<Trailer> trailers;
		PVector hole;
		
		float radius = 12;
		float box_size = 50;
		
		int trailer_every_num_frames = 1;
		
		int col_blue = color(21, 107, 193);
		int col_green = color(6, 229, 78);
		
		Dot(float x_, float y_, PVector hole_, float box_size_) {
			center = new PVector(x_, y_);
			
			location = new PVector(x_, y_);
			velocity = new PVector(0, 0);
			acceleration = new PVector(0, 0);
			
			newTarget();
			
			trailers = new ArrayList<Trailer>();
			
			hole = hole_.copy();
			box_size = box_size_;
		}
		
		public void newTarget() {
			PVector new_target_offset = PVector.random2D();
			new_target_offset.mult((box_size * 0.5f));
			
			target = center.copy();
			target.add(new_target_offset);
		}
		
		public boolean atTarget() {
			return (PVector.dist(location, target) <= radius);
		}
		
		public void applyForce(PVector force) {
			acceleration.add(force);
		}
		
		public void seek() {
			PVector desired = PVector.sub(target, location);
			desired.normalize();
			desired.mult(max_speed);
			
			PVector steer = PVector.sub(desired, velocity);
			steer.limit(max_force);
			
			applyForce(steer);
		}
		
		public void update() {
			seek();
			
			velocity.add(acceleration);
			velocity.limit(max_speed);
			location.add(velocity);
			acceleration.mult(0);
			
			// track movement on trail
			if((frameCount % trailer_every_num_frames) == 0) {
				trailers.add(new Trailer(location.x, location.y, hole));
			}
			
			if(atTarget()) {
				// met its goal, set a new target
				newTarget();
			}
		}
		
		public void draw() {
			/*// draw container box
			pushMatrix();
			pushStyle();
			
			stroke(color(255, 0, 0));
			noFill();
			rect(
				(center.x - (box_size * 0.5)),
				(center.y - (box_size * 0.5)),
				box_size,
				box_size
			);
			
			popStyle();
			popMatrix();*/
			
			
			// trailers
			for(Trailer trailer : trailers) {
				trailer.update();
				trailer.draw();
			}
			
			// clean out any trailers that finished finding their hole
			for(int i = (trailers.size() - 1); i >= 0; i--) {
				Trailer trailer = trailers.get(i);
				
				if(trailer.finished()) {
					trailers.remove(i);
				}
			}
			
			// draw dot
			pushMatrix();
			pushStyle();
			
			noStroke();
			fill(col_green);
			ellipseMode(CENTER);
			
			
			
			ellipse(location.x, location.y, radius+abs(velocity.x), radius+abs(velocity.y));
			
			popStyle();
			popMatrix();
			
			
			/*// draw target
			pushMatrix();
			pushStyle();
			
			noStroke();
			fill(color(100));
			ellipseMode(CENTER);
			ellipse(target.x, target.y, radius, radius);
			
			popStyle();
			popMatrix();*/
		}
	}
	
	
	class Trailer {
		PVector init;
		
		PVector location;
		PVector velocity;
		PVector acceleration;
		
		float max_speed = 2;
		float max_force = 0.1f;
		
		PVector target;
		
		float max_goal_dist = 5;
		
		float radius = 2;
		float max_rel_dist = 70;
		float max_rel_radius = 10;
		
		int fill_color;
		int col_blue = color(21, 107, 193);
		int col_green = color(6, 229, 78);
		
		Trailer(float x_, float y_, PVector hole_) {
			init = new PVector(x_, y_);
			
			location = new PVector(x_, y_);
			velocity = new PVector(0, 0);
			acceleration = new PVector(0, 0);
			
			target = hole_.copy();
			
			//fill_color = color(red(col_blue), green(col_blue), blue(col_blue), (100 * 0.4));
			fill_color = col_blue;
		}
		
		public boolean finished() {
			return (PVector.dist(location, target) <= max_goal_dist);
		}
		
		public void applyForce(PVector force) {
			acceleration.add(force);
		}
		
		public void seek() {
			PVector desired = PVector.sub(target, location);
			desired.normalize();
			desired.mult(max_speed);
			
			PVector steer = PVector.sub(desired, velocity);
			steer.limit(max_force);
			
			
			applyForce(steer);
		}
		
		public void update() {
			seek();
			
			velocity.add(acceleration);
			velocity.limit(max_speed);
			location.add(velocity);
			acceleration.mult(0);
		}
		
		public void draw() {
			pushMatrix();
			pushStyle();
			
			noStroke();
			
			float rel_dist = PVector.dist(init, location);
			float rel_radius = radius;
			int rel_color = fill_color;
			
			if(rel_dist <= max_rel_dist) {
				float relative_size = norm(rel_dist, 0, max_rel_dist);
				
				rel_radius = map(relative_size, 0, 1, max_rel_radius, radius);
				rel_color = lerpColor(color(21, 193, 147), fill_color, relative_size);
				//rel_color = color(red(rel_color), green(rel_color), blue(rel_color), (100 * map(relative_size, 0, 1, 0.5, 1)));
			}
			
			float targ_dist = PVector.dist(location, target);
			float max_targ_dist = 50;
			
			if(targ_dist <= max_targ_dist) {
				rel_radius = map(targ_dist, max_targ_dist, 0, rel_radius, 1);
				rel_color = lerpColor(rel_color, color(30), (1 - (targ_dist / max_targ_dist)));
			}
			
			fill(rel_color);
			ellipseMode(CENTER);
			ellipse(location.x, location.y, rel_radius, rel_radius);
			
			popStyle();
			popMatrix();
		}
	}
	
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "dot_bands" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
