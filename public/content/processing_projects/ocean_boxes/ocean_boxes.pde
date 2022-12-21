float zz;
float aa;
boolean clicked;

void setup() {
  size(500, 500, P3D);

  aa = 0;
  zz = 0;
  clicked = false;
}

void draw() {
  background(175);

  //if(clicked) {
  //  ortho();
  //} else {
  //perspective();
  //}

  translate(width/2, height/2);

  rotateX(radians(-30));
  rotateY(QUARTER_PI);//rotateY(QUARTER_PI + zz);

  int x;
  int y;
  float by;
  float num = 80;
  float size = ((width * 0.66) / num);

  float yoff;
  float xoff;
  float byc;
  float bys;
  float step = 0.25;//float step = (num / size);
  float distance;
  float block_height_increase;
  
  for (y = 0; y < num; y++) {
    yoff = ((-(num / 2) + y) * size) + size;

    for (x = 0; x < num; x++) {
      xoff = ((-(num / 2) + x) * size) + size;

      //distance = dist(x + 0.5, y + 0.5, (num / 2), (num / 2));
      //
      //if (clicked && (distance < (num / 2))) {
      //  continue;
      //}

      pushMatrix();

      by = noise((step * x), (step * y), zz);
      block_height_increase = map((size * by), 0, size, 1, (size*3));
      
      translate(xoff, (size - (block_height_increase / 2)), yoff);
      
      //noStroke();
      fill(0, 0, map(by, 0, 1, 0, 255));

      box(size, (size + block_height_increase), size);

      popMatrix();
    }
  }

  zz += 0.01;
  
  if(frameCount == 50) { save("preview.png"); }
}

void mouseWheel(MouseEvent event) {
  float e = event.getCount();

  if (e > 0) {
    aa -= 0.1;
  } else if (e < 0) {
    aa += 0.1;
  }
}

void mousePressed() {
  clicked = !clicked;
}