float square_size = 100;
float radius = 40;
int num_n = 7;
int num_d = 9;

void rose(float n, float d) {
  float k = (n / d);
  
  float cx = ((square_size * (n - 1)) + (square_size / 2));
  float cy = ((square_size * (d - 1)) + (square_size / 2));
  
  pushMatrix();
  
  translate(cx, cy);
  
  beginShape();
  stroke(255);
  strokeWeight(1);
  noFill();
  
  float r, x, y;
  
  for (float a = 0; a < TWO_PI * d; a += 0.02) {
    //r = 100;
    r = radius * cos(k * a);
    x = (r * cos(a));
    y = (r * sin(a));

    vertex(x, y);
    //point(x,y);
  }
  
  endShape(CLOSE);
  popMatrix();
}

void setup() {
  size(700, 900);
}

void draw() {
  background(51);
  
  for(float d = 1.0; d <= num_d; d += 1) {
    for(float n = 1.0; n <= num_n; n += 1) {
      rose(n, d);
    }
  }
  
  if(frameCount == 50) { save("preview.png"); }
}