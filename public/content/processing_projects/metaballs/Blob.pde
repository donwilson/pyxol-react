class Blob {
  PVector pos;
  float r;
  float rhalf;
  PVector vel;
  
  Blob() {
    r=60;//r = random(120, 240);
    pos = new PVector(random(r, width-r), random(r, height-r));
    vel = PVector.random2D();
    vel.mult(random(2,4));
  }
  
  void update() {
    if(((pos.x + r) >= width) || ((pos.x - r) <= 0)) {
      vel.x *= -1;
    }
    
    if(((pos.y + r) >= height) || ((pos.y - r) <= 0)) {
      vel.y *= -1;
    }
    
    pos.add(vel);
  }
  
  void show() {
    noFill();
    stroke(0);
    strokeWeight(4);
    ellipseMode(CENTER);
    ellipse(pos.x, pos.y, r*2, r*2);
  }
}
