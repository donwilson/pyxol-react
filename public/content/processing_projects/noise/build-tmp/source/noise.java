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

public class noise extends PApplet {

float a;
float b;
float bps;
float blue;
float alph;
int id = 5;
int tdbg = 0;
float max_wave_height;
float xx;
float yy;
float zz;

public void setup() {
  
  //frameRate(60);
  
  a = 0.05f;
  b = 0;
  bps = 0.26f;
  max_wave_height = (height * 0.6f);
}

public void debugText(String str) {
  tdbg += 1;
  text(str, 15, (15 * tdbg));
}

public void draw() {
  tdbg = 0;
  
  background(255);
  //background(0, 191, 255);
  //background(135, 206, 250);
  //background(70, 130, 180);
  //background(135, 206, 250);
  //background(20, 20, 20);
  
  //debugText("fps = "+ Float.toString(frameRate));//debugText("fps = "+ Integer.toString(round(frameRate)));
  //debugText("bps = "+ Float.toString(bps));
  //debugText("a = "+ Float.toString(a));
  //debugText("num a = "+ Integer.toString(floor(1 / a)));
  //debugText("b = "+ Float.toString(b));
  //debugText("alph = "+ Float.toString(alph));
  
  zz = 0;
  
  for(float z = 1; z >= 0; z -= a) {
    alph = map(z, 0, 1, 60, 40);
    blue = map(z, 0, 1, 150, 200);
    fill(0, 0, blue, alph);
    noStroke();
    
    beginShape();
    //stroke(0, 0, blue, alph);
    
    vertex(0, height);
    
    xx = 0;
    yy = map(noise(0, zz + b, zz), 0, 1, (height - max_wave_height), height);
    
    vertex(xx, yy);
    
    for(float i = 0; i <= 1; i += a) {
      xx = (width * i);
      yy = map(noise(i, zz + b, zz), 0, 1, (height - max_wave_height), height);
      
      curveVertex(xx, yy);
    }
    
    xx = width;
    yy = map(noise(1, zz + b, zz), 0, 1, (height - max_wave_height), height);
    vertex(xx, yy);
    
    vertex(width, height);
    
    endShape();
    
    zz += a;
  }
  
  //b += ((frameRate / 60) * bps);
  b += ((frameRate / 60) / 60) * bps;
  //b += (a / frameRate);
  
  if(frameCount == 50) { save("preview.png"); }
}

public void mouseWheel(MouseEvent event) {
  float e = event.getCount();
  
  if(e > 0) {
    bps = max(0.01f, (bps - 0.01f));
  } else if(e < 0) {
    bps += 0.01f;
  }
}
  public void settings() {  size(500, 500); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "noise" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
