	
	class Trailer {
		PVector init;
		
		PVector location;
		PVector velocity;
		PVector acceleration;
		
		float max_speed = 2;
		float max_force = 0.1;
		
		PVector target;
		
		float max_goal_dist = 5;
		
		float radius = 2;
		float max_rel_dist = 70;
		float max_rel_radius = 10;
		
		color fill_color;
		color col_blue = color(21, 107, 193);
		color col_green = color(6, 229, 78);
		
		Trailer(float x_, float y_, PVector hole_) {
			init = new PVector(x_, y_);
			
			location = new PVector(x_, y_);
			velocity = new PVector(0, 0);
			acceleration = new PVector(0, 0);
			
			target = hole_.copy();
			
			//fill_color = color(red(col_blue), green(col_blue), blue(col_blue), (100 * 0.4));
			fill_color = col_blue;
		}
		
		boolean finished() {
			return (PVector.dist(location, target) <= max_goal_dist);
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
		}
		
		void draw() {
			pushMatrix();
			pushStyle();
			
			noStroke();
			
			float rel_dist = PVector.dist(init, location);
			float rel_radius = radius;
			color rel_color = fill_color;
			
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
	