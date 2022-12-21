boolean SAVE_FRAMES = false;

int off = 0;
float radius = 300;
float aa = 0.01;
int num_lines = 300;

color near = color(60, 31, 15);
color far = color(28, 10, 1);

void setup() {
	size(640, 640);
}

void draw() {
	background(0);
	
	for(int f = 0; f < num_lines; f++) {
		float amt = 0.0;
		float angle_increase = 1;
		float step = (aa * float((f + off)));
		color col = lerpColor(near, far, (1 - norm(f, 0, num_lines)));
		
		float pn_init = noise(step, amt);
		
		float zoom = max(0.1, (7 * norm(f, 0, num_lines)));
		
		pushMatrix();
		pushStyle();
		
		beginShape();
		
		stroke(col);
		strokeWeight( map(f, 0, (num_lines - 1), 3, 10) );
		noFill();
		
		translate(width/2, height/2);
		
		for(float angle = 0; angle <= 360; angle += angle_increase) {
			float pn = noise(step, amt);
			
			if(angle > 345) {
				pn = map(angle, 346, 360, pn, pn_init);
			} else if(angle < 15) {
				pn = map(angle, 0, 14, pn_init, pn);
			}
			
			float px = ((radius * pn * zoom) * cos(radians(angle - 90)));
			float py = ((radius * pn * zoom) * sin(radians(angle - 90)));
			
			vertex(px, py);
			
			amt = (amt + aa);
		}
		
		endShape();
		
		popStyle();
		popMatrix();
	}
	
	off++;
	
	if(SAVE_FRAMES) {
		saveFrame("frames/line-######.jpg");
	}
	
	if(frameCount == 50) { save("preview.png"); }
}