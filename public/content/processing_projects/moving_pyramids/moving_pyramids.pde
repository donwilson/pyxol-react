GridItem[][] grid_items;

int loopFollowAfterFrames = (40 * 4);
float followX, followY;
float followBorder = 100;

void setup() {
	size(640, 640);
	
	grid_items = new GridItem[10][10];
	
	for(int y = 0; y < 10; y++) {
		for(int x = 0; x < 10; x++) {
			grid_items[ y ][ x ] = new GridItem(x, y);
		}
	}
}

void draw() {
	background(color(255, 255, 255));
	
	updateFollowPosition();
	
	for(int y = 0; y < 10; y++) {
		for(int x = 0; x < 10; x++) {
			grid_items[ y ][ x ].update();
			grid_items[ y ][ x ].draw();
		}
	}
	
	if(frameCount == 50) { save("preview.png"); }
}


void updateFollowPosition() {
	float frameProgress = float((frameCount % loopFollowAfterFrames));
	float quarterProgress = (float(loopFollowAfterFrames) * 0.25);
	float realProgress = (frameProgress / quarterProgress);
	
	float widthWithoutFollowBorders = (float(width) - (followBorder * 2));
	float heightWithoutFollowBorders = (float(height) - (followBorder * 2));
	
	if(realProgress < 1) {
		followX = followBorder;
		followY = (followBorder + (heightWithoutFollowBorders * realProgress));
	} else if(realProgress < 2) {
		followX = (followBorder + (widthWithoutFollowBorders * (realProgress - 1)));
		followY = (followBorder + heightWithoutFollowBorders);
	} else if(realProgress < 3) {
		followX = (followBorder + widthWithoutFollowBorders);
		followY = (followBorder + (heightWithoutFollowBorders * (1 - (realProgress - 2))));
	} else if(realProgress <= 4) {
		followX = (followBorder + (widthWithoutFollowBorders * (1 - (realProgress - 3))));
		followY = followBorder;
	}
}