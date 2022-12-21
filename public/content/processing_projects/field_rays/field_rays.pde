PVector[] dots;
PVector[] trajectories;

int scrolled_amt = 8;

// blue/green:
color c1 = color(6, 229, 78);
color c2 = color(21, 107, 193);

// size of range between each wave
int field_range = 16;

// option: toggle draw field
boolean draw_rect_field = true;


color black = color(0);

void setup() {
	size(640, 640);
	smooth(4);
	
	dots = new PVector[50];
	trajectories = new PVector[50];
	
	for(int i = 0; i < dots.length; i++) {
		dots[ i ] = new PVector(random(width), random(height));
		//dots[ i ].mult(width);
		
		trajectories[ i ] = PVector.random2D();
		trajectories[ i ].mult(random(2, 5));
	}
}

void draw() {
	background(30);
	
	// update dots
	for(int i = 0; i < dots.length; i++) {
		dots[ i ].add(trajectories[ i ]);
		
		if(dots[ i ].x > width) {
			dots[ i ].x = (width - (dots[ i ].x - width));
			
			trajectories[ i ].x *= -1;
		} else if(dots[ i ].x < 0) {
			dots[ i ].x = abs(dots[ i ].x);
			
			trajectories[ i ].x *= -1;
		}
		
		if(dots[ i ].y > height) {
			dots[ i ].y = (height - (dots[ i ].y - height));
			
			trajectories[ i ].y *= -1;
		} else if(dots[ i ].y < 0) {
			dots[ i ].y = abs(dots[ i ].y);
			
			trajectories[ i ].y *= -1;
		}
	}
	
	
	// draw raw pixel field
	loadPixels();
	
	for(int y = 0; y < height; y++) {
		int y_off = (y * width);
		
		for(int x = floor( (width / 2) ); x < width; x++) {
			int i = (x + y_off);
			
			pixels[ i ] = determineColor(x, y);
		}
	}
	
	updatePixels();
	
	// draw rect field
	//float div_size = 91.42857;   // good
	//float div_size = 53.33333;
	float div_size = (width / scrolled_amt);
	float cell_size = (width / div_size);
	float cell_size_half = (cell_size * 0.5);
	
	for(int yy = 0; yy < div_size; yy++) {
		for(int xx = 0; xx < (div_size / 2); xx++) {
			float x = ((xx * cell_size) + cell_size_half);
			float y = ((yy * cell_size) + cell_size_half);
			
			pushMatrix();
			pushStyle();
			
			translate(x, y);
			
			noStroke();
			fill(determineColor(x, y));
			
			rectMode(CENTER);
			rect(0, 0, cell_size, cell_size);
			
			popStyle();
			popMatrix();
		}
	}
	
	/*// overlay dots
	for(int i = 0; i < dots.length; i++) {
		pushMatrix();
		pushStyle();
		
		translate(dots[ i ].x, dots[ i ].y);
		
		fill(c1);
		noStroke();
		
		if(dots[ i ].x < (width / 2)) {
			// analog
			rectMode(CENTER);
			rect(0, 0, 6, 6);
		} else {
			// digital
			ellipseMode(CENTER);
			ellipse(0, 0, 6, 6);
		}
		
		popStyle();
		popMatrix();
	}*/
	
	if(frameCount == 50) { save("preview.png"); }
}

color determineColor(float x, float y) {
	// find smallest distance from spot and any dot
	float determined_num = float(width);
	
	for(int i = 0; i < dots.length; i++) {
		determined_num = min(determined_num, dist(x, y, dots[ i ].x, dots[ i ].y));
	}
	
	int real_num = round(determined_num);
	
	int offset = (real_num % field_range);
	
	int div = floor( float(real_num) / float(field_range) );
	float pect = (float(offset) / float(field_range));
	
	// if((div % 2) == 1) {
	// 	return lerpColor(black, c2, pect);
	// }
	
	//return color(map(offset, 0, (field_range - 1), 0, 255));
	//return lerpColor(black, c1, pect);
	
	color bg = lerpColor(c2, black, pect);
	
	return lerpColor(
		lerpColor(c1, bg, pect),
		bg,
		norm(div, 0, 5)
	);
}

/*void mouseWheel(MouseEvent event) {
	scrolled_amt += event.getCount();
	
	scrolled_amt = max(1, scrolled_amt);
	
	println("scrolled_amt = "+ scrolled_amt);
}*/