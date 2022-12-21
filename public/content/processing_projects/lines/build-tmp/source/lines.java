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

public class lines extends PApplet {

public void setup() {
  
}

public void draw() {
  background(240);
  
  translate((width / 2), (height / 2));
  
  float radiusStep = 50;
  float radius;
  
  for(int d = 3; d <= 6; d++) {
    radius = (radiusStep * (d - 2));
    
    float degreeStep = (360 / d);
    
    float a, px, py;
    
    push();
    beginShape();
    noFill();
    
    for(int i = 0; i < d; i++) {
      a = ((degreeStep * i) - 90);
      px = (radius * cos(radians(a)));
      py = (radius * sin(radians(a)));
      
      vertex(px, py);
    }
    
    endShape(CLOSE);
    pop();
  }
  
  if(frameCount == 50) { save("preview.png"); }
}

public void push() {
  pushStyle();
  pushMatrix();
}

public void pop() {
  popMatrix();
  popStyle();
}
  public void settings() {  size(500, 500); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "lines" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
