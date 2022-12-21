int boxsize = 10;
PImage picture;

int halfBoxsize;
float boxdist;
float a = 0;

void setup() {
  size(1024, 1024, P3D);
  ortho();
  colorMode(HSB);
  
  picture = loadImage("starry-night.jpg");
  picture.loadPixels();
  
  halfBoxsize = round( (boxsize / 2) );
  boxdist = (boxsize * 1.5);
}

void draw() {
  background(0);
  
  float startPosX = ((width % boxdist) / 2);
  float startPosY = ((height % boxdist) / 2);
  
  for(float yy = startPosY; yy < height; yy += boxdist) {
    for(float xx = startPosX; xx < width; xx += boxdist) {
      drawBox(xx, yy);
    }
  }
  
  a += (1 / frameRate);
  
  if(frameCount == 50) { save("preview.png"); }
}


color getColor(float cx, float cy) {
  int xoff = 0;
  int yoff = 0;
  
  if(picture.width > picture.height) {
    xoff = round( ((picture.width - picture.height) / 2) ); 
  } else if(picture.height > picture.width) {
    yoff = round( ((picture.height - picture.width) / 2) );
  }
  
  int px = round(map(cx, 0, width, xoff, (picture.width - (xoff * 2))));
  int py = round(map(cy, 0, height, yoff, (picture.height - (yoff * 2))));
  
  return picture.get(px, py);
}

void drawBox(float x, float y) {
  pushMatrix();
  translate(x, y);
  rotateX((-PI / 6) + a);
  rotateY((asin( (1 / sqrt(2)) ) + a));
  //rotateZ(a);
  color c = getColor(x, y);
  fill(c);
  stroke(color(
    hue(c),
    saturation(c),
    constrain(brightness(c) - 50, 0, 255)
  ));
  box(boxsize);
  popMatrix();
}