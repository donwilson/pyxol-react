	
	class QuadTree {
		constructor(boundary, n) {
			this.boundary = boundary;
			this.capacity = n;
			this.points = [];
			this.divided = false;
		}
		
		subdivide() {
			if(this.divided) {
				return;
			}
			
			// nw
			let nw = new Rectangle(
				(this.boundary.x - (this.boundary.w / 2)),
				(this.boundary.y - (this.boundary.h / 2)),
				(this.boundary.w / 2),
				(this.boundary.h / 2)
			);
			this.northwest = new QuadTree(nw, this.capacity);
			
			// ne
			let ne = new Rectangle(
				(this.boundary.x + (this.boundary.w / 2)),
				(this.boundary.y - (this.boundary.h / 2)),
				(this.boundary.w / 2),
				(this.boundary.h / 2)
			);
			this.northeast = new QuadTree(ne, this.capacity);
			
			// sw
			let sw = new Rectangle(
				(this.boundary.x - (this.boundary.w / 2)),
				(this.boundary.y + (this.boundary.h / 2)),
				(this.boundary.w / 2),
				(this.boundary.h / 2)
			);
			this.southwest = new QuadTree(sw, this.capacity);
			
			// se
			let se = new Rectangle(
				(this.boundary.x + (this.boundary.w / 2)),
				(this.boundary.y + (this.boundary.h / 2)),
				(this.boundary.w / 2),
				(this.boundary.h / 2)
			);
			this.southeast = new QuadTree(se, this.capacity);
			
			// prevent further dividing
			this.divided = true;
		}
		
		insert(point) {
			if(!this.boundary.contains(point)) {
				return;
			}
			
			if(this.points.length < this.capacity) {
				this.points.push(point);
			} else {
				this.subdivide();
				
				this.northwest.insert(point);
				this.northeast.insert(point);
				this.southwest.insert(point);
				this.southeast.insert(point);
			}
		}
		
		query(range, found) {
			if(!found) {
				found = [];
			}
			
			if(!this.boundary.insersects(range)) {
				return found;
			}
			
			for(let p of this.points) {
				if(range.contains(p)) {
					found.push(p);
				}
			}
			
			if(this.divided) {
				this.northwest.query(range, found);
				this.northeast.query(range, found);
				this.southwest.query(range, found);
				this.southeast.query(range, found);
			}
			
			return found;
		}
		
		show() {
			stroke(255);
			strokeWeight(1);
			noFill();
			rectMode(CENTER);
			rect(this.boundary.x, this.boundary.y, (this.boundary.w * 2), (this.boundary.h * 2));
			
			if(this.divided) {
				this.northwest.show();
				this.northeast.show();
				this.southwest.show();
				this.southeast.show();
			}
			
			for(let p of this.points) {
				strokeWeight(3);
				point(p.x, p.y);
			}
		}
	}
	