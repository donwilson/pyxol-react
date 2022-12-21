class Curve {
	ArrayList<PVector> path;
	PVector current;
	
	Curve() {
		path = new ArrayList<PVector>();
		current = new PVector();
	}
	
	void setX(float x_) {
		current.x = x_;
	}
	
	void setY(float y_) {
		current.y = y_;
	}
	
	void addPoint(float x, float y) {
		path.add(new PVector(x, y));
	}
	
	void addPoint() {
		path.add(current);
		current = new PVector();
	}
	
	void show() {
		stroke(255, 90);
		strokeWeight(1);
		noFill();
		beginShape();
		
		for(PVector v : path) {
			vertex(v.x, v.y);
		}
		
		endShape();
	}
	
	void reset() {
		path = new ArrayList<PVector>();
	}
}