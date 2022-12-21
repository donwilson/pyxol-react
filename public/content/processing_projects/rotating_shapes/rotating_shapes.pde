int num_points = 3;
float rotation_angle = 0.0;
float rotate_angle_per_frame = 3.0;
float radius = 240;

void setup() {
	size(600, 600, P3D);
}

void draw() {
	background(10);
	
	translate((width / 2), (height / 2));
	
	rotateY(radians(-90));
	rotateY(radians(-rotation_angle));
	
	fill(color(255, 0, 0));
	noStroke();
	beginShape();
	
	float angle_per_point = (360.0 / (float)num_points);
	
	for(int pt = 1; pt <= num_points; pt++) {
		float angle = (angle_per_point * pt);
		
		float x = (cos(radians(-90 + angle)) * radius);
		float y = (sin(radians(-90 + angle)) * radius);
		
		vertex(x, y, 1);
	}
	
	endShape();
	
	rotation_angle += rotate_angle_per_frame;
	
	if(rotation_angle >= 180) {
		rotation_angle = 0;
		num_points++;
	}
	
	if(frameCount == 50) { save("preview.png"); }
}