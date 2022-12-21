class Liner {
	int lineSize = 4;
	
	float atAngle = 0;
	float nextAngle;
	float angleProgress = 0;
	float anglesPerUpdate = 3;
	float radius = 20;
	PVector atVec;
	
	int onOff;
	
	float pct;
	float angle;
	
	void newNextAngle() {
		if(onOff == 1) {
			nextAngle = random((360 * 2), (360 * 6));
			anglesPerUpdate = 12;
		} else {
			nextAngle = random(45, 160);
			anglesPerUpdate = 3;
		}
		
		angleProgress = 0;
	}
	
	Liner() {
		atAngle = random(0, 359);
		
		onOff = round(random(1));
		
		newNextAngle();
		
		//atVec = new PVector((width * 0.5), (height * 0.5));
		atVec = new PVector(random(width), random(height));
		
		//radius = random(5, 25);
		//radius = 100;
		radius = (lineSize * 12);
	}
	
	void update() {
		angleProgress += anglesPerUpdate;
		
		if(angleProgress >= nextAngle) {
			atAngle = (atAngle + nextAngle);
			
			atVec.set(
				(atVec.x + (cos( radians( atAngle ) ) * radius)),
				(atVec.y + (sin( radians( atAngle ) ) * radius))
			);
			
			if(atVec.x > width) {
				atVec.x -= width;
			} else if(atVec.x < 0) {
				atVec.x += width;
			}
			
			if(atVec.y > height) {
				atVec.y -= height;
			} else if(atVec.y < 0) {
				atVec.y += height;
			}
			
			atAngle = ((atAngle + 180) % 360);
			
			newNextAngle();
			
			pct = 0;
			angle = atAngle;
		} else {
			pct = (angleProgress / nextAngle);
			angle = (atAngle + (nextAngle * pct));
		}
	}
	
	void draw() {
		// origin
		//float origin_pct = ((1 - pct) * Easing.easeOutQuad((1 - pct)));
		float origin_pct = (1 - pct);
		
		PVector originVec = new PVector(
			(cos( radians( atAngle ) ) * (radius * origin_pct)),
			(sin( radians( atAngle ) ) * (radius * origin_pct))
		);
		
		color fill_col = color(80);
		
		pushMatrix();
		
		
		translate(atVec.x, atVec.y);
		
		pushStyle();
		fill(fill_col);
		stroke(fill_col);
		strokeWeight(lineSize);
		
		line(0, 0, originVec.x, originVec.y);
		popStyle();
		
		pushStyle();
		noStroke();
		fill(fill_col);
		ellipseMode(CENTER);
		ellipse(originVec.x, originVec.y, (lineSize * 2), (lineSize * 2));
		popStyle();
		popMatrix();
	}
	
	void drawForeground() {
		// progress
		//float progress_pct = (pct * Easing.easeOutQuad(pct));
		float progress_pct = pct;
		PVector progressVec;
		
		//color fill_col = color(255, 255, 255);
		color fill_col;
		
		if(onOff == 1) {
			fill_col = color(6, 229, 78);
			
			progressVec = new PVector(
				(cos( radians( angle ) ) * (radius * progress_pct)),
				(sin( radians( angle ) ) * (radius * progress_pct))
			);
		} else {
			fill_col = color(21, 107, 193);
			
			progressVec = new PVector(
				(cos( radians( ((atAngle + nextAngle) % 360) ) ) * (radius * progress_pct)),
				(sin( radians( ((atAngle + nextAngle) % 360) ) ) * (radius * progress_pct))
			);
		}
		
		
		
		pushMatrix();
		
		translate(atVec.x, atVec.y);
		
		pushStyle();
		fill(fill_col);
		stroke(fill_col);
		strokeWeight(lineSize);
		
		line(0, 0, progressVec.x, progressVec.y);
		popStyle();
		
		
		pushStyle();
		noStroke();
		fill(fill_col);
		ellipseMode(CENTER);
		ellipse(0, 0, (lineSize * 2), (lineSize * 2));
		popStyle();
		
		pushStyle();
		noStroke();
		fill(fill_col);
		ellipseMode(CENTER);
		ellipse(progressVec.x, progressVec.y, (lineSize * 2), (lineSize * 2));
		
		popStyle();
		
		
		popMatrix();
	}
}