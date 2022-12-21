	
	class Cluster {
		Dot[] dots;
		ArrayList<FloatList> explosions;
		float explosion_radius_min = 400;
		float explosion_radius_max = 450;
		float explosion_frames = 60;
		
		Cluster() {
			// arraylist to record explosion points/states
			explosions = new ArrayList<FloatList>();
			
			// load image
			PImage pimg = loadImage("pi.jpg");
			
			// create dots array, pull random black pixel for each dot
			dots = new Dot[1700];
			
			for(int i = 0; i < dots.length; i++) {
				// pick random black pixel
				int px = 0;
				int py = 0;
				int attempts = 0;
				int max_attempts = 500;
				
				while((attempts < max_attempts) && (px == 0) && (py == 0)) {
					attempts++;
					px = floor(random(pimg.width));
					py = floor(random(pimg.height));
					
					color pcol = pimg.get(px, py);
					
					if(red(pcol) > 100) {
						px = 0;
						py = 0;
					} else {
						// check other dots
						if(i > 0) {
							for(int k = 0; k < i; k++) {
								float distance = dist(px, py, dots[ k ].x, dots[ k ].y);
								
								if(distance <= 9.8) {
									px = 0;
									py = 0;
								}
							}
						}
					}
				}
				
				int dotpx = floor(px);//int dotpx = floor(map(px, 0, pimg.width, 0, width));
				int dotpy = floor(py);//int dotpy = floor(map(py, 0, pimg.height, 0, height));
				
				dots[ i ] = new Dot(dotpx, dotpy);
			}
		}
		
		void explodeAt(float x, float y) {
			//println("explodeAt("+ x +","+ y +")");
			
			FloatList explosion = new FloatList();
			explosion.append((float)x);
			explosion.append((float)y);
			explosion.append(0.0);
			explosion.append(random(explosion_radius_min, explosion_radius_max));
			
			explosions.add(explosion);
		}
		
		void update() {
			// clear out finished explosions
			for(int i = explosions.size() - 1; i >= 0; i--) {
				FloatList exp = explosions.get(i);
				
				if(exp.get(2) >= 1) {
					explosions.remove(i);
				}
			}
			
			// increase explosion radius
			for(int i = 0; i < explosions.size(); i++) {
				FloatList exp = explosions.get(i);
				
				exp.set(2, (exp.get(2) + (1 / explosion_frames) ));
			}
		}
		
		void draw() {
			for(int j = 0; j < dots.length; j++) {
				Dot dot = dots[j];
				
				for(int i = 0; i < explosions.size(); i++) {
					FloatList exp = explosions.get(i);
					
					dot.processExplosion(exp.get(0), exp.get(1), (exp.get(3) * exp.get(2)));
				}
				
				dot.draw();
			}
		}
	}
	