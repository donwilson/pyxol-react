class Spot {
	int x;
	int y;
	PFont font;
	int font_size;
	float letter_width;
	float letter_height;
	float noise_d;
	int color_scale;
	
	int color_val;
	int color_val_mid;   // mid point of color
	String letter;
	
	float connectionLineSize = 2.0;
	
	PVector center;
	
	ArrayList<PVector> neighbors;
	boolean drawingConnections = false;
	
	Spot(int x_, int y_, PFont font_, int font_size_, float noise_d_, int color_scale_) {
		x = x_;
		y = y_;
		
		font = font_;
		font_size = font_size_;
		letter_width = (font_size * 0.8);
		letter_height = (font_size * 0.92);
		
		noise_d = noise_d_;
		color_scale = color_scale_;
		
		// calculate center point
		center = new PVector(
			((x * letter_width) + (letter_width * 0.5)),
			((y * letter_height) + (letter_height * 0.5))
		);
		
		calcNeighbors();
	}
	
	void update(float d) {
		float noise_val = noise((x * noise_d), (y * noise_d), d);
		noise_val = map(noise_val, 0.3, 0.7, 0, 1);
		//noise_val = map(noise_val, 0.3, 0.7, 0, 1);
		
		color_val = round( (noise_val * color_scale) );
		
		updateLetter();
	}
	
	void enableConnections() { drawingConnections = true; }
	void disableConnections() { drawingConnections = false; }
	
	boolean isDrawingConnections() {
		return drawingConnections;
	}
	
	void draw(float d) {
		if(drawingConnections) {
			//drawConnections();
			drawComplexConnections();
		} else {
			drawLetter();
		}
	}
	
	void drawLetter() {
		pushMatrix();
		pushStyle();
		
		noStroke();
		fill(color_val_mid, color_scale, color_scale);
		
		textAlign(CENTER, CENTER);
		textFont(font);
		textSize(font_size);
		textLeading(0);
		text(letter, center.x, center.y);
		
		popStyle();
		popMatrix();
	}
	
	void drawConnections() {
		pushMatrix();
		pushStyle();
		
		int num_connections = 0;
		
		if(neighbors.size() > 0) {
			for(PVector neighbor_vector : neighbors) {
				Spot neighbor = spots[ int(neighbor_vector.y) ][ int(neighbor_vector.x) ];
				
				if(letter.equals(neighbor.letter)) {
					// same color letter, make connection
					if(neighbor.isDrawingConnections()) {
						// neighbor is accepting connections
						pushStyle();
						
						stroke(color_val_mid, color_scale, color_scale);
						fill(color_val_mid, color_scale, color_scale);
						
						strokeWeight(connectionLineSize);
						strokeCap(ROUND);
						
						line(
							center.x,
							(center.y - y_off),
							neighbor.center.x,
							(neighbor.center.y - y_off)
						);
						
						popStyle();
						
						num_connections++;
					}
				}
			}
		}
		
		if(num_connections == 0) {
			// no connections made, make a center dot
			pushStyle();
			
			ellipseMode(CENTER);
			
			noStroke();
			fill(color_val, color_scale, color_scale);
			
			ellipse(
				center.x,
				(center.y - y_off),
				(font_size / 3),
				(font_size / 3)
			);
			
			popStyle();
		}
		
		popStyle();
		popMatrix();
	}
	
	void drawComplexConnections() {
		pushMatrix();
		pushStyle();
		
		ArrayList<PVector> connections = new ArrayList<PVector>();
		
		if(neighbors.size() > 0) {
			for(PVector neighbor_vector : neighbors) {
				Spot neighbor = spots[ int(neighbor_vector.y) ][ int(neighbor_vector.x) ];
				
				if(letter.equals(neighbor.letter)) {
					// same color letter, make connection
					if(neighbor.isDrawingConnections()) {
						// neighbor is accepting connections
						connections.add(neighbor.center);
					}
				}
			}
		}
		
		if(connections.size() > 2) {
			// complex shape
			noStroke();//strokeWeight(1);
			//stroke(color_val_mid, color_scale, color_scale);
			fill(color_val_mid, color_scale, color_scale);
			
			//strokeWeight(connectionLineSize);
			//strokeCap(ROUND);
			
			beginShape(TRIANGLE_STRIP);
			
			vertex(center.x, center.y - y_off + 3);
			
			for(PVector neighbor_center : connections) {
				vertex(neighbor_center.x, neighbor_center.y - y_off + 3);
			}
			
			endShape(CLOSE);
		} else if(connections.size() > 1) {
			// complex shape
			//noStroke();//strokeWeight(1);
			stroke(color_val_mid, color_scale, color_scale);
			fill(color_val_mid, color_scale, color_scale);
			
			//strokeWeight(connectionLineSize);
			//strokeCap(ROUND);
			
			beginShape();
			
			vertex(center.x, center.y - y_off + 3);
			
			for(PVector neighbor_center : connections) {
				vertex(neighbor_center.x, neighbor_center.y - y_off + 3);
			}
			
			endShape(CLOSE);
		} else/* if(connections.size() > 0) {
			// line
			stroke(color_val_mid, color_scale, color_scale);
			fill(color_val_mid, color_scale, color_scale);
			
			strokeWeight(connectionLineSize);
			strokeCap(ROUND);
			
			PVector neighbor = connections.get(0);
			
			line(
				center.x,
				(center.y - y_off),
				neighbor.x,
				(neighbor.y - y_off)
			);
		} else*/ {
			/*// no connections made, make a center dot
			ellipseMode(CENTER);
			
			noStroke();
			fill(color_val, color_scale, color_scale);
			
			ellipse(
				center.x,
				(center.y - y_off),
				(font_size * 0.25),
				(font_size * 0.25)
			);*/
		}
		
		popStyle();
		popMatrix();
	}
	
	void calcNeighbors() {
		neighbors = new ArrayList<PVector>();
		
		// max x/y values possible for comparing neighbors
		int x_max = (floor( (width / letter_width) ) - 1);
		int y_max = (floor( (height / letter_height) ) - 1);
		
		// check left side
		if(x > 0) {
			// has left neighbor(s)
			
			if(y > 0) {
				// top left
				neighbors.add(new PVector((x - 1), (y - 1)));
			}
			
			// left
			neighbors.add(new PVector((x - 1), y));
			
			if(y < y_max) {
				// bottom left
				neighbors.add(new PVector((x - 1), (y + 1)));
			}
		}
		
		// above?
		if(y > 0) {
			// top
			neighbors.add(new PVector(x, (y - 1)));
		}
		
		// below?
		if(y < y_max) {
			// bottom
			neighbors.add(new PVector(x, (y + 1)));
		}
		
		// check right side
		if(x < x_max) {
			// has right neighbor(s)
			
			if(y > 0) {
				// top right
				neighbors.add(new PVector((x + 1), (y - 1)));
			}
			
			// right
			neighbors.add(new PVector((x + 1), y));
			
			if(y < y_max) {
				// bottom right
				neighbors.add(new PVector((x + 1), (y + 1)));
			}
		}
	}
	
	void updateLetter() {
		if((color_val >= 17) && (color_val < 41)) {
			letter = "O";
			color_val_mid = (17 + (41 - 17));
		} else if((color_val >= 41) && (color_val < 76)) {
			letter = "Y";
			color_val_mid = (41 + (76 - 41));
		} else if((color_val >= 76) && (color_val < 165)) {
			letter = "G";
			color_val_mid = (76 + (165 - 76));
		} else if((color_val >= 165) && (color_val < 254)) {
			letter = "B";
			color_val_mid = (165 + (254 - 165));
		} else if((color_val >= 254) && (color_val < 280)) {
			letter = "I";
			color_val_mid = (254 + (280 - 254));
		} else if((color_val >= 280) && (color_val < 335)) {
			letter = "V";
			color_val_mid = (280 + (335 - 280));
		} else {
			letter = "R";
			color_val_mid = 5;
		}
	}
}