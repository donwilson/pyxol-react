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

public class triangle_triangles extends PApplet {

Shape[] shapes;
int num_tris = 17;
float tri_radius = 16.0f;

public int nth_triangle(int t) {
	return ((PApplet.parseInt(sq(PApplet.parseFloat(t))) + t) / 2);
}

public float nth_triangle(float t) {
	return ((sq(t) + t) / 2);
}

public float triangleWidth(float radius) {
	return ((cos(radians(30)) - cos(radians(150))) * radius);
}

public float triangleHeight(float radius) {
	return ((sin(radians(150)) - sin(radians(-90))) * radius);
}

public void setup() {
	
	colorMode(HSB, 100);
	
	shapes = new Shape[ nth_triangle(num_tris) ];
	//shapes = new Shape[ ((nth_triangle(num_tris) * 2) - num_tris) ];
	int si = 0;
	
	PVector pos = new PVector(0.0f, 0.0f);
	
	pos.x += (width / 2);
	pos.y += ((height / 2) - (((triangleHeight(tri_radius) * num_tris) / 2) - (triangleHeight(tri_radius) / 2)));
	
	for(int i = 1; i <= num_tris; i++) {
		PVector tri_pos = new PVector();
		tri_pos = pos.copy();
		
		for(int t = 0; t < i; t++) {
			shapes[ si ] = new Shape(ShapeType.TRIANGLE, tri_pos, tri_radius);
			
			si++;
			
			tri_pos.add(triangleWidth(tri_radius), 0.0f);
		}
		
		pos.add((triangleWidth(tri_radius) * -0.5f), triangleHeight(tri_radius));
	}
	
	/*// add upside down triangles
	int lsi = 0;
	
	for(int i = 1; i < num_tris; i++) {
		for(int t = 0; t < i; t++) {
			PVector u_pos = new PVector();
			u_pos = shapes[ lsi ].center.copy();
			u_pos.add(0, (triangleHeight(tri_radius) / 2) + 4.3);
			
			shapes[ si ] = new Shape(ShapeType.UPSIDE_DOWN_TRIANGLE, u_pos, tri_radius);
			
			si++;
			lsi++;
		}
	}*/
	
	// find most distant triangle from center (bottom right) and max distance on all
	PVector last_tri_center = new PVector();
	last_tri_center = shapes[ (nth_triangle(num_tris) - 1) ].center.copy();
	float max_dist = dist(last_tri_center.x, last_tri_center.y, (width / 2), (height / 2));
	
	for(int i = 0; i < shapes.length; i++) {
		shapes[ i ].setMaxDistance(max_dist);
	}
}

public void draw() {
	background(20, 10, 10);
	
	for(int i = 0; i < shapes.length; i++) {
		shapes[ i ].update();
		shapes[ i ].render();
	}
	
	if(frameCount == 50) { save("preview.png"); }
}
enum ShapeType {
	TRIANGLE,
	UPSIDE_DOWN_TRIANGLE
}

class Shape {
	ShapeType type;
	PVector center;
	float radius;
	float max_distance;
	
	int step = 0;
	int step_cycle = 180;
	float angle;
	
	float x1, y1;
	float x2, y2;
	float x3, y3;
	int col;
	
	Shape(ShapeType type_, PVector center_, float radius_) {
		type = type_;
		
		center = new PVector();
		center = center_.copy();
		
		radius = radius_;
		
		col = color(100);
		
		// default max distance from center to edge of sketch, set more accurately later
		setMaxDistance(dist(0, 0, (width / 2), (height / 2)));
		
		// calculate initial points
		calculatePoints(radius);
	}
	
	public void setColor(int col_) {
		col = col_;
	}
	
	public void setMaxDistance(float max_distance_) {
		max_distance = max_distance_;
		
		// setup starting step
		float distance_to_center = distanceToCenter();
		
		step = floor(map(
			distance_to_center,
			0,
			abs(max_distance),
			(step_cycle - 1),
			0
		));
		
		calculateColorBasedOnDistance();
	}
	
	public void calculateColorBasedOnDistance() {
		float distance_to_center = distanceToCenter();
		
		col = color(map(distance_to_center, 0, max_distance, 0, 100), 100, 100);
	}
	
