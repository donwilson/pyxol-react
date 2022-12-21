	
	class Clock {
		int top_y;
		int right_x;
		int bottom_y;
		int left_x;
		int size;
		
		PVector center;
		
		int hour;
		float minute;
		float hour_line_size;
		float minute_line_size;
		
		float speed;
		
		boolean DEBUG = false;
		
		Clock(int left_x_, int top_y_, int size_) {
			DEBUG = false;
			
			left_x = left_x_;
			top_y = top_y_;
			
			size = size_;
			
			right_x = (left_x + size);
			bottom_y = (top_y + size);
			
			center = new PVector(
				(left_x + (size / 2)),
				(top_y + (size / 2))
			);
			
			hour = ceil(random(0, 11));
			minute = ceil(random(0, 59));
			
			hour_line_size = (float(size) * 0.5 * 0.7);
			minute_line_size = (float(size) * 0.5 * 0.9);
			
			speed = random(1, 2.3);
		}
		
		void update() {
			minute += speed;
			
			if(minute >= 60) {
				hour += 1;
				minute %= 60;
				
				hour %= 11;
			}
		}
		
		void draw() {
			pushMatrix();
			
			if(DEBUG) {
				pushStyle();
				
				stroke(255);
				noFill();
				rect(left_x, top_y, size, size);
				
				popStyle();
			}
			
			// clock face
			pushStyle();
			
			ellipseMode(CENTER);
			noFill();
			stroke(color(100));
			strokeWeight(2);
			ellipse(center.x, center.y, size, size);
			
			popStyle();
			
			//stroke(color(100));
			//strokeWeight(1);
			//fill(color(30));
			
			//rect(left_x, top_y, size, size);
			
			// minute
			pushStyle();
			stroke(color(150));
			strokeWeight(1);
			
			line(
				center.x,
				center.y,
				(center.x + (minute_line_size * cos(radians( minute * (360 / 60) )))),
				(center.y + (minute_line_size * sin(radians( minute * (360 / 60) ))))
			);
			popStyle();
			
			
			// hour
			pushStyle();
			stroke(color(255));
			strokeWeight(2);
			
			line(
				center.x,
				center.y,
				(center.x + (hour_line_size * cos(radians( hour * (360 / 12) )))),
				(center.y + (hour_line_size * sin(radians( hour * (360 / 12) ))))
			);
			
			popStyle();
			
			
			popMatrix();
		}
	}
	