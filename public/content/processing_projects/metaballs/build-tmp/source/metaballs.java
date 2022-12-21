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

public class metaballs extends PApplet {

int bgColor;
int blobColor;

Blob[] blobs = new Blob[6];

public void setup() {
  
  //colorMode(HSB, 100);
  
  //bgColor = color(0, 27, 30);
  //blobColor = color(35, 97, 103);
  
  bgColor = color(11, 18, 44);
  blobColor = color(252, 3, 232);
  
  for(int i = 0; i < blobs.length; i++) {
    blobs[ i ] = new Blob();
  }
}

public void draw() {
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
      
      float sum_mapped = map(sum, 0, (width * 0.9f), 0, 100);
      //pixels[ index ] = color(72, (100 - sum_mapped), sum_mapped);
      
      if(sum_mapped > 40) {
        pixels[ index ] = blobColor;
      } else if(sum_mapped > 30) {
        pixels[ index ] = lerpColor(blobColor, bgColor, map(sum_mapped, 30, 40, 1, 0.8f));
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
  
  public void update() {
    if(((pos.x + r) >= width) || ((pos.x - r) <= 0)) {
      vel.x *= -1;
    }
    
    if(((pos.y + r) >= height) || ((pos.y - r) <= 0)) {
      vel.y *= -1;
    }
    
    pos.add(vel);
  }
  
  public void show() {
    noFill();
    stroke(0);
    strokeWeight(4);
    ellipseMode(CENTER);
    ellipse(pos.x, pos.y, r*2, r*2);
  }
}
  public void settings() {  size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "metaballs" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
