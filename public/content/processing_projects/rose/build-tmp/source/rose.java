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

public class rose extends PApplet {

float square_size = 100;
float radius = 40;
int num_n = 7;
int num_d = 9;

public void rose(float n, float d) {
  float k = (n / d);
  
  float cx = ((square_size * (n - 1)) + (square_size / 2));
  float cy = ((square_size * (d - 1)) + (square_size / 2));
  
  pushMatrix();
  
  translate(cx, cy);
  
  beginShape();
  stroke(255);
  strokeWeight(1);
  noFill();
  
  float r, x, y;
  
  for (float a = 0; a < TWO_PI * d; a += 0.02f) {
    //r = 100;
    r = radius * cos(k * a);
    x = (r * cos(a));
    y = (r * sin(a));

    vertex(x, y);
    //point(x,y);
  }
  
  endShape(CLOSE);
  popMatrix();
}

public void setup() {
  
}

public void draw() {
  background(51);
  
  for(float d = 1.0f; d <= num_d; d += 1) {
    for(float n = 1.0f; n <= num_n; n += 1) {
      rose(n, d);
    }
  }
  
  if(frameCount == 50) { save("preview.png"); }
}
  public void settings() {  size(700, 900); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "rose" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