	public void setAngle(float angle_) {
		angle = angle_;
	}
	
	public float width() {
		return ((cos(radians(30)) - cos(radians(150))) * radius);
	}
	
	public float height() {
		return ((sin(radians(150)) - sin(radians(-90))) * radius);
	}
	
	public float distanceToCenter() {
		// calculate distance
		return dist(center.x, center.y, (width / 2), (height / 2));
	}
	
	public void calculatePoints(float r_) {
		if(type == ShapeType.TRIANGLE) {
			x1 = (center.x + (cos(radians(-90 + angle)) * r_));
			y1 = (center.y + (sin(radians(-90 + angle)) * r_));
			
			x2 = (center.x + (cos(radians(30 + angle)) * r_));
			y2 = (center.y + (sin(radians(30 + angle)) * r_));
			
			x3 = (center.x + (cos(radians(150 + angle)) * r_));
			y3 = (center.y + (sin(radians(150 + angle)) * r_));
		} else if(type == ShapeType.UPSIDE_DOWN_TRIANGLE) {
			x1 = (center.x + (cos(radians(90 + angle)) * r_));
			y1 = (center.y + (sin(radians(90 + angle)) * r_));
			
			x2 = (center.x + (cos(radians(-30 + angle)) * r_));
			y2 = (center.y + (sin(radians(-30 + angle)) * r_));
			
			x3 = (center.x + (cos(radians(-150 + angle)) * r_));
			y3 = (center.y + (sin(radians(-150 + angle)) * r_));
		}
	}
	
	public void calculateStepAngle() {
		int steps[];
		
		if(type == ShapeType.UPSIDE_DOWN_TRIANGLE) {
			steps = new int[2];
			steps[0] = 0;
			steps[1] = 90;
			//steps[2] = 200;
			//steps[3] = 300;
			//steps[4] = 400;
		} else {
			steps = new int[2];
			steps[0] = 0;
			steps[1] = 90;
			//steps[2] = 200;
			//steps[3] = 300;
			//steps[4] = 400;
		}
		
		
		int step_size = 45;
		int step_multiplier = 1;
		
		for(int i = 0; i < steps.length; i++) {
			if((step >= steps[ i ]) && (step <= (steps[ i ] + step_size))) {
				angle = map(step, steps[ i ], (steps[ i ] + step_size), 0, (360 * step_multiplier));
				
				break;
			}
			
			step_multiplier *= -1;
		}
		
		// trim angle
		if(angle >= 360) {
			angle = 0;
		} else if(angle <= -360) {
			angle = 0;
		}
	}
	
	public int calculateAngledColor() {
		if((angle > 0) || (angle < 0)) {
			return col;
		}
		
		/*if(angle > 0) {
			float angle_amt = map(angle, 1, 359, 0, 1);
			
			//return color(map(angle, 1, 359, 0, 100), 100, 100);
			
			return lerpColor(
				color(100),
				col,
				angle_amt
			);
		} else if(angle < 0) {
			float angle_amt = map(angle, -1, -359, 0, 1);
			
			//return color(map(angle, -1, -359, 0, 100), 100, 100);
			return lerpColor(
				col,
				color(100),
				angle_amt
			);
		} else {*/
			if(type == ShapeType.UPSIDE_DOWN_TRIANGLE) {
				return color(20, 10, 10, 0);
			}
			
			return color(100);
		//}
	}
	
	public void update() {
		// set next step
		step += 1;
		
		if(step > step_cycle) {
			step = 1;
		}
		
		calculateStepAngle();
		
		float step_radius = radius;
		
		if(angle != 0) {
			float middle_angle = 180;
			float radius_min = (radius * 0.3f);
			
			step_radius = (radius - (radius_min * (1 - (abs(abs(angle) - middle_angle) / middle_angle))));
		}
		
		// calculate new points
		calculatePoints(step_radius);
	}
	
	public void render() {
		pushMatrix();
		
		//white: fill(100);
		//distance color: fill(map(distanceToCenter(), 0, (width / 2), 100, 0), 100, 100);
		
		fill(calculateAngledColor());
		
		noStroke();
		
		triangle(x1, y1, x2, y2, x3, y3);
		
		popMatrix();
	}
}
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "triangle_triangles" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
