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

public class julia_set extends PApplet {

float aa = 0.0f;
float bb = 0.0f;

float aa_inc = 0.001f;
float bb_inc = 0.001f;

public void setup() {
  
}
 
public void draw() {
  drawJuliaSet();
  
  //aa += aa_inc;
  bb += bb_inc;
  
  if((aa >= 0.03f) || (aa <= -0.05f)) {
  	aa_inc *= -1;
  }
  
  if((bb >= 0.03f) || (bb <= -0.05f)) {
  	bb_inc *= -1;
  }
  
  if(frameCount == 50) { save("preview.png"); }
}

public void mousePressed() {
	loop();
}

public void drawJuliaSet() {
	float cX = -0.7f + aa;
	float cY = 0.27015f + bb;
	float zx, zy;
	float maxIter = 300;
	
	for (int x = 0; x < width; x++) {
    for (int y = 0; y < height; y++) {
      zx = 1.5f * (x - width / 2) / (0.5f * width);
      zy = (y - height / 2) / (0.5f * height);
      float i = maxIter;
      while (zx * zx + zy * zy < 4 && i > 0) {
        float tmp = zx * zx - zy * zy + cX;
        zy = 2.0f * zx * zy + cY;
        zx = tmp;
        i -= 1;
      }
      int c = hsv2rgb(i / maxIter * 360, 1, i > 1 ? 1 : 0);
      set(x, y, c);
    }
  }
}

public int hsv2rgb(float h, float s, float v) {
  float c = v * s;
  float x = c * (1 - abs(((h/60) % 2) - 1));
  float m = v - c;
 
  float r, g, b;
  if (h < 60) {
    r = c;
    g = x;
    b = 0;
  } else if (h < 120) {
    r = x;
    g = c;
    b = 0;
  } else if (h < 180) {
    r = 0;
    g = c;
    b = x;
  } else if (h < 240) {
    r = 0;
    g = x;
    b = c;
  } else if (h < 300) {
    r = x;
    g = 0;
    b = c;
  } else {
    r = c;
    g = 0;
    b = x;
  }
 
  int ri = round((r + m) * 255);
  int gi = round((g + m) * 255);
  int bi = round((b + m) * 255);
 
  return color(ri, gi, bi);
}
  public void settings() {  size(640, 480); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "julia_set" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
