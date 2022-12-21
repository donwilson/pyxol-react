class Y {
  float radius;
  float linesize;
  float x;
  float y;
  float distance;
  float maxDist;
  float angle;
  float angleTotal;
  color col = color(0, 100, 100);
  color col1;
  color col2;
  color red = color(0, 100, 100);
  color blue = color(99, 100, 100);
  float step = 0;
  int direction = 1;
  
  boolean shifted = false;
  
  Y(float radius_, float linesize_, int row_x, int row_y) {
    radius = radius_;
    linesize = linesize_;
    
    x = ((obj_radius + offset_x) * row_x);
    y = ((obj_radius + offset_y) * row_y);
    
    if((row_y % 2) == 1) {
      x -= (offset_x + (linesize / 2));
    }
    
    distance = dist(x, y, width/2, height/2);
    maxDist = dist(0, 0, width/2, height/2);   // max distance = corner to center
    
    angle = 0.0;
    angleTotal = map(distance, 0, maxDist, 60, 0);
  }
  
  void update(float angleStep) {
    //angleTotal = constrain((angleTotal + angleStep), angleTotal, (angleTotal + (angleTotal % 120) + 119.999));
    angleTotal = constrain((angleTotal + angleStep), angleTotal, (angleTotal + (angleTotal % 120) + 120));
    
    float stepAt = floor( (angleTotal / 60) );
    
    if((stepAt % 2) == 1) {
      //angle = constrain((angle + angleStep), angle, (angle + (angle % 60) + 59.999));
      angle = constrain((angle + angleStep), angle, (angle + (angle % 60) + 60));
      
      if(!shifted) {
        shifted = true;
        direction *= -1;
        step *= direction;
      }
      
      step += ((angleStep / 60) * direction);
    } else {
      shifted = false;
    }
  }
  
  void display() {
    push();
    
    translate(x, y);
    
    //color calccol = lerpColor(col1, col2, map((angle % 60), 0, 60, 0, 1));
    color calccol = lerpColor(red, blue, norm(step, -1, 1));
    
    stroke(calccol);
    strokeWeight(lineSize);
    
    float a_a = (radians((0.0 + angle)) - HALF_PI);
    line(0, 0, (cos(a_a) * obj_radius), (sin(a_a) * obj_radius));
    
    float a_b = (radians((120.0 + angle)) - HALF_PI);
    line(0, 0, (cos(a_b) * obj_radius), (sin(a_b) * obj_radius));
    
    float a_c = (radians((240.0 + angle)) - HALF_PI);
    line(0, 0, (cos(a_c) * obj_radius), (sin(a_c) * obj_radius));
    
    pop();
  }
}
