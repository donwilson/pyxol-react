ArrayList<Dot> dots;
PVector blackhole;

int num_dots = 24;
float box_size = 50;

void setup() {
	size(640, 640);
	
	dots = new ArrayList<Dot>();
	blackhole = new PVector(320, 620);
	
	for(int i = 0; i < num_dots; i++) {
		float rand_x = random(50, 590);
		float rand_y = random(50, 270);
		
		dots.add(new Dot(rand_x, rand_y, blackhole, box_size));
	}
}

void draw() {
	background(30);
	
	for(Dot dot : dots) {
		dot.update();
		dot.draw();
	}
	
	// draw blackhole
	pushMatrix();
	pushStyle();
	
	
	color col_blue = color(21, 107, 193);
	color col_green = color(6, 229, 78);
	
	//fill(color(0));
	fill(color(30));
	noStroke();
	//stroke(col_green);
	//strokeWeight(2);
	ellipseMode(CENTER);
	ellipse(blackhole.x, blackhole.y, 12, 12);
	
	popStyle();
	popMatrix();
	
	if(frameCount == 250) { save("preview.png"); }
}