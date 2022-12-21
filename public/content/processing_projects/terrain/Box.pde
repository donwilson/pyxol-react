class Box {
  float x;
  float y;
  float z;
  
  float box_size;
  float fill_color;
  
  Box(float tmpx, float tmpy, float tmpz, float size) {
    box_size = size;
    
    x = (((num_boxes_x / 2) - tmpx) * box_size);
    y = (((num_boxes_y / 2) - tmpy) * box_size); //<>//
    z = (tmpz * box_size);
    
    fill_color = map(tmpz, 1, box_size, 0, 255);
  }
  
  void draw() {
    pushMatrix();
    translate(x, y, z);
    
    noStroke();
    fill(fill_color);
    box(box_size, box_size, box_size);
    popMatrix();
  }
}