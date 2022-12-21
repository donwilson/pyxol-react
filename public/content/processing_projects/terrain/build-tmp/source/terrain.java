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

public class terrain extends PApplet {

float a;

Box[] boxes;

float box_size = 10;
int num_boxes_x = 100;
int num_boxes_y = 100;
float box_noise;
float box_color;

public void setup() {
  
  
  frameRate(60);
  
  a = 0;
  
  boxes = new Box[ (num_boxes_x * num_boxes_y) ];
  
  float z;
  
  int i = 0;
  for(int y = 0; y < num_boxes_y; y++) {
    for(int x = 0; x < num_boxes_x; x++) {
      float raw_z = noise(
        (x / box_size),
        (y / box_size)
      );
      
      z = map(raw_z, 0, 1, 1, box_size);
      
      boxes[ i ] = new Box(x, y, z, box_size);
      
      i++;
    }
  }
}

public void draw() {
  background(51);
  
  // fsp
  fill(255, 255, 255);
  textSize(12);
  text(frameRate, 16, 16);
  
  // camera
  translate((width / 2), (height / 2));
  rotateX(PI / 3);
  //rotateZ(a);
  
  // boxes
  for(Box box : boxes) {
    box.draw();
  }
  
  //a += 0.05;
  
  if(frameCount == 50) { save("preview.png"); }
}
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
  
  public void draw() {
    pushMatrix();
    translate(x, y, z);
    
    noStroke();
    fill(fill_color);
    box(box_size, box_size, box_size);
    popMatrix();
  }
}
  public void settings() {  size(800, 600, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "terrain" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
