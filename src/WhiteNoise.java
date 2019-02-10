import java.util.Random;

public class WhiteNoise implements Source  {
	AudioClip sound;
	public WhiteNoise () {
		createSound();
	}
	
	private void createSound() {
		sound = new AudioClip(1, 44100);
		double maxVal = Math.pow(2, 15) - 1;
		for (int i = 0; i < sound.getSampleRate(); i++) {
			//int value = (int)(Math.random() * maxVal +1);
			Random random = new Random();
			int value = random.nextInt((int)maxVal + 1 + (int)(Math.pow(2, 15))) - (int)Math.pow(2, 15); 
			sound.setSample(i, value);
		}		
	}
	
	public AudioClip getSound() {
		return sound;
	}

	@Override
	public void regenerate() {
		createSound();
	}

}
