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

public class rotating_ys extends PApplet {

float obj_radius = 30.0f;
float num_seconds_per_full_rotation = 3.0f;
float aoff = 0.0f;
float lineSize = 8;

float offset_x, offset_y;
int num_x, num_y;

Y[] ys;

public void push() {
  pushMatrix();
  pushStyle();
}

public void pop() {
  popStyle();
  popMatrix();
}

public void setup() {
  
  offset_y = (sin(radians(120) - HALF_PI) * obj_radius);
  offset_x = (cos(radians(120) - HALF_PI) * obj_radius);
  
  num_x = (ceil( (600 / (obj_radius + offset_x)) ) + 1);
  num_y = (ceil( (600 / (obj_radius + offset_y)) ) + 1);
  
  colorMode(HSB, 100);
  
  // create Ys
  ys = new Y[ (num_y * num_x) ];
  
  for(int y = 0; y < num_y; y++) {
    for(int x = 0; x < num_x; x++) {
      ys[ ((num_x * y) + x) ] = new Y(obj_radius, lineSize, x, y);
    }
  }
}

public void debugLines() {
  // red lines
  push();
  stroke(255, 255, 255);
  strokeWeight(1);
  line(width/2, 0, width/2, height);
  line(0, height/2, width, height/2);
  pop();
}

public void draw() {
  background(0);
  
  //debugLines();
  
  translate(-8, -15);
  
  float angleStep = ((360 / 60) / num_seconds_per_full_rotation);
  
  for(Y y : ys) {
    y.update(angleStep);
    y.display();
  }
  
  if(frameCount == 50) { save("preview.png"); }
}
class Y {
  float radius;
  float linesize;
  float x;
  float y;
  float distance;
  float maxDist;
  float angle;
  float angleTotal;
  int col = color(0, 100, 100);
  int col1;
  int col2;
  int red = color(0, 100, 100);
  int blue = color(99, 100, 100);
  float step = 0;
  int direction = 1;
  
  boolean shifted = false;
  
  Y(float radius_, float linesize_, int row_x, int row_y) {
    radius = radius_;
    linesize = linesize_;
    
    x = ((obj_radius + offset_x) * row_x);
    y = ((obj_radius + offset_y) * row_y);
    
    if((row_y % 2) == 1) {
      x -= (offset_x + (linesize / 2));
    }
    
    distance = dist(x, y, width/2, height/2);
    maxDist = dist(0, 0, width/2, height/2);   // max distance = corner to center
    
    angle = 0.0f;
    angleTotal = map(distance, 0, maxDist, 60, 0);
  }
  
  public void update(float angleStep) {
    //angleTotal = constrain((angleTotal + angleStep), angleTotal, (angleTotal + (angleTotal % 120) + 119.999));
    angleTotal = constrain((angleTotal + angleStep), angleTotal, (angleTotal + (angleTotal % 120) + 120));
    
    float stepAt = floor( (angleTotal / 60) );
    
    if((stepAt % 2) == 1) {
      //angle = constrain((angle + angleStep), angle, (angle + (angle % 60) + 59.999));
      angle = constrain((angle + angleStep), angle, (angle + (angle % 60) + 60));
      
      if(!shifted) {
        shifted = true;
        direction *= -1;
        step *= direction;
      }
      
      step += ((angleStep / 60) * direction);
    } else {
      shifted = false;
    }
  }
  
  public void display() {
    push();
    
    translate(x, y);
    
    //color calccol = lerpColor(col1, col2, map((angle % 60), 0, 60, 0, 1));
    int calccol = lerpColor(red, blue, norm(step, -1, 1));
    
    stroke(calccol);
    strokeWeight(lineSize);
    
    float a_a = (radians((0.0f + angle)) - HALF_PI);
    line(0, 0, (cos(a_a) * obj_radius), (sin(a_a) * obj_radius));
    
    float a_b = (radians((120.0f + angle)) - HALF_PI);
    line(0, 0, (cos(a_b) * obj_radius), (sin(a_b) * obj_radius));
    
    float a_c = (radians((240.0f + angle)) - HALF_PI);
    line(0, 0, (cos(a_c) * obj_radius), (sin(a_c) * obj_radius));
    
    pop();
  }
}
  public void settings() {  size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "rotating_ys" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
