class Star {
	float r_min;
	float r_max;
	float rotation = 0.0;
	float rotation_speed = 3.0;
	int step;
	int num_edges = 4;
	float strokeW = 4.0;
	float life_ratio = 0;   // 0-1 range of life of star
	color col_start = color(6, 229, 78);
	color col_mid = color(145, 11, 198);
	color col_end = color(21, 107, 193);
	
	Star(float r_min_, float r_max_, int num_edges_) {
		r_min = r_min_;
		r_max = r_max_;
		num_edges = num_edges_;
	}
	
	void setStrokeWeight(float strokeW_) {
		strokeW = strokeW_;
	}
	
	void setStrokeWeight(int strokeW_) {
		strokeW = float(strokeW_);
	}
	
	void tmp_setRotation(float rotation_) {
		rotation = rotation_;
	}
	
	void tmp_setStep(int step_) {
		step = step_;
	}
	
	void update(int step_amt, float rotation_) {
		step += step_amt;
		
		float n = (r_min * step);
		float d = (dist(0, 0, (width / 2), (height / 2)) + (strokeW / 2));
		
		life_ratio = (n / d);
		
		rotation = (rotation_ + map(life_ratio, 0, 1, 0, 30.0));
	}
	
	void render() {
		float step_min = (r_min * step);
		float step_max = (r_max * step);
		
		pushMatrix();
		pushStyle();
		
		translate((width / 2), (height / 2));
		
		noFill();
		
		float mid_point = 0.3;
		float fade_up = 0.05;
		
		if(life_ratio < 0.3) {
			color col = lerpColor(
				col_start,
				col_mid,
				map(life_ratio, 0.0, mid_point, 0, 1)
			);
			
			float col_alpha = 1.0;
			
			if(life_ratio <= fade_up) {
				col_alpha = map(life_ratio, 0, fade_up, 0, 1);
			}
			
			stroke(col, (255 * col_alpha));
		} else {
			stroke(lerpColor(
				col_mid,
				col_end,
				map(life_ratio, mid_point, 1.0, 0, 1)
			));
		}
		
		strokeWeight(strokeW);
		
		beginShape();
		
		float angle_per_i = (360.0 / (num_edges * 2));
		
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
	
	float angleX(float angle, float rad) {
		return (cos(radians( (-90.0 + rotation + angle) )) * rad);
	}
	
	float angleY(float angle, float rad) {
		return (sin(radians( (-90.0 + rotation + angle) )) * rad);
	}
	
	boolean reachedMax() {
		return (life_ratio > 1);
	}
}