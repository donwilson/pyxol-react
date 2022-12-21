color bg;
color fg;
color col_start;
color col_end;

float aa = 0.0;
float color_speed = 1.0;

float max_distance = 150;
float tri_radius = 150;
float tri_step = 24.0;

float tri_width;
float tri_height;

void setup() {
	size(640, 640);
	
	bg = color(30);
	fg = color(255, 255, 255);
	
	col_start = color(21, 107, 193);
	col_end = color(6, 229, 78);
	
	tri_width = ((cos(radians(-90 + 120)) * tri_radius) - (cos(radians(-90 + 240)) * tri_radius));
	tri_height = ((sin(radians(-90 + 120)) * tri_radius) - (sin(radians(-90)) * tri_radius));
	
	//max_distance = dist(
	//	(width / 2),
	//	(height / 2),
	//	
	//);
}

color calcColor(float distance, int which_t) {
	float distance_pct = map(distance, 0, max_distance, 0, 100);
	//float animating_at = map(((distance_pct + aa) % 360), 0, 360, 0, 100);
	float animating_at = ((distance_pct + aa) % 100);
	
	//return color(animating_at, 100, 100);
	// return lerpColor(
	// 	col_start,
	// 	col_end,
	// 	(animating_at / 100)
	// );
	
	if(which_t == 1) {
		// bottom right
		return col_start;
	} else if(which_t == 2) {
		// bottom left
		return col_end;
	} else {
		// top
		return col_end;
	}
}

void draw() {
	background(bg);
	
	translate(
		(width / 2),
		(height / 2)
	);
	
	translate(0, (tri_height / 6));
	
	int num_tris = ceil((tri_radius / tri_step));
	
	// top triangle
	for(int t = 0; t < 3; t++) {
		pushMatrix();
		pushStyle();
		
		if(t == 0) {
			// top triangle
			translate(0, (-tri_height / 2));
		} else if(t == 1) {
			// bottom right triangle
			translate((tri_width / 2), (tri_height / 2));
		} else if(t == 2) {
			// bottom left triangle
			translate((-tri_width / 2), (tri_height / 2));
		}
		
		rotate(radians(120 * t));
		
		for(int i = 0; i < num_tris; i++) {
			float this_radius = (tri_radius - (tri_step * i));
			
			float stroke_weight = (float(i) + 1);
			
			for(int h = 2; h >= 0; h--) {
				pushMatrix();
				pushStyle();
				
				color col = fg;
				
				if(h == 0) {
					// bottom
					float distance = (max_distance - this_radius);
					col = calcColor(distance, t);
					rotate(radians(180));
				} else if(h == 1) {
					// top left
					rotate(radians(300));
				} else if(h == 2) {
					// top right
					rotate(radians(60));
				}
				
				if(i == (num_tris - 1)) {
					// need a triangle
					fill(col);
					noStroke();
					
					pushMatrix();
					
					triangle(
						0, 0,
						(cos(radians(90 + 120)) * 14),
						(sin(radians(90 + 120)) * 14),
						(cos(radians(90 + 240)) * 14),
						(sin(radians(90 + 240)) * 14)
					);
					
					popMatrix();
				} else if(i > 0) {
					// need a quad
					fill(col);
					noStroke();
					
					PVector left_cen = new PVector(
						(cos(radians(90 + 240)) * this_radius),
						(sin(radians(90 + 240)) * this_radius)
					);
					
					PVector right_cen = new PVector(
						(cos(radians(90 + 120)) * this_radius),
						(sin(radians(90 + 120)) * this_radius)
					);
					
					float half_stroke = (stroke_weight / 2);
					
					quad(
						round(left_cen.x - half_stroke - (i / 2)),
						round(left_cen.y + half_stroke),
						
						round(left_cen.x + i),
						round(left_cen.y - half_stroke),
						
						round(right_cen.x - i),
						round(right_cen.y - half_stroke),
						
						round(right_cen.x + half_stroke + (i / 2)),
						round(right_cen.y + half_stroke)
					);
				} else {
					// need a line
					noFill();
					stroke(col);
					strokeWeight(stroke_weight);
					
					line(
						(cos(radians(90 + 120)) * this_radius),
						(sin(radians(90 + 120)) * this_radius),
						(cos(radians(90 + 240)) * this_radius),
						(sin(radians(90 + 240)) * this_radius)
					);
				}
				
				popStyle();
				popMatrix();
			}
		}
		
		popStyle();
		popMatrix();
	}
	
	
	pushMatrix();
	
	translate(0, (tri_height / 6));
	
	for(int i = 0; i < num_tris; i++) {
		float this_radius = (tri_radius - (tri_step * i));
		
		noFill();
		
		//int line_step = round((( aa + map(i, 0, num_tris - 1, 0, 360) ) % 360));
		//color col = color(map(line_step, 0, 360, 0, 100), 100, 100);
		
		// bottom left
		strokeWeight(1);
		stroke(calcColor(this_radius, 2));
		
		line(
			(cos(radians(90)) * this_radius),
			(sin(radians(90)) * this_radius),
			(cos(radians(90 + 120)) * this_radius),
			(sin(radians(90 + 120)) * this_radius)
		);
		
		// top
		strokeWeight(1);
		stroke(calcColor(this_radius, 0));
		
		line(
			(cos(radians(90 + 120)) * this_radius),
			(sin(radians(90 + 120)) * this_radius),
			(cos(radians(90 + 240)) * this_radius),
			(sin(radians(90 + 240)) * this_radius)
		);
		
		// bottom right
		strokeWeight(1);
		stroke(calcColor(this_radius, 1));
		
		line(
			(cos(radians(90 + 240)) * this_radius),
			(sin(radians(90 + 240)) * this_radius),
			(cos(radians(90)) * this_radius),
			(sin(radians(90)) * this_radius)
		);
		
		/*triangle(
			(cos(radians(90)) * this_radius),
			(sin(radians(90)) * this_radius),
			(cos(radians(90 + 120)) * this_radius),
			(sin(radians(90 + 120)) * this_radius),
			(cos(radians(90 + 240)) * this_radius),
			(sin(radians(90 + 240)) * this_radius)
		);*/
	}
	
	popMatrix();
	
	aa += color_speed;
	
	if(frameCount == 50) { save("preview.png"); }
}

//void mousePressed() {
//	save("screenshot.jpg");
//}

/*void mouseWheel(MouseEvent event) {
	float amt = event.getCount();
	
	if(amt > 0) {
		tri_step -= 1.0;
	} else {
		tri_step += 1.0;
	}
	
	println("tri_step = "+ tri_step);
}*/