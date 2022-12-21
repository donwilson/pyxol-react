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

public class ocean_boxes extends PApplet {

float zz;
float aa;
boolean clicked;

public void setup() {
  

  aa = 0;
  zz = 0;
  clicked = false;
}

public void draw() {
  background(175);

  //if(clicked) {
  //  ortho();
  //} else {
  //perspective();
  //}

  translate(width/2, height/2);

  rotateX(radians(-30));
  rotateY(QUARTER_PI);//rotateY(QUARTER_PI + zz);

  int x;
  int y;
  float by;
  float num = 80;
  float size = ((width * 0.66f) / num);

  float yoff;
  float xoff;
  float byc;
  float bys;
  float step = 0.25f;//float step = (num / size);
  float distance;
  float block_height_increase;
  
  for (y = 0; y < num; y++) {
    yoff = ((-(num / 2) + y) * size) + size;

    for (x = 0; x < num; x++) {
      xoff = ((-(num / 2) + x) * size) + size;

      //distance = dist(x + 0.5, y + 0.5, (num / 2), (num / 2));
      //
      //if (clicked && (distance < (num / 2))) {
      //  continue;
      //}

      pushMatrix();

      by = noise((step * x), (step * y), zz);
      block_height_increase = map((size * by), 0, size, 1, (size*3));
      
      translate(xoff, (size - (block_height_increase / 2)), yoff);
      
      //noStroke();
      fill(0, 0, map(by, 0, 1, 0, 255));

      box(size, (size + block_height_increase), size);

      popMatrix();
    }
  }

  zz += 0.01f;
  
  if(frameCount == 50) { save("preview.png"); }
}

public void mouseWheel(MouseEvent event) {
  float e = event.getCount();

  if (e > 0) {
    aa -= 0.1f;
  } else if (e < 0) {
    aa += 0.1f;
  }
}

public void mousePressed() {
  clicked = !clicked;
}

  public void settings() {  size(500, 500, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ocean_boxes" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
