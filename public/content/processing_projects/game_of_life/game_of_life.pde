boolean do_stroke = false;

int cols;
int rows;

int cell_size = 4;

int[][] grid;


void setup() {
	size(640, 640);
	
	rows = (height / cell_size);
	cols = (width / cell_size);
	
	grid = new int[rows][cols];
	
	for(int y = 0; y < rows; y++) {
		grid[ y ] = new int[ cols ];
		
		for(int x = 0; x < cols; x++) {
			grid[ y ][ x ] = round(random(1));
		}
	}
	
	frameRate(10);
}

void draw() {
	background(0);
	
	if(do_stroke) {
		stroke(200);
		strokeWeight(1);
	} else {
		noStroke();
	}
	
	int[][] new_grid = copyGrid(grid);
	
	int draw_cell_size = cell_size;
	
	if(do_stroke) {
		draw_cell_size = (cell_size - 1);
	}
	
	for(int j = 0; j < grid.length; j++) {
		for(int k = 0; k < grid[ j ].length; k++) {
			int state = grid[ j ][ k ];
			
			int countNeighbors = calcCell(grid, k, j);
			
			if((state == 1) && ((countNeighbors < 2) || (countNeighbors > 3))) {
				state = 0;
			} else if((state == 0) && (countNeighbors == 3)) {
				state = 1;
			}
			
			new_grid[ j ][ k ] = state;
			
			if(state > 0) {
				fill(0);
			} else {
				fill(255);
			}
			
			rect((k * cell_size), (j * cell_size), draw_cell_size, draw_cell_size);
		}
	}
	
	grid = copyGrid(new_grid);
	
	if(frameCount == 50) { save("preview.png"); }
}


int[][] copyGrid(int[][] grid) {
	int[][] new_grid = new int[rows][cols];
	
	for(int j = 0; j < grid.length; j++) {
		new_grid[ j ] = new int[cols];
		
		for(int k = 0; k < grid[ j ].length; k++) {
			new_grid[ j ][ k ] = grid[ j ][ k ];
		}
	}
	
	return new_grid;
}

int calcCell(int[][] grid, int x, int y) {
	int sum = 0;
	
	for(int j = -1; j <= 1; j++) {
		int row = ((j + y + rows) % rows);
		
		for(int k = -1; k <= 1; k++) {
			int col = ((k + x + cols) % cols);
			
			sum += grid[ row ][ col ];
		}
	}
	
	sum -= grid[ y ][ x ];
	
	return sum;
}