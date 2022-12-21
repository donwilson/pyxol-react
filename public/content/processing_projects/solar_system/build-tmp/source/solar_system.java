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

public class solar_system extends PApplet {

Planet sun;

public void setup() {
  
  sun = new Planet(50, 0, 0);
  sun.spawnMoons(5, 1);
}

public void draw() {
  background(0);
  translate((width / 2), (height / 2));
  sun.show();
  sun.orbit();
  
  if(frameCount == 50) { save("preview.png"); }
}
class Planet {
  float radius;
  float distance;
  
  Planet[] planets;
  
  float angle;
  float orbitspeed;
  
  Planet(float r, float d, float o) {
    radius = r;
    distance = d;
    angle = random(TWO_PI);
    orbitspeed = o;
  }
  
  public void orbit() {
    angle = angle + orbitspeed;
    if(planets != null) {
      for(int i = 0; i < planets.length; i++) {
        planets[ i ].orbit();
      }
    }
  }
  
  public void spawnMoons(int total, int level) {
    planets = new Planet[total];
    
    for(int i = 0; i < planets.length; i++) {
      float r = (radius / (level * 2));
      float d = random(50, 150);
      float o = random(-0.1f, 0.1f);
      
      planets[ i ] = new Planet(r, (d / level), o);
      
      if(level < 3) {
        int num = PApplet.parseInt(random(0, 4));
        planets[ i ].spawnMoons(num, (level + 1));
      }
    }
  }
  
  public void show() {
    pushMatrix();
    fill(255, 100);
    rotate(angle);
    translate(distance, 0);
    ellipse(0, 0, (radius * 2), (radius * 2));
    
    if(planets != null) {
      for(int i = 0; i < planets.length; i++) {
        planets[ i ].show();
      }
    }
    
    popMatrix();
  }
}
  public void settings() {  size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "solar_system" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
