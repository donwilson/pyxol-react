	
	class Dot {
		float x;
		float y;
		color col;
		float opacity = 0;   // 0-100
		float opacity_decrease_rate_per_frame = 2;
		
		Dot(int x_, int y_) {
			x = ((float)x_ + random(-1, 1));
			y = ((float)y_ + random(-1, 1));
			
			color col_blue = color(21, 107, 193);
			color col_green = color(6, 229, 78);
			
			//col = lerpColor(col_blue, col_green, norm((((float)x / (float)width) + ((float)y / (float)height)), 0, 2));
			col = lerpColor(col_blue, col_green, norm((((float)x / (float)width) + ((float)y / (float)height)), 0, 2));
		}
		
		void processExplosion(float ex, float ey, float eradius) {
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
		
		void draw() {
			if(opacity > 0) {
				push();
				
				int this_opacity = 100;
				
				if(opacity < 75) {
					this_opacity = floor((100 * norm(opacity, 0, 75)));
				}
				color coll = color(red(col), green(col), blue(col), (2.5 * this_opacity));
				
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
	