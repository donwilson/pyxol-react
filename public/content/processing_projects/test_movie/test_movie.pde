import processing.video.*;

Movie myMovie;
PGraphics pg;

boolean ready = false;

color bgcol;
color fgcol;

void setup() {
	size(640, 360);
	//colorMode(HSB, 255);
	
	bgcol = color(0, 0, 0);//bgcol = color(0, 102, 204);
	fgcol = color(6, 67, 129);
	
	pg = createGraphics(640, 360);
	
	myMovie = new Movie(this, "big_buck_bunny.mp4");
	myMovie.loop();
}

void draw() {
	background(bgcol);
	
	pg.beginDraw();
	
	if(ready) {
		pg.image(myMovie, 0, 0);
		
		pg.loadPixels();
		
		for(int yy = 0; yy < 36; yy++) {
			for(int xx = 0; xx < 64; xx++) {
				float total_r = 0;
				float total_b = 0;
				float total_g = 0;
				int total_bright = 0;
				int total_count = 0;
				
				float max_r = 0;
				float max_g = 0;
				float max_b = 0;
				
				float min_r = 255;
				float min_g = 255;
				float min_b = 255;
				
				int offset = ((yy * 10 * width) + (xx * 10));
				
				for(int y = 0; y < 10; y++) {
					for(int x = 0; x < 10; x++) {
						int i = (offset + x + (y * width));
						
						color pcol = pg.pixels[ i ];
						
						float pcr = red(pcol);
						float pcg = green(pcol);
						float pcb = blue(pcol);
						
						total_r += pcr;
						total_g += pcg;
						total_b += pcb;
						total_bright += brightness(pcol);
						
						max_r = max(max_r, pcr);
						max_g = max(max_g, pcg);
						max_b = max(max_b, pcb);
						
						min_r = min(min_r, pcr);
						min_g = min(min_g, pcg);
						min_b = min(min_b, pcb);
						
						total_count++;
					}
				}
				
				float avg_bright = (total_bright / total_count);
				
				float amt = norm(avg_bright, 0, 255);
				
				color avgcol = color(
					round(total_r / total_count),
					round(total_g / total_count),
					round(total_b / total_count)
				);
				
				color maxcol = color(max_r, max_g, max_b);
				color mincol = color(min_r, min_g, min_b);
				
				pushMatrix();
				pushStyle();
				
				translate((xx * 10), (yy * 10));
				
				if(xx < 16) {
					if(yy < 18) {
						// slashes
						translate((10 / 2), (10 / 2));
						
						stroke(maxcol);
						fill(maxcol);
						
						float len = (amt * 5);
						
						line(
							(sin(radians(255)) * len),
							(cos(radians(225)) * len),
							(sin(radians(45)) * len),
							(cos(radians(45)) * len)
						);
						
						// line(
						// 	(sin(radians(135)) * len),
						// 	(cos(radians(135)) * len),
						// 	(sin(radians(315)) * len),
						// 	(cos(radians(315)) * len)
						// );
					} else {
						// mini rects
						translate((10 / 2), (10 / 2));
						
						noStroke();
						fill(maxcol);
						
						rectMode(CENTER);
						rect(0, 0, (amt * 10), (amt * 10));
					}
				} else if(xx < 32) {
					if(yy < 18) {
						// black filled rects
						translate((10 / 2), (10 / 2));
						rectMode(CENTER);
						
						noStroke();
						fill(maxcol);
						rect(0, 0, 10, 10);
						
						noStroke();
						fill(color(0, 0, 0));
						rect(0, 0, ((1 - amt) * 10), ((1 - amt) * 10));
					} else {
						// mini circles
						translate((10 / 2), (10 / 2));
						
						noStroke();
						fill(maxcol);
						
						ellipseMode(CENTER);
						ellipse(0, 0, (amt * 10), (amt * 10));
					}
				} else if(xx < 48) {
					if(yy < 18) {
						// black filled negative rects
						translate((10 / 2), (10 / 2));
						rectMode(CENTER);
						
						noStroke();
						fill(maxcol);
						rect(0, 0, 10, 10);
						
						noStroke();
						fill(color(0, 0, 0));
						rect(0, 0, (amt * 10), (amt * 10));
					} else {
						// rect avg colors
						fill(avgcol);
						noStroke();
						
						rect(0, 0, 10, 10);
					}
				} else {
					if(yy < 18) {
						// rect max colors
						fill(maxcol);
						noStroke();
						
						rect(0, 0, 10, 10);
					} else {
						// rect min colors
						fill(mincol);
						noStroke();
						
						rect(0, 0, 10, 10);
					}
				}
				
				popStyle();
				popMatrix();
			}
		}
	}
	
	//image(pg, 0, 0);
	
	if(frameCount == 150) { save("preview.png"); }
}

void movieEvent(Movie m) {
	m.read();
	
	ready = true;
}