	
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
			
			grav = new PVector(random(-0.03, 0.03), random(0.1, 0.3));
			
			particles = new ArrayList<PVector>();
			
			pos = new PVector(x, y);
			//vel = vel_default.copy();
			vel = new PVector(0, random(-12.5, -20));
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
		
		void applyForce(PVector force) {
			acc.add(force);
		}
		
		void update() {
			applyForce(grav);
			
			vel.add(acc);
			pos.add(vel);
			acc.mult(0);
			
			//println(vel.y);
		}
		
		void show(Cluster obj) {
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
						point((particle_dist * particle.x * (0.75 + particle.z)), (particle_dist * particle.y * (0.75 + particle.z)));
					}
					
					pop();
					
					particle_dist += particle_speed;
					
					explosion_frames++;
				} else {
					is_exploded_done = true;
				}
			}
		}
		
		boolean outOfBounds() {
			return ((this.pos.x < -15) || (this.pos.x > (width + 15)) || (this.pos.y > (height + 15)));
		}
		
		boolean isDone() {
			//return (exploded || outOfBounds());
			return is_exploded_done;
		}
	}
	