int num_layers = 12;
float yyy = 0.0;

color col_green;
color col_blue;

void setup() {
	size(640, 640);
	frameRate(30);
	col_green = color(6, 229, 78);
	col_blue = color(21, 107, 193);
}

void draw() {
	background(30);
	
	translate(width/2, height/2);
	
	for(int j = 0; j < num_layers; j++) {
		float radius = map(j, 0, num_layers, (width * 1.3), 10);
		float x = (map(j, 0, (num_layers - 1), 0, 150) * cos(radians(((yyy + (j * 10)) % 360))));
		float y = (map(j, 0, (num_layers - 1), 0, 150) * sin(radians(((yyy + (j * 10)) % 360))));
		
		pushMatrix();
		pushStyle();
		
		translate(x, y);
		ellipseMode(CENTER);
		fill(fi(j));
		stroke(color(30));
		strokeWeight(j*2);
		//noStroke();
		ellipse(0, 0, (radius * 1.1), (radius * 1.1));
		
		popStyle();
		popMatrix();
	}
	
	yyy += 4;
	
	if(frameCount == 50) { save("preview.png"); }
}

color fi(float i) {
	if(i == (num_layers - 1)) {
		return col_green;
	}
	
	return lerpColor(col_blue, col_green, norm(i, 0, (num_layers * 1.5)));
	//return color(floor(map(i, 0, (num_layers - 1), 0, 255)));
}