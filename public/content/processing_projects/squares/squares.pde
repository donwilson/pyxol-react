Square[] sqs;

int num_sqs = 11;

void push() {
  pushMatrix();
  pushStyle();
}

void pop() {
  popStyle();
  popMatrix();
}


void setup() {
  //size(600, 600, P3D);
  size(600, 600);
  
  colorMode(HSB, 100);
  
  //ortho();
  
  sqs = new Square[ num_sqs ];
  
  float w = 200;
  float h = 200;
  float spacing = 18;
  float strokeW = 9;
  float starting_x = (0 - (w / 2) + ((num_sqs / 2) * (spacing + (strokeW / 2))));
  float starting_y = (0 - (h / 2) - ((num_sqs / 2) * (spacing + (strokeW / 2))));
  
  for(int i = num_sqs; i > 0; i--) {
    float x = (starting_x - (i * spacing));
    float y = (starting_y + (i * spacing));
    //float z = (-1 * (num_sqs - i) * spacing);
    
    //float hue = map(i, num_sqs, 1, 50, 0);
    float hue = map(i, num_sqs, 1, 100, 10);
    
    //sqs[ (i - 1) ] = new Square(x, y, z, w, h, strokeW, hue);
    sqs[ (i - 1) ] = new Square(x, y, w, h, strokeW, hue);
  }
}

void draw() {
  background(0);
  
  push();
  translate(width/2, height/2);
  
  for(Square sq : sqs) {
    sq.update();
    sq.display();
  }
  
  pop();
  
  if(frameCount == 50) { save("preview.png"); }
}
