
public class SineWave implements Source, AdjustableSource {
	AudioClip sound;
	private double frequency;
	public SineWave (int freq) {
		//time is the index
		frequency = freq;
		createSound();
		
	}
	
	private void createSound() {
		sound = new AudioClip(1, 44100);
		double maxVal = Math.pow(2, 15) - 1;
		for (int i = 0; i < sound.getSampleRate(); i++) {
			int value = (int)(maxVal * Math.sin((2 * Math.PI * frequency * i) / sound.getSampleRate()));
			sound.setSample(i, value);
		}		
	}
	
	public void setFrequency(double newFrequency) {
		frequency = newFrequency;
		createSound();
	}	
	
	public AudioClip getSound() {
		return sound;
	}

	@Override
	public void regenerate() {
		createSound();
	}

}
