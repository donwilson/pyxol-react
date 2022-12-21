float aa = 0;
float aa_step = 0.01;

void setup() {
	size(640, 640);
}

void draw() {
	background(255);
	
	translate((width / 2), (height / 2));
	
	float radius_init = 200;
	
	float nval = 0.0;
	float nstep = 0.01;
	float nmult = 100;
	
	float nstart = 0;
	
	for(int z = 10; z > 0; z--) {
		pushStyle();
		
		float radius = (radius_init * map(z, 10, 1, 1, 0.3));
		
		beginShape();
		
		stroke(0);
		strokeWeight(2);
		
		for(float i = 0; i < 360; i++) {
			nval += nstep;
			float n = noise(nval, aa);
			
			if(i == 0) {
				nstart = n;
			} else if(i >= 300) {
				float perc = map(i, 300, 359, 0, 1);
				n = lerp(n, nstart, perc);
			}
			
			float point_x = (cos(radians(i)) * (radius + (n * nmult)));
			float point_y = (sin(radians(i)) * (radius + (n * nmult)));
			
			vertex(point_x, point_y);
		}
		
		endShape(CLOSE);
		
		popStyle();
	}
	
	aa += aa_step;
	
	if(frameCount == 50) { save("preview.png"); }
}