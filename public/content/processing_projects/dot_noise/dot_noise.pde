float aa;
float nstep = 0.005;

float natxy(int x, int y) {
  return noise(
    x * nstep,
    y * nstep,
    aa
  );
}

void setup() {
  size(600, 600);
  colorMode(HSB, 100);
}

void draw() {
  background(255);
  
  loadPixels();
  
  for(int py = 0; py < height; py++) {
    for(int px = 0; px < width; px++) {
      int index = ((py * width) + px);
      
      float n = natxy(px, py);
      
      pixels[ index ] = color(map(n, 0, 1, 30, 80), 100, 100);
    }
  }
  
  updatePixels();
  
  
  
  int spacing = 4;
  int ts = 8;   // triangle size
  
  int cellW = (width / ts);
  int cellH = (height / (ts + spacing));
  
  int num_x = (ceil( cellW ) + 1);
  int num_y = (ceil( cellH ) + 1);
  
  boolean upsideDown = true;
  
  for(float y = 0; y < num_y; y++) {
    for(float x = 0; x < num_x; x++) {
      if(x == 0) {
        upsideDown = ((y % 1) == 1);
      } else {
        upsideDown = !upsideDown;
      }
      
      PVector left, middle, right;
      
      float offx = (ts * x) - (ts / 2);
      float offy = ((ts + spacing) * y) - (ts / 2) + spacing;
      
      if(upsideDown) {
        left = new PVector(
          offx,
          offy
        );
        
        middle = new PVector(
          offx + ts/2,
          offy + ts
        );
        
        right = new PVector(
          offx + ts,
          offy
        );
      } else {
        left = new PVector(
          offx,
          offy + ts
        );
        
        middle = new PVector(
          offx + ts/2,
          offy
        );
        
        right = new PVector(
          offx + ts,
          offy + ts
        );
      }
      
      // play around with triangle color
      float tnoise = natxy( round(offx + ts/2), round(offy + ts/2) );
      noStroke();
      fill(color(map(tnoise, 0, 1, 20, 70), 100, 100, map(tnoise, 0.5, 1, 0, 100)));
      
      beginShape();
      
      vertex(left.x, left.y);
      vertex(middle.x, middle.y);
      vertex(right.x, right.y);
      vertex(left.x, left.y);
      
      endShape();
      
    }
  }
  
  aa += 0.02;
  
  if(frameCount == 50) { save("preview.png"); }
}
