int SQ_SIZE = 48;
int PAD = 9;

color col_blue = color(21, 107, 193);
color col_green = color(6, 229, 78);

void push() { pushMatrix(); pushStyle(); }
void pop() { popStyle(); popMatrix(); }

void setup() {
	size(640, 640);
}

void draw() {
	background(30);
	
	// center
	translate((width / 2), (height / 2));
	
	// top left
	translate(
		(0 - ((SQ_SIZE * 4) + (PAD * 4))),
		(0 - ((SQ_SIZE * 4) + (PAD * 4)))
	);
	
	float anim_frameLength = 180;
	float anim_frame = (frameCount % anim_frameLength);
	float anim_rate = (anim_frame / anim_frameLength);
	
	// squares
	for(int j = 0; j < 9; j++) {
		for(int k = 0; k < 9; k++) {
			if(((j == 3) || (j == 4) || (j == 5)) && ((k == 3) || (k == 4) || (k == 5))) {
				continue;
			}
			
			push();
			
			translate(
				((SQ_SIZE * j) + (PAD * j)),
				((SQ_SIZE * k) + (PAD * k))
			);
			
			rectMode(CENTER);
			noStroke();
			
			rotate((PI * 2 * anim_rate));
			
			color col = col_blue;
			
			fill(col);
			
			rect(0, 0, SQ_SIZE, SQ_SIZE);
			
			pop();
		}
	}
	
	if(frameCount == 50) { save("preview.png"); }
}