	
	let cols, rows;
	let w = 10;
	let grid = [];
	
	let current;
	
	let stack = [];
	
	function setup() {
		createCanvas(800, 600);
		
		cols = floor(width / w);
		rows = floor(height / w);
		
		for(let y = 0; y < rows; y++) {
			for(let x = 0; x < cols; x++) {
				let cell = new Cell(x, y);
				grid.push(cell);
			}
		}
		
		current = grid[0];
	}
	
	function draw() {
		background(51);
		
		for(let i = 0; i < grid.length; i++) {
			grid[ i ].show();
		}
		
		// step 1
		current.visited = true;
		current.highlight();
		let next = current.checkNeighbors();
		
		if(next) {
			next.visited = true;
			
			// step 2
			stack.push(current);
			
			// step 3
			removeWalls(current, next);
			
			// step 4
			current = next;
		} else if(stack.length) {
			current = stack.pop();
		}
	}
	
	function removeWalls(a, b) {
		let x = (a.i - b.i);
		
		if(x === 1) {
			a.walls[3] = false;
			b.walls[1] = false;
		} else if(x === -1) {
			a.walls[1] = false;
			b.walls[3] = false;
		}
		
		let y = (a.j - b.j);
		
		if(y === 1) {
			a.walls[0] = false;
			b.walls[2] = false;
		} else if(y === -1) {
			a.walls[2] = false;
			b.walls[0] = false;
		}
	}
	