	
	class Cell {
		constructor(x, y, size) {
			this.x = x;
			this.y = y;
			this.size = size;
			this.rect = {
				'topLeft': createVector((this.x * this.size), (this.y * this.size)),
				'bottomRight': createVector(((this.x + 1) * this.size), ((this.y + 1) * this.size))
			};
			this.neighbors = [];
			this.neighbor_bees = 0;
			
			this.revealed = false;
			this.bee = false;
		}
		
		countNeighbors() {
			if(this.bee) {
				return;
			}
			
			let lower_x = (this.x - 1);
			let upper_x = (this.x + 1);
			
			let lower_y = (this.y - 1);
			let upper_y = (this.y + 1);
			
			
			// row above
			for(let yy = (this.y - 1); yy <= (this.y + 1); yy++) {
				if((yy < 0) || (yy >= grid.length)) {
					continue;
				}
				
				for(let xx = (this.x - 1); xx <= (this.x + 1); xx++) {
					if((xx < 0) || (xx >= grid[ yy ].length)) {
						continue;
					}
					
					this.neighbors.push(createVector(xx, yy));
					
					if(grid[ yy ][ xx ].bee) {
						this.neighbor_bees++;
					}
				}
			}
		}

		contains(px, py) {
			return ((this.rect.topLeft.x < px) && (this.rect.topLeft.y < py) && (this.rect.bottomRight.x >= px) && (this.rect.bottomRight.y >= py));
		}

		reveal() {
			if(this.revealed) {
				return;
			}
			
			this.revealed = true;
			
			if(!this.neighbor_bees) {
				for(let i = 0; i < this.neighbors.length; i++) {
					let vec = this.neighbors[ i ];
					
					grid[ vec.y ][ vec.x ].reveal();
				}
			}
		}

		draw() {
			push();
			
			translate(this.rect.topLeft.x, this.rect.topLeft.y);
			
			if(this.revealed) {
				if(this.bee) {
					fill(255);
				} else {
					fill(200);
				}
			} else {
				fill(255);
				rect(0, 0, this.size, this.size);
			}
			
			stroke(0);
			rect(0, 0, this.size, this.size);
			
			if(this.revealed) {
				let rect_half = (this.size / 2);
				
				if(this.bee) {
					// draw bee
					push();
					
					translate(rect_half, rect_half);
					
					ellipseMode(CENTER);
					strokeWeight(3);
					stroke(0);
					fill("#fff01f");
					ellipse(0, 0, rect_half);
					
					pop();
				} else if(this.neighbor_bees > 0) {
					// draw neighbor tally
					push();
					
					//textAlign(LEFT);
					
					let text_height = (this.size * 0.66);
					
					textSize(text_height);//textSize(text_height);
					
					let text_width = textWidth(this.neighbor_bees);
					
					fill(0);
					text(this.neighbor_bees, (rect_half - (text_width / 2)), (rect_half + (text_height * 0.4)));
					
					pop();
				}
			}
			
			pop();
		}
		
		setBee() {
			if(this.bee) {
				return false;
			}
			
			this.bee = true;
			
			return true;
		}
	}
	