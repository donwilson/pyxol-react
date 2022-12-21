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

public class camera_drag extends PApplet {

int num_x = 15;
int num_y = 15;
int ballRadius = 2;
int spacing = 20;


// resting angle
float xx = 45;
float yy = 0;
float zz = 256;

// source click - where first clicked to drag camera
float sx = 0;
float sy = 0;
float rspeed = 0.5f;

public void push() {
  pushMatrix();
  pushStyle();
}

public void pop() {
  popStyle();
  popMatrix();
}

public void setup() {
  
}

public void draw() {
  background(0);
  
  float dx = xx;
  float dy = yy;
  
  if(mousePressed) {
    dx += ((sy - mouseY) * rspeed);
    dy -= ((sx - mouseX) * rspeed);
  }
  
  dx %= 360;
  dy %= 360;
  
  text("x: "+ Float.toString(dx) +", y: "+ Float.toString(dy), 15, 15);
  
  translate(width/2, height/2);

  rotateX(radians(dx));
  rotateY(radians(dy));
  //rotateZ(radians(zz));
  
  float half_dist = ((spacing * (num_x - 1)) / 2);
  
  for(int y = 0; y < num_y; y++) {
    for(int x = 0; x < num_x; x++) {
      push();
      
      translate((x * spacing) - half_dist, (y * spacing) - half_dist, 0);
      
      noStroke();
      fill(22, 72, 153);
      sphere(ballRadius);
      
      pop();
    }
  }
  
  if(frameCount == 50) { save("preview.png"); }
}

public void mousePressed() {
  sx = mouseX;
  sy = mouseY;
}

public void mouseReleased() {
  xx = ((xx + ((sy - mouseY)) * rspeed) % 360);
  yy = ((yy - ((sx - mouseX)) * rspeed) % 360);
  
  sx = 0;
  sy = 0;
}
  public void settings() {  size(600, 600, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "camera_drag" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
