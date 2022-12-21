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

public class pi_firework extends PApplet {

ArrayList<Firework> fireworks;
//PVector gravity;
Cluster cluster;

int fireworkEveryNumFrames = 60;

public void setup() {
	
	//smooth(8);
	
	
	//firework = new Firework((float)random(30, (width - 30)), (float)height);
	fireworks = new ArrayList<Firework>();
	
	//gravity = new PVector(random(-0.03, 0.03), 0.2);
	
	cluster = new Cluster();
}

public void draw() {
	background(30);
	
	if((frameCount % fireworkEveryNumFrames) == 0) {
		fireworks.add(new Firework((float)random(30, (width - 30)), (float)height));
	}
	
	for(int i = (fireworks.size() - 1); i >= 0; i--) {
		Firework firework = fireworks.get(i);
		
		//firework.applyForce(gravity);
		firework.update();
		firework.show(cluster);
		
		//if(firework.outOfBounds()) {
		//	println("outOfBounds");
		//	firework.setPos((float)random(30, (width - 30)), (float)height);
		//	
		//	gravity.x = random(-0.03, 0.03);
		//}
		
		if(firework.isDone()) {
			fireworks.remove(i);
		}
	}
	
	cluster.update();
	cluster.draw();
	
	// save frames
	if(frameCount <= 1800) {
		saveFrame("shots/preview-####.png");
	}
	
	if(frameCount == 1800) {
		println("done");
	}
	
	//if(frameCount == 350) {
	//	save("preview.png");
	//}
}

public void push() { pushMatrix(); pushStyle(); }
public void pop() { popStyle(); popMatrix(); }
	
	class Cluster {
		Dot[] dots;
		ArrayList<FloatList> explosions;
		float explosion_radius_min = 400;
		float explosion_radius_max = 450;
		float explosion_frames = 60;
		
		Cluster() {
			// arraylist to record explosion points/states
			explosions = new ArrayList<FloatList>();
			
			// load image
			PImage pimg = loadImage("pi.jpg");
			
			// create dots array, pull random black pixel for each dot
			dots = new Dot[1700];
			
			for(int i = 0; i < dots.length; i++) {
				// pick random black pixel
				int px = 0;
				int py = 0;
				int attempts = 0;
				int max_attempts = 500;
				
				while((attempts < max_attempts) && (px == 0) && (py == 0)) {
					attempts++;
					px = floor(random(pimg.width));
					py = floor(random(pimg.height));
					
					int pcol = pimg.get(px, py);
					
					if(red(pcol) > 100) {
						px = 0;
						py = 0;
					} else {
						// check other dots
						if(i > 0) {
							for(int k = 0; k < i; k++) {
								float distance = dist(px, py, dots[ k ].x, dots[ k ].y);
								
								if(distance <= 9.8f) {
									px = 0;
									py = 0;
								}
							}
						}
					}
				}
				
				int dotpx = floor(px);//int dotpx = floor(map(px, 0, pimg.width, 0, width));
				int dotpy = floor(py);//int dotpy = floor(map(py, 0, pimg.height, 0, height));
				
				dots[ i ] = new Dot(dotpx, dotpy);
			}
		}
		
		public void explodeAt(float x, float y) {
			//println("explodeAt("+ x +","+ y +")");
			
			FloatList explosion = new FloatList();
			explosion.append((float)x);
			explosion.append((float)y);
			explosion.append(0.0f);
			explosion.append(random(explosion_radius_min, explosion_radius_max));
			
			explosions.add(explosion);
		}
		
		public void update() {
			// clear out finished explosions
			for(int i = explosions.size() - 1; i >= 0; i--) {
				FloatList exp = explosions.get(i);
				
				if(exp.get(2) >= 1) {
					explosions.remove(i);
				}
			}
			
			// increase explosion radius
			for(int i = 0; i < explosions.size(); i++) {
				FloatList exp = explosions.get(i);
				
				exp.set(2, (exp.get(2) + (1 / explosion_frames) ));
			}
		}
		
		public void draw() {
			for(int j = 0; j < dots.length; j++) {
				Dot dot = dots[j];
				
				for(int i = 0; i < explosions.size(); i++) {
					FloatList exp = explosions.get(i);
					
					dot.processExplosion(exp.get(0), exp.get(1), (exp.get(3) * exp.get(2)));
				}
				
				dot.draw();
			}
		}
	}
	
	
	class Dot {
		float x;
		float y;
		int col;
		float opacity = 0;   // 0-100
		float opacity_decrease_rate_per_frame = 2;
		
