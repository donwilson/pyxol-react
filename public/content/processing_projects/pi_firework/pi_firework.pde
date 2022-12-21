ArrayList<Firework> fireworks;
//PVector gravity;
Cluster cluster;

int fireworkEveryNumFrames = 60;

void setup() {
	size(640, 640, P2D);
	//smooth(8);
	noSmooth();
	
	//firework = new Firework((float)random(30, (width - 30)), (float)height);
	fireworks = new ArrayList<Firework>();
	
	//gravity = new PVector(random(-0.03, 0.03), 0.2);
	
	cluster = new Cluster();
}

void draw() {
	background(30);
	
	if((frameCount % fireworkEveryNumFrames) == 0) {
		fireworks.add(new Firework((float)random(30, (width - 30)), (float)height));
	}
	
	for(int i = (fireworks.size() - 1); i >= 0; i--) {
		Firework firework = fireworks.get(i);
		
		//firework.applyForce(gravity);
		firework.update();
		firework.show(cluster);
		
		//if(firework.outOfBounds()) {
		//	println("outOfBounds");
		//	firework.setPos((float)random(30, (width - 30)), (float)height);
		//	
		//	gravity.x = random(-0.03, 0.03);
		//}
		
		if(firework.isDone()) {
			fireworks.remove(i);
		}
	}
	
	cluster.update();
	cluster.draw();
	
	// save frames
	//if(frameCount <= 1800) {
	//	saveFrame("shots/preview-####.png");
	//}
	//
	//if(frameCount == 1800) {
	//	println("done");
	//}
	
	//if(frameCount == 350) {
	//	save("preview.png");
	//}
}

void push() { pushMatrix(); pushStyle(); }
void pop() { popStyle(); popMatrix(); }