class Blip {
	PVector location;
	PVector velocity;
	PVector acceleration;
	String uniq_id;
	float maxDist = 36.0;
	boolean invisible = false;
	
	int num_neighbors;
	ArrayList<PVector> neighbors;
	
	Blip() {
		location = new PVector(random(0, width), random(0, height));
		
		init();
	}
	
	Blip(float x_, float y_) {
		location = new PVector(x_, y_);
		
		init();
	}
	
	void init() {
		velocity = new PVector(0, 0);
		acceleration = PVector.random2D();
		acceleration.mult(0.3);
		
		genRandID();
		
		neighbors = new ArrayList<PVector>();
	}
	
	void genRandID() {
		uniq_id = "" + char( int( random(33,126) ) ) + char( int( random(33,126) ) ) + char( int( random(33,126) ) ) + char( int( random(33,126) ) ) + char( int( random(33,126) ) ) + char( int( random(33,126) ) );
	}
	
	void stopAcceleration() {
		acceleration.set(0, 0);
		invisible = true;
	}
	
	void setAcceleration(PVector vel_) {
		acceleration = vel_.copy();
	}
	
	void setAcceleration(float x_, float y_) {
		acceleration.set(x_, y_);
	}
	
	void update() {
		// movement
		velocity.add(acceleration);
		velocity.limit(1);
		
		location.add(velocity);
		
		if(location.x > width) {
			location.x -= width;
		} else if(location.x < 0) {
			location.x += width;
		}
		
		if(location.y > height) {
			location.y -= height;
		} else if(location.y < 0) {
			location.y += height;
		}
	}
	
	void countNeighbors(ArrayList<Blip> neighbors_) {
		for(int i = 0; i < neighbors_.size(); i++) {
			Blip other_blip = neighbors_.get(i);
			
			if(uniq_id.equals(other_blip.uniq_id)) {
				continue;
			}
			
			float distance = dist(
				location.x,
				location.y,
				other_blip.location.x,
				other_blip.location.y
			);
			
			if(distance <= maxDist) {
				PVector other_loc = other_blip.location.copy();
				
				neighbors.add(other_loc);
			}
		}
	}
	
	void drawLines() {
		pushMatrix();
		pushStyle();
		
		noFill();
		
		if(invisible) {
			stroke(color(225,255,23));
		} else {
			stroke(color(18,123,229));
		}
		
		num_neighbors = 0;
		
		// lines
		if(neighbors.size() > 0) {
			for(int i = (neighbors.size() - 1); i >= 0; i--) {
				PVector other_loc = neighbors.get(i);
				
				line(
					location.x,
					location.y,
					other_loc.x,
					other_loc.y
				);
				
				neighbors.remove(i);
				
				num_neighbors++;
			}
		}
		
		popStyle();
		popMatrix();
	}
	
	void draw() {
		if(invisible) {
			return;
		}
		
		// dot
		pushMatrix();
		pushStyle();
		
		float radius = (1.0 + num_neighbors);
		
		fill(color(225,255,23));
		noStroke();
		
		ellipse(location.x, location.y, radius, radius);
		
		popStyle();
		popMatrix();
	}
}