		Dot(int x_, int y_) {
			x = ((float)x_ + random(-1, 1));
			y = ((float)y_ + random(-1, 1));
			
			int col_blue = color(21, 107, 193);
			int col_green = color(6, 229, 78);
			
			//col = lerpColor(col_blue, col_green, norm((((float)x / (float)width) + ((float)y / (float)height)), 0, 2));
			col = lerpColor(col_blue, col_green, norm((((float)x / (float)width) + ((float)y / (float)height)), 0, 2));
		}
		
		public void processExplosion(float ex, float ey, float eradius) {
			// ex,ey = explosion epicenter
			// estate = 0-1 range of explosion state
			// eradius = max radius of explosion
			
			float raw_distance = dist(x, y, ex, ey);
			
			//if(raw_distance <= eradius) {
			//	opacity = 100;
			//}
			
			// shouldn't always be on if in radius of explosion, so set range
			if(abs((raw_distance - eradius)) <= 5) {
				opacity = 100;
			}
		}
		
		public void draw() {
			if(opacity > 0) {
				push();
				
				int this_opacity = 100;
				
				if(opacity < 75) {
					this_opacity = floor((100 * norm(opacity, 0, 75)));
				}
				int coll = color(red(col), green(col), blue(col), (2.5f * this_opacity));
				
				//stroke(coll);
				//strokeWeight(4);
				//point(x, y);
				translate(x, y);
				
				fill(coll);
				noStroke();
				rect(-4, -4, 8, 8);
				//ellipseMode(CENTER);
				//ellipse(0, 0, 4, 4);
				pop();
				
				opacity = max(0, (opacity - opacity_decrease_rate_per_frame));
			}
		}
	}
	
	
	class Firework {
		//PVector vel_default;
		PVector pos;
		PVector vel;
		PVector acc;
		PVector grav;
		PVector exploded_at;
		
		boolean exploded = false;
		ArrayList<PVector> particles;
		float particle_dist = 0;
		float particle_speed = 5;
		int explosion_frames = 0;
		int explosion_frame_limit = 30;
		boolean is_exploded_done;
		
		Firework(float x, float y) {
			//vel_default = new PVector(0, random(-12, -25));
			
			grav = new PVector(random(-0.03f, 0.03f), random(0.1f, 0.3f));
			
			particles = new ArrayList<PVector>();
			
			pos = new PVector(x, y);
			//vel = vel_default.copy();
			vel = new PVector(0, random(-12.5f, -20));
			acc = new PVector(0, 0);
		}
		
		//void setPos(float x, float y) {
		//	//println("setPos.old="+ pos.x +","+ pos.y +" :: new="+ x +","+ y);
		//	pos = new PVector(x, y);
		//	vel = vel_default.copy();
		//	acc.mult(0);
		//	
		//	exploded = false;
		//}
		
		public void applyForce(PVector force) {
			acc.add(force);
		}
		
		public void update() {
			applyForce(grav);
			
			vel.add(acc);
			pos.add(vel);
			acc.mult(0);
			
			//println(vel.y);
		}
		
		public void show(Cluster obj) {
			if(vel.y < 6) {
				push();
				
				stroke(255);
				strokeWeight(4);
				point(pos.x, pos.y);
				
				pop();
			} else {
				if(!exploded) {
					// explode here
					exploded_at = new PVector(pos.x, pos.y);
					
					obj.explodeAt(pos.x, pos.y);
					
					int num_particles = floor(random(10, 25));
					
					for(int i = 0; i < num_particles; i++) {
						particles.add(PVector.random3D());
					}
					
					exploded = true;
				}
				
				if(explosion_frames < explosion_frame_limit) {
					push();
					
					translate(exploded_at.x, exploded_at.y);
					
					stroke(color(255, 255, 255, map(explosion_frames, 0, explosion_frame_limit, 255, 30)));
					strokeWeight(2);
					
					for(PVector particle : particles) {
						point((particle_dist * particle.x * (0.75f + particle.z)), (particle_dist * particle.y * (0.75f + particle.z)));
					}
					
					pop();
					
					particle_dist += particle_speed;
					
					explosion_frames++;
				} else {
					is_exploded_done = true;
				}
			}
		}
		
		public boolean outOfBounds() {
			return ((this.pos.x < -15) || (this.pos.x > (width + 15)) || (this.pos.y > (height + 15)));
		}
		
		public boolean isDone() {
			//return (exploded || outOfBounds());
			return is_exploded_done;
		}
	}
	
  public void settings() { 	size(640, 640, P2D); 	noSmooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "pi_firework" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
