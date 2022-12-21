class Truchet {
	float x;
	float y;
	int state;
	
	Truchet(float x_, float y_) {
		x = x_;
		y = y_;
		
		state = round(random(1));
	}
	
	void update() {
		
	}
	
	void draw() {
		pushMatrix();
		pushStyle();
		
		translate(x, y);
		
		fill(color(255));
		noStroke();
		rect(0, 0, tile_size, tile_size);
		
		noFill();
		stroke(color(0));
		strokeWeight( floor((tile_size * 0.1)) );
		
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