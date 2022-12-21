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

public class cubicolor extends PApplet {

float box_size;
float box_spacing;
float num = 15;
float yy;

public void push() {
  pushStyle();
  pushMatrix();
}

public void pop() {
  popMatrix();
  popStyle();
}

public void setup() {
  
  
  
  yy = 0;
  
  box_size = ((width * 0.5f) / (num * 2));
  box_spacing = box_size;
}

public void draw() {
  background(30);
  
  translate(width/2, height/2);

  rotateX(radians(-30) + yy);
  rotateY(QUARTER_PI * 3 + yy);
  
  float xoff, yoff, zoff;
  float byc;
  
  int x, y, z;
  
  for (z = 0; z < num; z++) {
    //zoff = ((-(num / 2) + z) * box_size) + box_size;
    zoff = ((-(num / 2) + z) * (box_size + box_spacing)) + box_size;
    
    for (y = 0; y < num; y++) {
      //yoff = ((-(num / 2) + y) * box_size) + box_size;
      yoff = ((-(num / 2) + y) * (box_size + box_spacing)) + box_size;
      
      for (x = 0; x < num; x++) {
        //xoff = ((-(num / 2) + x) * box_size) + box_size;
        xoff = ((-(num / 2) + x) * (box_size + box_spacing)) + box_size;
        
        push();
        
        translate(xoff, yoff, zoff);
        noStroke();
        fill(
          map(x, 0, (num - 1), 0, 255),
          map(y, 0, (num - 1), 0, 255),
          map(z, 0, (num - 1), 0, 255)
        );
        box(box_size, box_size, box_size);
        
        pop();
      }
    }
  }
  
  yy += 0.01f;
  
  if(frameCount == 50) { save("preview.png"); }
}
  public void settings() {  size(500, 500, P3D);  smooth(8); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "cubicolor" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
