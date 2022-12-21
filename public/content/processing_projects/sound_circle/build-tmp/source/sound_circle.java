import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class sound_circle extends PApplet {



float angle_step = 1;

int frames_per_sample = 3;

int col_start = color(21, 107, 193);
int col_end = color(6, 229, 78);

SoundFile sample;
Amplitude amp;

ArrayList<SampleDot> sample_dots;
FloatList pending_amplitudes;

public void setup() {
	
	
	//device = new AudioDevice(this, 44000, bands);
	
	sample = new SoundFile(this, "alpines-chances-cyril-hahn-remix-mono.mp3");
	//sample = new SoundFile(this, "cyril-hahn-say-my-name-mono.mp3");
	sample.loop();
	
	//song = new SoundFile(this, "alpines-chances-cyril-hahn-remix.mp3");
	//song.loop();
	
	//fft = new FFT(this, bands);
	//fft.input(sample);
	
	amp = new Amplitude(this);
	amp.input(sample);
	
	pending_amplitudes = new FloatList();
	sample_dots = new ArrayList<SampleDot>();
}

public void draw() {
	background(30);
	
	// fft.analyze(spectrum);
	
	// for(int i = 0; i < bands; i++) {
	// 	line((width/2)+i, height, (width/2)+i, height - spectrum[i] * height);
	// }
	
	pending_amplitudes.append(amp.analyze());
	
	if((frameCount % frames_per_sample) == 0) {
		int num_amps = pending_amplitudes.size();
		float total_amps = 0.0f;
		
		for(float amp_i : pending_amplitudes) {
			total_amps += amp_i;
		}
		
		pending_amplitudes.clear();
		
		sample_dots.add(new SampleDot( (total_amps / num_amps) ));
		//sample_dots.add(new SampleDot(amp.analyze()));
	}
	
	for(SampleDot dot : sample_dots) {
		dot.render();
		dot.update(angle_step);
	}
	
	for(int i = sample_dots.size() - 1; i >= 0; i--) {
		SampleDot dot = sample_dots.get(i);
		
		if(dot.isDone()) {
			sample_dots.remove(i);
		}
	}
	
	if(frameCount == 400) { save("preview.png"); }
}
class SampleDot {
	float amount;
	float angle;
	
	float start_from_center = 10;
	int num_dots = 35;
	float divider = 8;
	float radius = 4;
	
	SampleDot(float amount_) {
		amount = amount_;
		angle = 0;
	}
	
	public void update(float angleStep) {
		angle += angleStep;
	}
	
	public void render() {
		pushMatrix();
		pushStyle();
		
		translate((width / 2), (height / 2));
		
		noStroke();
		
		float mapped_i = (map(amount, 0, 1, 0, num_dots) * 2);
		
		for(int i = 0; i < num_dots; i++) {
			float raw_radius = (start_from_center + (divider * i));
			
			float x = (cos(radians(-90 + angle)) * raw_radius);
			float y = (sin(radians(-90 + angle)) * raw_radius);
			
			if(i <= mapped_i) {
				int col = lerpColor(col_start, col_end, map(i, 0, num_dots, 0, 1));
				
				if(angle > 270) {
					float opacity = map(angle, 270, 359, 255, 0);
					
					fill(red(col), green(col), blue(col), opacity);
				} else {
					fill(col);
				}
				
				ellipseMode(CENTER);
				ellipse(x, y, radius, radius);
			}
		}
		
		popStyle();
		popMatrix();
	}
	
	public boolean isDone() {
		return (angle >= 360);
	}
}
  public void settings() { 	size(640, 640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "sound_circle" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
