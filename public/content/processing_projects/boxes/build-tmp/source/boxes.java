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

public class boxes extends PApplet {

float default_box_size = 32;
float box_size = default_box_size;
float distance=box_size;//float distance = (box_size * 3);
float a = 0;

public void push() {
  pushMatrix();
  pushStyle();
}

public void pop() {
  popStyle();
  popMatrix();
}

public void drawBox(float xm, float ym, float zm) {
  push();
  translate(distance*xm, distance*ym, distance*zm);
  box(box_size);
  pop();
}

public void setup() {
  
}

public void draw() {
  background(0);
  
  //push();
  stroke(255, 0, 0);
  line(0, height/2, width, height/2);
  line(width/2, 0, width/2, height);
  //pop();
  
  translate(width/2, height/2);
  
  rotateY(a);
  
  noFill();
  stroke(255);
  strokeWeight(1);
  
  //box_size = map(sin(a), -1, 1, default_box_size, default_box_size*2);
  float num_boxes_per_axis = 6;
  float all_w = ((box_size * num_boxes_per_axis) + ((distance - box_size) * (num_boxes_per_axis - 1)));
  float all_h = ((box_size * num_boxes_per_axis) + ((distance - box_size) * (num_boxes_per_axis - 1)));
  
  translate( -(all_w / 2) + (box_size / 2), -(all_h / 2) + (box_size / 2) );
  
  for(float z = 0; z < num_boxes_per_axis; z++) {
    for(float y = 0; y < num_boxes_per_axis; y++) {
      for(float x = 0; x < num_boxes_per_axis; x++) {
        drawBox(x, y, z);
      }
    }
  }
  
  a += 0.01f;
  
  if(frameCount == 50) { save("preview.png"); }
}
  public void settings() {  size(600, 600, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "boxes" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
