float obj_radius = 30.0;
float num_seconds_per_full_rotation = 3.0;
float aoff = 0.0;
float lineSize = 8;

float offset_x, offset_y;
int num_x, num_y;

Y[] ys;

void push() {
  pushMatrix();
  pushStyle();
}

void pop() {
  popStyle();
  popMatrix();
}

void setup() {
  size(600, 600);
  offset_y = (sin(radians(120) - HALF_PI) * obj_radius);
  offset_x = (cos(radians(120) - HALF_PI) * obj_radius);
  
  num_x = (ceil( (600 / (obj_radius + offset_x)) ) + 1);
  num_y = (ceil( (600 / (obj_radius + offset_y)) ) + 1);
  
  colorMode(HSB, 100);
  
  // create Ys
  ys = new Y[ (num_y * num_x) ];
  
  for(int y = 0; y < num_y; y++) {
    for(int x = 0; x < num_x; x++) {
      ys[ ((num_x * y) + x) ] = new Y(obj_radius, lineSize, x, y);
    }
  }
}

void debugLines() {
  // red lines
  push();
  stroke(255, 255, 255);
  strokeWeight(1);
  line(width/2, 0, width/2, height);
  line(0, height/2, width, height/2);
  pop();
}

void draw() {
  background(0);
  
  //debugLines();
  
  translate(-8, -15);
  
  float angleStep = ((360 / 60) / num_seconds_per_full_rotation);
  
  for(Y y : ys) {
    y.update(angleStep);
    y.display();
  }
  
  if(frameCount == 50) { save("preview.png"); }
}
