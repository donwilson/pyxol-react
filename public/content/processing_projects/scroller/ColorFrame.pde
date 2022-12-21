	
	class AnimFrame {
		int frame_length;
		int frame_at = 0;
		
		AnimFrame(int frame_length_) {
			setNewLength(frame_length_);
		}
		
		AnimFrame(float frame_length_) {
			setNewLength(frame_length_);
		}
		
		void setNewLength(int frame_length_) {
			frame_length = frame_length_;
		}
		
		void setNewLength(float frame_length_) {
			frame_length = round(frame_length_);
		}
		
		void update() {
			if(frame_at > frame_length) {
				frame_at = 0;
			}
			
			frame_at++;
		}
		
		boolean maxScaled() {
			return (frame_at > frame_length);
		}
		
		float getScaledValue(int scaler) {
			return (scaler * norm(frame_at, 0, frame_length));
		}
		
		float getScaledValue(float scaler) {
			return (scaler * norm(frame_at, 0, frame_length));
		}
	}
	