import processing.sound.*;

float angle_step = 1;

int frames_per_sample = 3;

color col_start = color(21, 107, 193);
color col_end = color(6, 229, 78);

SoundFile sample;
Amplitude amp;

ArrayList<SampleDot> sample_dots;
FloatList pending_amplitudes;

void setup() {
	size(640, 640);
	
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

void draw() {
	background(30);
	
	// fft.analyze(spectrum);
	
	// for(int i = 0; i < bands; i++) {
	// 	line((width/2)+i, height, (width/2)+i, height - spectrum[i] * height);
	// }
	
	pending_amplitudes.append(amp.analyze());
	
	if((frameCount % frames_per_sample) == 0) {
		int num_amps = pending_amplitudes.size();
		float total_amps = 0.0;
		
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