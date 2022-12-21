ArrayList<Star> stars = new ArrayList<Star>();

int frames = 0;
int frame_group = 30;
int edges = 4;

void setup() {
	size(640, 640);
}

void draw() {
	background(30, 30, 30, (100 * 0.1));
	
	// add another star every frame_group
	if((frames % frame_group) == 0) {
		stars.add(new Star(0.3, 1.0, edges));
	}
	
	// update stars
	int num_stars = stars.size();
	for(int i = (stars.size() - 1); i >= 0; i--) {
		Star star = stars.get(i);
		
		star.update(3, (frames % 360));
		star.render();
		
		if(star.reachedMax()) {
			stars.remove(i);
		}
	}
	
	frames++;
	
	if(frameCount == 450) { save("preview.png"); }
}