float a;

Box[] boxes;

float box_size = 10;
int num_boxes_x = 100;
int num_boxes_y = 100;
float box_noise;
float box_color;

void setup() {
  
  size(800, 600, P3D);
  frameRate(60);
  
  a = 0;
  
  boxes = new Box[ (num_boxes_x * num_boxes_y) ];
  
  float z;
  
  int i = 0;
  for(int y = 0; y < num_boxes_y; y++) {
    for(int x = 0; x < num_boxes_x; x++) {
      float raw_z = noise(
        (x / box_size),
        (y / box_size)
      );
      
      z = map(raw_z, 0, 1, 1, box_size);
      
      boxes[ i ] = new Box(x, y, z, box_size);
      
      i++;
    }
  }
}

void draw() {
  background(51);
  
  // fsp
  fill(255, 255, 255);
  textSize(12);
  text(frameRate, 16, 16);
  
  // camera
  translate((width / 2), (height / 2));
  rotateX(PI / 3);
  //rotateZ(a);
  
  // boxes
  for(Box box : boxes) {
    box.draw();
  }
  
  //a += 0.05;
  
  if(frameCount == 50) { save("preview.png"); }
}