void setup() {
  size(500, 500);
}

void draw() {
  background(240);
  
  translate((width / 2), (height / 2));
  
  float radiusStep = 50;
  float radius;
  
  for(int d = 3; d <= 6; d++) {
    radius = (radiusStep * (d - 2));
    
    float degreeStep = (360 / d);
    
    float a, px, py;
    
    push();
    beginShape();
    noFill();
    
    for(int i = 0; i < d; i++) {
      a = ((degreeStep * i) - 90);
      px = (radius * cos(radians(a)));
      py = (radius * sin(radians(a)));
      
      vertex(px, py);
    }
    
    endShape(CLOSE);
    pop();
  }
  
  if(frameCount == 50) { save("preview.png"); }
}

void push() {
  pushStyle();
  pushMatrix();
}

void pop() {
  popMatrix();
  popStyle();
}