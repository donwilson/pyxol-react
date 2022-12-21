PGraphics pg;

int spacing = 24;
int margin = -100;
int num_dots;
float max_dist;

float[] dots;

float aa;
float bb;

void push() {
	pg.pushMatrix();
	pg.pushStyle();
}

void pop() {
	pg.popStyle();
	pg.popMatrix();
}

void setup() {
	colorMode(HSB, 100);
	size(600, 600);
	
	pg = createGraphics(width, height);
	
	num_dots = round( ((width - (margin * 2)) / spacing) );
	max_dist = dist(margin, margin, width/2, height/2);
	
	dots = new float[ (num_dots * num_dots) ];
	
	for(int iy = 0; iy < num_dots; iy++) {
		for(int ix = 0; ix < num_dots; ix++) {
			int j = ((iy * num_dots) + ix);
			
			int x = (margin + (ix * spacing));
			int y = (margin + (iy * spacing));
			
			float distance = dist(x, y, (width / 2), (height / 2));
			
			dots[ j ] = map(distance, 0, max_dist, 1, 360);
		}
	}
	
	aa = 270.0;
	bb = 1;
}

void draw() {
	pg.beginDraw();
	pg.background(10, 35);
	
	for(int iy = 0; iy < num_dots; iy++) {
		for(int ix = 0; ix < num_dots; ix++) {
			int x = (margin + (ix * spacing));
			int y = (margin + (iy * spacing));
			
			int j = ((iy * num_dots) + ix);
			
			push();
			pg.translate(x, y);
			
			float distance = dist(x, y, (width / 2), (height / 2));
			float distance_percent = (1 - (distance / max_dist));
			
			//float xx = (cos(radians(dots[ j ])) * ((sqrt(spacing) + sqrt(spacing)) * distance_percent));
			float xx = cos(radians(dots[ j ]));
			xx *= (spacing * 1.5);
			//float yy = (sin(radians(dots[ j ])) * ((sqrt(spacing) + sqrt(spacing)) * distance_percent));
			float yy = sin(radians(dots[ j ]));
			yy *= (spacing * 1.5);
			
			distance = dist((x + xx), (y + yy), width/2, height/2);
			color col = color(map(distance, 0, max_dist, 100, 0), 100, 100);
			
			
			pg.strokeWeight(spacing/2);
			pg.stroke(col);
			pg.noFill();
			pg.rectMode(CENTER);
			//pg.rect(spacing/2, spacing/2, spacing, spacing);
			pg.point(xx, yy);
			
			dots[ j ] += 6;
			
			pop();
		}
	}
	pg.endDraw();
	
	image(pg, 0, 0, 600, 600);
	
	if(frameCount == 50) { save("preview.png"); }
}