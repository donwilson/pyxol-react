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

public class ultra_map extends PApplet {

PVector camera_angle;

Table table;
float r = 200;

int max_elevation = 0;
int min_elevation = 0;

PImage earth;
PShape globe;


public Table convertRawTable(String file_name, String optional_flags) {
	Table new_table = new Table();
	
	new_table.addColumn("name", Table.STRING);
	new_table.addColumn("elevation", Table.INT);
	new_table.addColumn("latitude", Table.FLOAT);
	new_table.addColumn("longitude", Table.FLOAT);
	
	Table raw_table = loadTable(file_name, optional_flags);
	
	println(raw_table.getRowCount() +" total rows in "+ file_name);
	
	for(TableRow row : raw_table.rows()) {
		String raw_lat = row.getString("latitude");
		String lat_which = raw_lat.substring(0, 1);
		float lat_val = abs(PApplet.parseFloat(raw_lat.substring(1)));
		
		if(lat_which.equals("S")) {
			lat_val *= -1.0f;
		}
		
		String raw_lon = row.getString("longitude");
		String lon_which = raw_lon.substring(0, 1);
		float lon_val = abs(PApplet.parseFloat(raw_lon.substring(1)));
		
		if(lon_which.equals("W")) {
			lon_val *= -1.0f;
		}
		
		//println(row.getString("name") +" ("+ row.getInt("elevation") +"ft): "+ lat_which +":"+ lat_val +" "+ lon_which +":"+ lon_val);
		
		TableRow newRow = new_table.addRow();
		newRow.setString("name", row.getString("name"));
		newRow.setInt("elevation", row.getInt("elevation"));
		newRow.setFloat("latitude", lat_val);
		newRow.setFloat("longitude", lon_val);
	}
	
	return new_table;
}


public void setup() {
	// load assets
	earth = loadImage("data/earth.jpg");
	table = convertRawTable("data/ultras.csv", "header, csv");
	
	
	
	camera_angle = new PVector(-180.0f, 0.0f);
	
	// find min and max elevations
	for(TableRow row : table.rows()) {
		int elev = row.getInt("elevation");
		
		if(elev > max_elevation) {
			max_elevation = elev;
		}
		
		if((min_elevation == 0) || (elev < min_elevation)) {
			min_elevation = elev;
		}
	}
	
	
	//println("elevation between: "+ min_elevation +" & "+ max_elevation);
	
	noStroke();
	globe = createShape(SPHERE, r);
	//globe.rotateY(radians(180));
	globe.setTexture(earth);
}

public void draw() {
	background(30);
	translate((width / 2), (height / 2));
	
	rotateY(radians(camera_angle.x));
	rotateX(radians(camera_angle.y));
	
	
	
	float r = 200;
	
	lights();
	fill(200);
	noStroke();
	//sphere(r);
	// -2704
	shape(globe);
	
	for(TableRow row : table.rows()) {
		int elev = row.getInt("elevation");
		float lat = row.getFloat("latitude");
		float lon = row.getFloat("longitude");
		
		float theta = radians(lat) + PI/2;
		float phi = radians(-lon) + PI;
		
		float x = (r * sin(theta) * cos(phi));
		float y = (r * cos(theta));
		float z = (r * sin(theta) * sin(phi));
		
		pushStyle();
		pushMatrix();
		
		ambient(lerpColor(
			color(255, 255, 255),
			color(255, 0, 0),
			map(elev, min_elevation, max_elevation, 0, 1)
		));
		
		PVector pos = new PVector(x, y, z);
		
		float h = map(elev, min_elevation, max_elevation, 20.0f, 200.0f);
		
		PVector xaxis = new PVector(1, 0, 0);
		float angleb = PVector.angleBetween(xaxis, pos);
		PVector raxis = xaxis.cross(pos);
		
		
		translate(x, y, z);
		rotate(angleb, raxis.x, raxis.y, raxis.z);
		box(h, 2, 2);
		
		popMatrix();
		popStyle();
	}
	
	if(frameCount == 50) { save("preview.png"); }
}

public void mouseDragged() {
	camera_angle.add(
		((mouseX - pmouseX) * 0.5f),
		((mouseY - pmouseY) * 0.5f)
	);
}
  public void settings() { 	size(640, 640, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ultra_map" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
