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

public class sketch_3d_terrain extends PApplet {

boolean animating = true;
boolean wireframe = false;

int cols, rows;
int scl = 8;
int w = 1600;
int h = 1200;
float xyoffstep;

float flightSpeedPS = 2;
float flying = 0;
float framescl = 0;

float[][] terrain;

public void setup() {
  
  
  cols = ceil( (w / scl) );
  rows = ceil( (h / scl) );
  
  xyoffstep = 0.05f;
  
  terrain = new float[cols][rows];
}

public void draw() {
  float delta = ((frameRate / 60.0f) / 60.0f);
  
  flying -= (flightSpeedPS * delta);
  
  float yoff = flying;
  
  for(int y = 0; y < rows; y++) {
    float xoff = 0;
    
    for(int x = 0; x < cols; x++) {
      terrain[ x ][ y ] = map(noise(xoff, yoff), 0, 1, -100, 100);
      
      xoff += xyoffstep;
    }
    
    yoff += xyoffstep;
  }
  
  background(0);
  
  text("scl = "+ Integer.toString(scl), 15, 15);
  text("xyoffstep = "+ Float.toString(xyoffstep), 15, 30);
  text("xyoffstep/scl  = "+ Float.toString(xyoffstep/scl), 15, 45);
  
  translate(width/2, height/2+50);
  rotateX(PI/3);
  
  
  if(wireframe) {
    stroke(200);
    noFill();
  } else {
    fill(255);
    noStroke();
    //directionalLight(51, 250, 126, 0, 1, 0);
    //pointLight(51, 250, 126, width/2, height/2, 1000);
    
    //
    
    
    // set light
    pushMatrix();
    translate(-width/2, -height/2+50);
    
    float plX = (width / 2);//float plX = (mouseX - (width / 2));
    float plY = height;//float plY = (mouseY - (height / 2));
    float plZ = (200);
    
    translate(plX, plY, plZ);
    stroke(255);
    noFill();
    box(15);
    
    pointLight(51, 250, 126, 0, 0, 0);
    popMatrix();
    
    noStroke();
    fill(255);
    
    
    /*
    // draw dummy boxes
    pushMatrix();
    translate(width/2 - (12 * 10), height/2 - (12 * 5));
    
    stroke(100);
    fill(255);
    
    for(int yy = 0; yy < 10; yy++) {
      for(int xx = 0; xx < 20; xx++) {
        pushMatrix();
        translate((xx * 12), (yy * 12), 0);
        box(10);
        popMatrix();
      }
    }
    popMatrix();
    */
  }
  
  /*
  translate(width/2, height/2+50);
  rotateX(PI/3);
  */
  
  translate((-w / 2), (-h / 2));
  
  for(int y = 0; y < (rows - 1); y++) {
    beginShape(TRIANGLE_STRIP);
    
    for(int x = 0; x < cols; x++) {
      vertex((x * scl), (y * scl), terrain[ x ][ y ]);
      vertex((x * scl), ((y + 1) * scl), terrain[ x ][ (y + 1) ]);
    }
    
    endShape();
  }
  
  if(!animating) {
    noLoop();
  }
  
  if(frameCount == 50) { save("preview.png"); }
}

public void mouseClicked() {
  if(mouseButton == LEFT) {
    loop();
  } else if(mouseButton == RIGHT) {
    animating = !animating;
    loop();
  } else if(mouseButton == CENTER) {
    wireframe = !wireframe;
    loop();
  }
}

public void mouseWheel(MouseEvent event) {
  float d = event.getCount();
  
  if(d < 0) {
    xyoffstep += 0.01f;
  } else if(d > 0) {
    xyoffstep -= 0.01f;
  }
}
  public void settings() {  size(600, 600, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "sketch_3d_terrain" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
