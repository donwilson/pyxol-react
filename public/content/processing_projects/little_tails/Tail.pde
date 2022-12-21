class Tail {
	PVector now;
	PVector[] past;
	
	float border = 12;
	
	Tail() {
		now = PVector.random2D();
		now.mult((width - (border * 2)));
		
		past = new PVector[100];
		
		for(int i = 0; i < past.length; i++) {
			past[ i ] = new PVector();
			past[ i ] = now.copy();
		}
	}
	
	void update() {
		PVector direction = PVector.random2D();
		direction.mult( random(5, 15) );
		
		now.add(direction);
		
		if(now.x > (width - border)) {
			now.x -= (now.x - (width - border));
		} else if(now.x < border) {
			now.x = (border + (border - now.x));
		}
		
		if(now.y > (height - border)) {
			now.y -= (now.y - (height - border));
		} else if(now.y < border) {
			now.y = (border + (border - now.y));
		}
	}
	
	color colorAt(float cax, float cay) {
		return color(
			map(cax, border, (width - border), 0, 255),
			map(cay, border, (height - border), 0, 255),
			map((cax * cay), (border * border), ((width - border) * (height - border)), 0, 255)
		);
	}
	
	void draw() {
		//pushMatrix();
		pushStyle();
		
		// tail
		beginShape();
		
		stroke(colorAt(now.x, now.y));
		strokeWeight(2);
		noFill();
		curveVertex(now.x, now.y);
		
		boolean opened = true;
		
		for(int i = 0; i < past.length; i++) {
			curveVertex(past[ i ].x, past[ i ].y);
			
			if((i % 3) == 0) {
				endShape();
				
				popStyle();
				
				opened = false;
				
				
				if(i < (past.length - 1)) {
					opened = true;
					
					pushStyle();
					
					beginShape();
					stroke(colorAt(past[ i ].x, past[ i ].y));
					strokeWeight(2);
					noFill();
					
					curveVertex(past[ i ].x, past[ i ].y);
				}
			}
		}
		
		if(opened) {
			endShape();
			
			popStyle();
		}
		
		// dot
		pushStyle();
		
		ellipseMode(CENTER);
		fill(colorAt(now.x, now.y));
		noStroke();
		
		ellipse(now.x, now.y, 8, 8);
		
		popStyle();
		//popMatrix();
		
		
		// reverse set tail values so the first value is ready to be set to $now
		float min_x = float(width);
		float min_y = float(height);
		
		float max_x = 0;
		float max_y = 0;
		
		for(int i = (past.length - 2); i >= 0; i--) {
			past[ (i + 1) ] = past[ i ].copy();
			
			min_x = min(min_x, past[ (i + 1) ].x);
			min_y = min(min_y, past[ (i + 1) ].y);
			
			max_x = max(max_x, past[ (i + 1) ].x);
			max_y = max(max_y, past[ (i + 1) ].y);
		}
		
		past[0] = now.copy();
		
		min_x = min(min_x, now.x);
		min_y = min(min_y, now.y);
		
		max_x = max(max_x, now.x);
		max_y = max(max_y, now.y);
		
		// outer box
		pushStyle();
		
		stroke(color(100));
		strokeWeight(2);
		noFill();
		
		rect(
			(min_x - 3),
			(min_y - 3),
			((max_x - min_x) + 6),
			((max_y - min_y) + 6)
		);
		
		popStyle();
	}
}