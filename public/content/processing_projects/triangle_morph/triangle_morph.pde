float tri_size = 600;
int num_tris = 1;
float aa = 0.0;
float aa_inc = 1.0;
float aa_step = 60.0;

void setup() {
	size(640, 640);
	colorMode(HSB, 100);
	
	//num_tris = 17;
}

void draw() {
	background(10);
	
	float ow = tri_size;
	float oh = abs((sin(radians(-60)) * ow));
	
	float w = (tri_size / float(num_tris));
	float h = (oh / float(num_tris));
	
	PVector pos = new PVector(0.0, 0.0);
	
	pos.x += (width / 2);
	pos.y += ((height / 2) - (oh / 2));
	
	float step_ratio = ((aa % aa_step) / aa_step);
	
	for(int i = 1; i <= num_tris; i++) {
		PVector tri_pos = new PVector();
		tri_pos = pos.copy();
		
		for(int j = 0; j < i; j++) {
			beginShape();
			
			noStroke();
			fill(100);
			
			vertex(tri_pos.x, tri_pos.y);
			vertex(tri_pos.x - (w / 2), (tri_pos.y + h));
			vertex(tri_pos.x + (w / 2), (tri_pos.y + h));
			
			endShape(CLOSE);
			
			tri_pos.add(w, 0);
		}
		
		pos.add((w * -0.5), h);
	}
	
	aa += aa_inc;
	
	if(aa >= aa_step) {
		aa = 0.0;
		num_tris++;
		aa_step *= 0.9;
	}
	
	if(frameCount == 150) { save("preview.png"); }
}