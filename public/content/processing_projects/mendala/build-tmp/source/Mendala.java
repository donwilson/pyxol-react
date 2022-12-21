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

public class Mendala extends PApplet {

// http://ensign.editme.com/t43dances

FloatList points;

float num_orbits;
int planet1;
int planet2;
float planet1Year;
float planet2Year;
float planet1Radius;
float planet2Radius;
float interval;
float r1;
float r2;
float r;
float rStop;
float a1;
float a1Interval;
float a2;
float a2Interval;

float minDist;
float maxDist;

public float planetYear(int planet) {
  if(planet == 1) {
    return 87.969f;
  } else if (planet == 2) {
    return 224.701f;
  } else if (planet == 3) {
    return 365.256f;
  } else if (planet == 4) {
    return 686.980f;
  } else if (planet == 5) {
    return 4332.6f;
  } else if (planet == 6) {
    return 10759.2f;
  } else if (planet == 7) {
    return 30685;
  } else if (planet == 8) {
    return 60190;
  } else if (planet == 9) {
    return 90465;
  }
  
  return 0;
}

public float planetOrbit(int planet) {
  if (planet == 1) {
    return 57.91f;
  } else if(planet == 2) {
    return 108.21f;
  } else if(planet == 3) {
    return 149.60f;
  } else if(planet == 4) {
    return 227.92f;
  } else if(planet == 5) {
    return 778.57f;
  } else if(planet == 6) {
    return 1433.5f;
  } else if(planet == 7) {
    return 2872.46f;
  } else if(planet == 8) {
    return 4495.1f;
  } else if(planet == 9) {
    return 5869.7f;
  }
  
  return 0;
}

public String planetName(int planet) {
  if(planet == 1) {
    return "Mercury";
  } else if(planet == 2) {
    return "Venus";
  } else if(planet == 3) {
    return "Earth";
  } else if(planet == 4) {
    return "Mars";
  } else if(planet == 5) {
    return "Jupiter";
  } else if(planet == 6) {
    return "Saturn";
  } else if(planet == 7) {
    return "Uranus";
  } else if(planet == 8) {
    return "Neptune";
  } else if(planet == 9) {
    return "Pluto";
  }
  
  return "";
}

public void setup() {
  
  
  num_orbits = 8;
  
  planet1 = 3;//planet1 = round(random(1, 9));
  planet2 = 4;//planet2 = round(random(1, 9));
  
  println("Planet #1: "+ planetName(planet1));
  println("Planet #2: "+ planetName(planet2));
  
  planet1Year = planetYear(planet1);
  planet2Year = planetYear(planet2);
  
  planet1Radius = planetOrbit(planet1);
  println("planet1Radius = "+ Float.toString(planet1Radius));
  planet2Radius = planetOrbit(planet2);
  println("planet2Radius = "+ Float.toString(planet2Radius));
  
  // calculate radii
  if(planet2Radius > planet1Radius) {
    r2 = (height / 2);
    r1 = ((r2 * planet1Radius) / planet2Radius);
    minDist = (r2 - r1);
    maxDist = (r2 + r1);
  } else {
    r1 = (height / 2);
    r2 = ((r1 * planet2Radius) / planet1Radius);
    minDist = (r1 - r2);
    maxDist = (r1 + r2);
  }
  
  println("r1 = "+ Float.toString(r1));
  println("r2 = "+ Float.toString(r2));
  
  r = 0;
  if(planet1Year > planet2Year) {
    interval = planet1Year / 75;
    rStop = (planet1Year * num_orbits);
  } else {
    interval = planet2Year / 75;
    rStop = (planet2Year * num_orbits);
  }
  println("interval = "+ Float.toString(interval));
  println("rStop = "+ Float.toString(rStop));
  
  a1 = 0;
  a1Interval = ((2 * PI * interval) / planet1Year);
  println("a1Interval = "+ Float.toString(a1Interval));
  
  a2 = 0;
  a2Interval = ((2 * PI * interval) / planet2Year);
  println("a2Interval = "+ Float.toString(a2Interval));
  
  points = new FloatList();
}

public void draw() {
  background(0);
  
  translate((width / 2), (height / 2));
  
  pushStyle();
  //noStroke();
  fill(255, 255, 0);
  ellipseMode(CENTER);
  ellipse(0, 0, 4, 4);
  popStyle();
  
  float x1, y1;
  float x2, y2;
  
  a1 = (a1 - a1Interval);
  a2 = (a2 - a2Interval);
  
  x1 = (r1 * cos(a1));
  y1 = (r1 * sin(a1));
  
  x2 = (r2 * cos(a2));
  y2 = (r2 * sin(a2));
  
  points.push(x1);
  points.push(y1);
  points.push(x2);
  points.push(y2); 
  
  stroke(255, 40);
  //strokeWeight(map(dist(x1, y1, x2, y2), minDist, maxDist, 0.2, 1));
  //line(x1, y1, x2, y2);
  
  int ps = points.size();
  
  for(int pj = 0; pj < ps; pj += 4) {
    line(
      points.get(pj),
      points.get( (pj + 1) ),
      points.get( (pj + 2) ),
      points.get( (pj + 3) )
    );
  }
  
  r += interval;
  
  if(r > rStop) {
    noLoop();
    
    return;
  }
  
  fill(255);
  ellipse(x1, y1, 6, 6);
  ellipse(x2, y2, 6, 6);
  
  if(frameCount == 150) { save("preview.png"); }
}
  public void settings() {  size(500, 500); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Mendala" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
