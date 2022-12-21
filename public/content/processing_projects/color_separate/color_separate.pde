int pixelsIndexAtXY(int x, int y) {
  return ((width * y) + x);
}

void setup() {
  size(600, 600);
  
  background(255);
  
  PImage homer = loadImage("homer.jpg");
  image(homer, 200, 200);
  
  loadPixels();
  
  for(int hy = 0; hy < 200; hy++) {
    for(int hx = 0; hx < 200; hx++) {
      color pixel = pixels[ pixelsIndexAtXY((hx + 200), (hy + 200)) ];
      
      float pr = red(pixel);
      float pg = green(pixel);
      float pb = blue(pixel);
      
      /*
        R  RG  G
        RB S  
        B     BG
      */
      
      
      // red
      pixels[ pixelsIndexAtXY((hx + 0), (hy + 0)) ] = color(pr, 0, 0);
      
      // red green
      pixels[ pixelsIndexAtXY((hx + 200), (hy + 0)) ] = color(pr, pg, 0);
      
      // green
      pixels[ pixelsIndexAtXY((hx + 400), (hy + 0)) ] = color(0, pg, 0);
      
      // red blue
      pixels[ pixelsIndexAtXY((hx + 0), (hy + 200)) ] = color(pr, pb, 0);
      
      // blue
      pixels[ pixelsIndexAtXY((hx + 0), (hy + 400)) ] = color(0, 0, pb);
      
      // blue green
      pixels[ pixelsIndexAtXY((hx + 400), (hy + 400)) ] = color(0, pg, pb);
    }
  }
  
  updatePixels();
  
  save("preview.png");
}

void draw() {
  
}
