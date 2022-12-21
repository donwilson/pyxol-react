	
	class Blob {
		PVector center;
		float radius;
		
		color col_bg;
		color col_shell;
		
		int hue_pink = 204;
		int hue_aqua = 126;
		int hue_yellow = 62;
		
		AnimFrame scaler_pink;
		AnimFrame scaler_aqua;
		AnimFrame scaler_yellow;
		
		float pink_angle;
		float aqua_angle;
		float yellow_angle;
		
		float pink_arm_length;
		float aqua_arm_length;
		float yellow_arm_length;
		
		int tmp_anim_length = 100;
		int anim_frames = 0;
		int anim_frame_length = 0;
		
		float max_arm_length;
		float hand_radius;
		
		Blob(PVector center_, float radius_) {
			center = center_.copy();
			radius = radius_;
			
			setArmRatio();
			setNewArmScalers();
		}
		
		Blob(float cx_, float cy_, float radius_) {
			center = new PVector(cx_, cy_);
			radius = radius_;
			
			setArmRatio();
			setNewArmScalers();
		}
		void setArmRatio() {
			max_arm_length = (radius * 2 * 2);
			hand_radius = (radius * 0.33);
		}
		
		void setNewArmAngles() {
			// center
			aqua_angle = random(0, 360);
			
			// left
			pink_angle = (aqua_angle - 30);
			
			// right
			yellow_angle = (aqua_angle + 30);
			
			// overflow fix
			aqua_angle %= 360;
			pink_angle %= 360;
			yellow_angle %= 360;
		}
		
		void setBG(color col_bg_) { col_bg = col_bg_; }
		void setShell(color col_shell_) { col_shell = col_shell_; }
		
		void setNewArmScalers() {
			pink_arm_length = random(30, max_arm_length);
			scaler_pink = new AnimFrame(pink_arm_length);
			
			aqua_arm_length = random(30, max_arm_length);
			scaler_aqua = new AnimFrame(aqua_arm_length);
			
			yellow_arm_length = random(30, max_arm_length);
			scaler_yellow = new AnimFrame(yellow_arm_length);
			
			setNewArmAngles();
		}
		
		void update() {
			if(anim_frames > anim_frame_length) {
				// new animation step
				anim_frame_length = round(random(30, 200));
				anim_frames = 0;
			}
			
			float anim_rate = norm(anim_frames, 0, anim_frame_length);
			
			// @todo: remove animation length from each arm, allow for all arm changes at once
			if(scaler_pink.maxScaled()) {
				pink_arm_length = random(30, max_arm_length);
				scaler_pink.setNewLength(pink_arm_length);
			}
			
			if(scaler_aqua.maxScaled()) {
				aqua_arm_length = random(30, max_arm_length);
				scaler_aqua.setNewLength(aqua_arm_length);
			}
			
			if(scaler_yellow.maxScaled()) {
				yellow_arm_length = random(30, max_arm_length);
				scaler_yellow.setNewLength(yellow_arm_length);
			}
			
			scaler_pink.update();
			scaler_aqua.update();
			scaler_yellow.update();
			
			anim_frames++;
		}
		
		void render() {
			// draw arms
			drawArm(hue_pink, pink_angle, scaler_pink.getScaledValue(pink_arm_length));
			drawArm(hue_aqua, aqua_angle, scaler_aqua.getScaledValue(aqua_arm_length));
			drawArm(hue_yellow, yellow_angle, scaler_yellow.getScaledValue(yellow_arm_length));
			
			
			// draw outer circle and inner black (after arms)
			push();
			
			//translate(center.x, center.y);
			
			fill(col_bg);
			stroke(col_shell);
			strokeWeight(2);
			
			ellipse(center.x, center.y, (radius * 2), (radius * 2));
			
			pop();
			
			
			/*// hue arcs
			push();
			
			translate(center.x, center.y);
			
			ellipseMode(CENTER);
			fill(color(hue_pink, 100, 100));
			arc(, CENTER);
			
			pop();*/
		}
		
		void drawArm(float hue_, float angle_, float len_) {
			float start_x = (radius * cos(radians(angle_ - 90)));
			float start_y = (radius * sin(radians(angle_ - 90)));
			float end_x = ((radius + len_) * cos(radians(angle_ - 90)));
			float end_y = ((radius + len_) * sin(radians(angle_ - 90)));
			
			// arm
			push();
			
			translate(center.x, center.y);
			
			stroke(color(hue_, 255, 255));
			strokeWeight(1);
			noFill();
			
			line(start_x, start_y, end_x, end_y);
			
			pop();
			
			// hand
			push();
			
			translate(center.x, center.y);
			
			fill(color(hue_, 255, 255));
			noStroke();
			
			ellipseMode(CENTER);
			ellipse(end_x, end_y, (hand_radius * 2), (hand_radius * 2));
			
			pop();
		}
	}
	