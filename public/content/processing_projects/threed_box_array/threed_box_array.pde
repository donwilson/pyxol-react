int num_boxes = 10;

float box_size_init = 32.0;
float box_size_spacing = 32.0;
float box_stroke_weight = 3.0;
float rotate_speed = 0.3;

color col_start = color(21, 107, 193);
color col_end = color(6, 229, 78);

float frames_per_full_scene = 180;

float aa = 0.0;

void setup() {
	size(640, 640, P3D);
	//colorMode(HSB, 100);
	ortho();
}

void draw() {
	background(10);
	
	float raw_box_degrees, box_degrees;
	raw_box_degrees = ((frameCount / frames_per_full_scene) * 360);
	box_degrees = (raw_box_degrees % 360);
	
	translate((width / 2), (height / 2));
	
	float raw_rotate_percent = (raw_box_degrees % (360 * 3));
	float rotate_percent = (box_degrees / 359);
	
	if(raw_rotate_percent >= 720) {
		rotateY(radians(-box_degrees * 0.25 * Easing.easeOutCubic(rotate_percent)));
		rotateZ(radians(-box_degrees * 0.25 * Easing.easeOutCubic(rotate_percent)));
	} else if(raw_rotate_percent >= 360) {
		rotateY(radians(box_degrees * 0.25 * Easing.linear(rotate_percent)));
	}
	
	float degrees_per_box_count = (360 / num_boxes);
	
	for(int i = 0; i < num_boxes; i++) {
		pushMatrix();
		pushStyle();
		
		strokeWeight(box_stroke_weight);
		noFill();
		
		float this_box_size = (box_size_init + (box_size_spacing * i));
		float min_num_degrees = (degrees_per_box_count * i);
		
		float percent = constrain(map(box_degrees, min_num_degrees, (min_num_degrees + degrees_per_box_count), 0, 1), 0, 1);
		
		float size_increase;
		
		if(raw_rotate_percent >= 720) {
			size_increase = (box_size_spacing * percent);
		} else if(raw_rotate_percent >= 360) {
			size_increase = (box_size_spacing * Easing.easeInOutCubic(percent));
		} else {
			size_increase = (box_size_spacing * Easing.easeInQuad(percent));
		}
		
		this_box_size += size_increase;
		
		stroke(col_start);
		
		if((percent > 0.0) && (percent < 1.0)) {
			stroke(col_end);
			strokeWeight((box_stroke_weight * 1.5));
			//stroke(lerpColor(col_start, col_end, percent));
			
			if(i == (num_boxes - 1)) {
				this_box_size += (size_increase * 5);
				
				stroke(color(red(col_end), green(col_end), blue(col_end), (10 * (1 - percent))));
			}
		}
		
		box(this_box_size, this_box_size, this_box_size);
		
		popStyle();
		popMatrix();
	}
	
	aa += rotate_speed;
	
	if(frameCount == 440) { save("preview.png"); }
}

void scrolledDown() {
	rotate_speed -= 0.1;
	println("rotate_speed="+ rotate_speed);
}

void scrolledUp() {
	rotate_speed += 0.1;
	println("rotate_speed="+ rotate_speed);
}

void mouseWheel(MouseEvent event) {
	int amount = event.getCount();
	
	if(amount > 0) {
		scrolledDown();
	} else if(amount < 0) {
		scrolledUp();
	}
}