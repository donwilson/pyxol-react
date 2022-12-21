	
	let grid = [];
	
	function setup() {
		let num_cols = 10;
		let num_rows = 12;
		let cell_size = 32;
		
		let game_width = ((num_cols * cell_size) + 1);
		let game_height = ((num_rows * cell_size) + 1);
		
		createCanvas(game_width, game_height);
		
		for(let y = 0; y < num_rows; y++) {
			grid[ y ] = [];
			
			for(let x = 0; x < num_cols; x++) {
				grid[ y ][ x ] = new Cell(x, y, cell_size);
			}
		}
		
		// add bees
		let num_bees = floor(random(ceil((num_cols / 2)), num_cols));
		let num_bees_added = 0;
		
		while(num_bees_added < num_bees) {
			let randX = floor(random(0, (num_cols - 1)));
			let randY = floor(random(0, (num_rows - 1)));
			
			if(grid[ randY ][ randX ].setBee()) {
				num_bees_added += 1;
			}
		}
		
		// count neighbors
		for(let y = 0; y < num_rows; y++) {
			for(let x = 0; x < num_cols; x++) {
				grid[ y ][ x ].countNeighbors();
			}
		}
	}
	
	function gameLost() {
		for(let cell_row in grid) {
			for(let cell_col in grid[ cell_row ]) {
				let cell = grid[ cell_row ][ cell_col ];
				
				cell.revealed = true;
			}
		}
		
		alert("Sorry, you lost!");
	}
	
	function gameWon() {
		for(let cell_row in grid) {
			for(let cell_col in grid[ cell_row ]) {
				let cell = grid[ cell_row ][ cell_col ];
				
				cell.revealed = true;
			}
		}
		
		alert("You won!");
	}
	
	function checkGameWon() {
		let num_remaining = 0;
		
		for(let cell_row in grid) {
			for(let cell_col in grid[ cell_row ]) {
				let cell = grid[ cell_row ][ cell_col ];
				
				if(!cell.bee && !cell.revealed) {
					num_remaining++;
				}
			}
		}
		
		if(num_remaining == 0) {
			gameWon();
		}
	}
	
	function draw() {
		background(255);
		
		for(let cell_row in grid) {
			for(let cell_col in grid[ cell_row ]) {
				let cell = grid[ cell_row ][ cell_col ];
				
				cell.draw();
			}
		}
	}
	
	function mousePressed() {
		for(let cell_row in grid) {
			for(let cell_col in grid[ cell_row ]) {
				let cell = grid[ cell_row ][ cell_col ];
				
				if(cell.contains(mouseX, mouseY)) {
					cell.reveal();
					
					if(cell.bee) {
						gameLost();
					} else {
						checkGameWon();
					}
				}
			}
		}
	}
	