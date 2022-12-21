class SampleDot {
	float amount;
	float angle;
	
	float start_from_center = 10;
	int num_dots = 35;
	float divider = 8;
	float radius = 4;
	
	SampleDot(float amount_) {
		amount = amount_;
		angle = 0;
	}
	
	void update(float angleStep) {
		angle += angleStep;
	}
	
	void render() {
		pushMatrix();
		pushStyle();
		
		translate((width / 2), (height / 2));
		
		noStroke();
		
		float mapped_i = (map(amount, 0, 1, 0, num_dots) * 2);
		
		for(int i = 0; i < num_dots; i++) {
			float raw_radius = (start_from_center + (divider * i));
			
			float x = (cos(radians(-90 + angle)) * raw_radius);
			float y = (sin(radians(-90 + angle)) * raw_radius);
			
			if(i <= mapped_i) {
				color col = lerpColor(col_start, col_end, map(i, 0, num_dots, 0, 1));
				
				if(angle > 270) {
					float opacity = map(angle, 270, 359, 255, 0);
					
					fill(red(col), green(col), blue(col), opacity);
				} else {
					fill(col);
				}
				
				ellipseMode(CENTER);
				ellipse(x, y, radius, radius);
			}
		}
		
		popStyle();
		popMatrix();
	}
	
	boolean isDone() {
		return (angle >= 360);
	}
}