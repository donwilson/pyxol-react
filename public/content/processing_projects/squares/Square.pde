class Square {
  float x;
  float y;
  float z;
  float w;
  float h;
  float strokeW;
  float hue;
  int tick = 0;
  
  // 2d
  Square(float x_, float y_, float w_, float h_, float strokeW_, float hue_) {
    x = x_;
    y = y_;
    z = 0;
    w = w_;
    h = h_;
    strokeW = strokeW_;
    hue = hue_;
    tick = floor(map(floor(hue_), 0, 50, 0, 60));
  }
  
  // 3d
  Square(float x_, float y_, float z_, float w_, float h_, float strokeW_, float hue_) {
    x = x_;
    y = y_;
    z = z_;
    w = w_;
    h = h_;
    strokeW = strokeW_;
    hue = hue_;
    //tick = floor(map(floor(hue_), 0, 50, 0, 60));
    tick = floor(map(floor(hue_), 100, 10, 0, 60));
  }
  
  void update() {
    tick += 2;
  }
  
  void display() {
    push();
    
    float brightness = map((tick % 120), 0, 60, 0, 100); 
    
    translate(x, y);
    
    beginShape();
    
    noFill();
    strokeWeight(strokeW);
    stroke(hue, 100, brightness);
    
    //rect(0, 0, w, h);
    
    //vertex(0, 0, z);
    //vertex(w, 0, z);
    //vertex(w, h, z);
    //vertex(0, h, z);
    
    vertex(0, 0);
    vertex(w, 0);
    vertex(w, h);
    vertex(0, h);
    
    endShape(CLOSE);
    
    pop();
  }
}
