import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class dot_noise extends PApplet {

float aa;
float nstep = 0.005f;

public float natxy(int x, int y) {
  return noise(
    x * nstep,
    y * nstep,
    aa
  );
}

public void setup() {
  
  colorMode(HSB, 100);
}

public void draw() {
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
      fill(color(map(tnoise, 0, 1, 20, 70), 100, 100, map(tnoise, 0.5f, 1, 0, 100)));
      
      beginShape();
      
      vertex(left.x, left.y);
      vertex(middle.x, middle.y);
      vertex(right.x, right.y);
      vertex(left.x, left.y);
      
      endShape();
      
    }
  }
  
  aa += 0.02f;
  
  if(frameCount == 50) { save("preview.png"); }
}
  public void settings() {  size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "dot_noise" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
