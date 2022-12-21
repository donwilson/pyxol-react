Cell[] cells = new Cell[1500];
float aa;

void setup() {
	size(640, 640, P3D);
	noSmooth();
	
	for(int i = 0; i < cells.length; i++) {
		cells[ i ] = new Cell(i, cells.length);
	}
}

void draw() {
	background(30);
	ortho();
	lights();
	//ambientLight(255, 255, 255, 0, -1, 0);
	
	translate((width / 2), (height / 2), 0);
	
	// debug
	//push();
	////fill(255, 0, 0);
	//noStroke();
	//sphere(2);
	//pop();
	
	for(Cell cell : cells) {
		//cell.update();
		cell.draw();
	}
	
	aa += 0.01;
	
	if(frameCount == 50) { save("preview.png"); }
}

void push() { pushMatrix(); pushStyle(); }
void pop() { popStyle(); popMatrix(); }