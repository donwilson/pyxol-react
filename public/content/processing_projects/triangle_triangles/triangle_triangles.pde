Shape[] shapes;
int num_tris = 17;
float tri_radius = 16.0;

int nth_triangle(int t) {
	return ((int(sq(float(t))) + t) / 2);
}

float nth_triangle(float t) {
	return ((sq(t) + t) / 2);
}

float triangleWidth(float radius) {
	return ((cos(radians(30)) - cos(radians(150))) * radius);
}

float triangleHeight(float radius) {
	return ((sin(radians(150)) - sin(radians(-90))) * radius);
}

void setup() {
	size(640, 640);
	colorMode(HSB, 100);
	
	shapes = new Shape[ nth_triangle(num_tris) ];
	//shapes = new Shape[ ((nth_triangle(num_tris) * 2) - num_tris) ];
	int si = 0;
	
	PVector pos = new PVector(0.0, 0.0);
	
	pos.x += (width / 2);
	pos.y += ((height / 2) - (((triangleHeight(tri_radius) * num_tris) / 2) - (triangleHeight(tri_radius) / 2)));
	
	for(int i = 1; i <= num_tris; i++) {
		PVector tri_pos = new PVector();
		tri_pos = pos.copy();
		
		for(int t = 0; t < i; t++) {
			shapes[ si ] = new Shape(ShapeType.TRIANGLE, tri_pos, tri_radius);
			
			si++;
			
			tri_pos.add(triangleWidth(tri_radius), 0.0);
		}
		
		pos.add((triangleWidth(tri_radius) * -0.5), triangleHeight(tri_radius));
	}
	
	/*// add upside down triangles
	int lsi = 0;
	
	for(int i = 1; i < num_tris; i++) {
		for(int t = 0; t < i; t++) {
			PVector u_pos = new PVector();
			u_pos = shapes[ lsi ].center.copy();
			u_pos.add(0, (triangleHeight(tri_radius) / 2) + 4.3);
			
			shapes[ si ] = new Shape(ShapeType.UPSIDE_DOWN_TRIANGLE, u_pos, tri_radius);
			
			si++;
			lsi++;
		}
	}*/
	
	// find most distant triangle from center (bottom right) and max distance on all
	PVector last_tri_center = new PVector();
	last_tri_center = shapes[ (nth_triangle(num_tris) - 1) ].center.copy();
	float max_dist = dist(last_tri_center.x, last_tri_center.y, (width / 2), (height / 2));
	
	for(int i = 0; i < shapes.length; i++) {
		shapes[ i ].setMaxDistance(max_dist);
	}
}

void draw() {
	background(20, 10, 10);
	
	for(int i = 0; i < shapes.length; i++) {
		shapes[ i ].update();
		shapes[ i ].render();
	}
	
	if(frameCount == 50) { save("preview.png"); }
}