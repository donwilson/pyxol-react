color bgColor;
color blobColor;

Blob[] blobs = new Blob[6];

void setup() {
  size(600, 600);
  //colorMode(HSB, 100);
  
  //bgColor = color(0, 27, 30);
  //blobColor = color(35, 97, 103);
  
  bgColor = color(11, 18, 44);
  blobColor = color(252, 3, 232);
  
  for(int i = 0; i < blobs.length; i++) {
    blobs[ i ] = new Blob();
  }
}

void draw() {
  background(0);
  
  loadPixels();
  
  for(int x = 0; x < width; x++) {
    for(int y = 0; y < height; y++) {
      int index = (x + (y * width));
      float sum = 0;
      
      for(Blob b : blobs) {
        float d = dist(x, y, b.pos.x, b.pos.y);
        sum += (80 * b.r) / d;
      }
      
      float sum_mapped = map(sum, 0, (width * 0.9), 0, 100);
      //pixels[ index ] = color(72, (100 - sum_mapped), sum_mapped);
      
      if(sum_mapped > 40) {
        pixels[ index ] = blobColor;
      } else if(sum_mapped > 30) {
        pixels[ index ] = lerpColor(blobColor, bgColor, map(sum_mapped, 30, 40, 1, 0.8));
      } else {
        pixels[ index ] = bgColor;
      }
      
      //pixels[ index ] = color(55, 100, sum_mapped*0.8);
    }
  }
  
  updatePixels();
  
  for(Blob b : blobs) {
    b.update();
    //b.show();
  }
  
  if(frameCount == 50) { save("preview.png"); }
}
