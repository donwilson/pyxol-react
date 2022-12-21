	
	class Dot {
		PVector center;
		
		PVector location;
		PVector velocity;
		PVector acceleration;
		
		float max_speed = 8;
		float max_force = 0.1;
		
		PVector target;
		
		ArrayList<Trailer> trailers;
		PVector hole;
		
		float radius = 12;
		float box_size = 50;
		
		int trailer_every_num_frames = 1;
		
		color col_blue = color(21, 107, 193);
		color col_green = color(6, 229, 78);
		
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
		
		void newTarget() {
			PVector new_target_offset = PVector.random2D();
			new_target_offset.mult((box_size * 0.5));
			
			target = center.copy();
			target.add(new_target_offset);
		}
		
		boolean atTarget() {
			return (PVector.dist(location, target) <= radius);
		}
		
		void applyForce(PVector force) {
			acceleration.add(force);
		}
		
		void seek() {
			PVector desired = PVector.sub(target, location);
			desired.normalize();
			desired.mult(max_speed);
			
			PVector steer = PVector.sub(desired, velocity);
			steer.limit(max_force);
			
			applyForce(steer);
		}
		
		void update() {
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
		
		void draw() {
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
	