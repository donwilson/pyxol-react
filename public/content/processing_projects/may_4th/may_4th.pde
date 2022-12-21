int num_stars = 500;
Star stars[] = new Star[num_stars];

PVector star_vel;

float max_radius = 7;
float lower_radius = 4.0;
float medium_radius = 7.0;

boolean is_moving = false;
Asteroid asteroid;

void push() {
	pushMatrix();
	pushStyle();
}

void pop() {
	popStyle();
	popMatrix();
}

void setup() {
	size(640, 640);
	
	frameRate(23.976);   // IG
	
	// star velocity
	star_vel = new PVector(-0.1, 0.1);
	
	// Orion
	createOrion();
	
	// create rest of star field
	for(int i = 7; i < num_stars; i++) {
		stars[ i ] = new Star();
	}
	
	// asteroid
	asteroid = new Asteroid();
}

void draw() {
	background(0, 50);
	
	for(int i = 0; i < num_stars; i++) {
		stars[ i ].update();
		stars[ i ].render();
	}
	
	asteroid.update();
	asteroid.render();
	
	if(frameCount == 50) { save("preview.png"); }
}

void mousePressed() {
	is_moving = !is_moving;
}



void createOrion() {
	// colors
	color col_betelgeuse = color(255, 166, 33);
	color col_rigel = color(81, 172, 227);
	color col_other = color(50, 116, 160);
	
	// overall size
	float orion_width = 91;
	float orion_height = 153;
	
	// offset
	float offset_x = random(0, (width - orion_width));
	float offset_y = random(0, (height - orion_height));
	
	// betelgeuse
	stars[0] = new Star((0.0 + offset_x), (0.0 + offset_y), (max_radius * 1.2));
	stars[0].setColor(col_betelgeuse);
	stars[0].setMove(false);
	
	// rigel
	stars[1] = new Star((91.0 + offset_x), (140.0 + offset_y), (max_radius * 1.2));
	stars[1].setColor(col_rigel);
	stars[1].setMove(false);
	
	// others
	stars[2] = new Star((68.0 + offset_x), (11.0 + offset_y), (max_radius * 1.1));
	stars[2].setColor(col_other);
	stars[2].setMove(false);
	stars[3] = new Star((53.0 + offset_x), (70.0 + offset_y), (max_radius * 1.1));
	stars[3].setColor(col_other);
	stars[3].setMove(false);
	stars[4] = new Star((43.5 + offset_x), (78.0 + offset_y), (max_radius * 1.1));
	stars[4].setColor(col_other);
	stars[4].setMove(false);
	stars[5] = new Star((33.5 + offset_x), (84.5 + offset_y), (max_radius * 1.1));
	stars[5].setColor(col_other);
	stars[5].setMove(false);
	stars[6] = new Star((18.0 + offset_x), (153.5 + offset_y), (max_radius * 1.1));
	stars[6].setColor(col_other);
	stars[6].setMove(false);
}

// CLASS: Star

class Star {
	PVector pos;
	PVector vel;
	float radius;
	boolean twinkles = false;
	float twinkle_state = 1.0;
	float twinkle_step = (-1 * (random(0.004, 0.05)));
	color col = color(255);
	boolean can_move = true;
	
	Star() {
		pos = new PVector(random(width), random(height));
		
		if(random(1) < 0.05) {
			radius = max_radius;
		} else {
			radius = random(lower_radius, medium_radius);
			
			if(random(1) < 0.8) {
				twinkles = true;
			}
		}
	}
	
	Star(float x_, float y_, float radius_) {
		pos = new PVector(x_, y_);
		radius = radius_;
		
		if((radius < max_radius) && random(1) < 0.8) {
			twinkles = true;
			
			//twinkle_step = (-1 * random(0.001, 0.02));
		}
	}
	
	void setLocation(float x_, float y_) {
		pos.x = x_;
		pos.y = y_;
	}
	
	void setRadius(float radius_) {
		radius = radius_;
	}
	
	void setColor(color col_) {
		col = col_;
	}
	
	void setTwinkling(boolean twinkles_) {
		twinkles = twinkles_;
	}
	
	void setMove(boolean can_move_) {
		can_move = can_move_;
	}
	
	void update() {
		if(is_moving && can_move) {
			pos.add(star_vel);
			
			if(pos.x < 0) {
				pos.x = width;
			} else if(pos.x > width) {
				pos.x = 0;
			}
			
			if(pos.y < 0) {
				pos.y = height;
			} else if(pos.y > height) {
				pos.y = 0;
			}
		}
		
		if(twinkles) {
			twinkle_state += twinkle_step;
			
			if(twinkle_state < 0) {
				twinkle_step *= -1;
				twinkle_state = 0.0;
			} else if(twinkle_state > 1) {
				twinkle_step *= -1;
				twinkle_state = 1.0;
			}
		}
	}
	
	void render() {
		push();
		
		translate(pos.x, pos.y);
		
		noStroke();
		
		if(twinkles) {
			fill(color(red(col), green(col), blue(col), (100 * twinkle_state)));
		} else {
			fill(color(col));
		}
		
		ellipseMode(CENTER);
		ellipse(0, 0, radius, radius);
		
		pop();
	}
}


class Asteroid {
	PVector vel;
	float speed_per_frame = 6.0;
	float frames_to_next_start;
	PVector pos = new PVector();
	float radius = (max_radius + random(1, 2));
	
	Asteroid() {
		newVelocity();
		newDelay();
	}
	
	void newVelocity() {
		vel = PVector.random2D();
		
		// make sure velocity is always going bottom left
		vel.x = -1 * abs(vel.x);
		vel.y = abs(vel.y);
		vel.mult(speed_per_frame);
	}
	
	void newDelay() {
		frames_to_next_start = (frameRate * random(6.0, 8.0));
	}
	
	void update() {
		if(frames_to_next_start > 0) {
			frames_to_next_start--;
			
			if(frames_to_next_start > 0) {
				return;
			}
			
			// started
			
			if(random(1) < 0.5) {
				// top
				pos.x = random(width * 0.1, width * 0.8);
				pos.y = 0;
			} else {
				// right
				pos.x = width;
				pos.y = random(height * 0.1, height * 0.8);
			}
			
			return;
		}
		
		pos.add(vel);
		
		if((pos.x < -40) || (pos.y < -40) || (pos.x > (width + 40)) || (pos.y > (height + 40))) {
			newVelocity();
			newDelay();
		}
	}
	
	void render() {
		push();
		
		ellipseMode(CENTER);
		
		// trail
		PVector trail_vel = vel.copy();
		//trail_vel.mult(1.1);
		PVector trail_pos = pos.copy();
		int num_trail_bits = 30;
		
		for(int i = 0; i < num_trail_bits; i++) {
			trail_vel.mult(0.9);
			trail_pos.sub(trail_vel);
			
			noStroke();
			//fill(color(255, 255, 255, map(i, 0, (num_trail_bits - 1), 100, 0)));
			fill(map(i, 0, (num_trail_bits - 1), 70, 0), map(i, 0, (num_trail_bits - 1), 255, 0));
			
			float trail_radius = map(i, 0, (num_trail_bits - 1), radius, lower_radius);
			
			ellipse(trail_pos.x, trail_pos.y, trail_radius, trail_radius);
		}
		
		// asteroid
		noStroke();
		fill(255);
		ellipse(pos.x, pos.y, radius, radius);
		
		pop();
	}
}