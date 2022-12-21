ArrayList<Truchet> tiles;

float tile_size = 24;

void setup() {
	size(640, 640);
	
	tiles = new ArrayList<Truchet>();
	
	int num_y = ceil( (height / tile_size) );
	int num_x = ceil( (width / tile_size) );
	
	for(int y = 0; y < num_y; y++) {
		for(int x = 0; x < num_x; x++) {
			Truchet tile = new Truchet(
				(x * tile_size),
				(y * tile_size)
			);
			
			tiles.add(tile);
		}
	}
}

void draw() {
	background(30);
	
	for(Truchet tile : tiles) {
		tile.update();
		tile.draw();
	}
	
	if(frameCount == 50) { save("preview.png"); }
}