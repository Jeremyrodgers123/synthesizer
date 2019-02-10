import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class AudioPlayer {
	private Clip c;
	AudioFormat format16;
	
	public AudioPlayer(Source gen) throws LineUnavailableException {
		c = AudioSystem.getClip();
		format16 = new AudioFormat(c.getFormat().getSampleRate(), 16, 1, true, false);
		getAudioClip(gen);
	}
	public void getAudioClip(Source gen) throws LineUnavailableException {
		
		c.open(format16, gen.getSound().bytes, 0, gen.getSound().bytes.length); //reads data from my byte array to play it

		System.out.println("about to play");

		c.start(); //plays it

		c.loop(2); //plays it 2 more times if desired

		while(c.getFramePosition() < gen.getSound().getSampleRate() || c.isActive() || c.isRunning()){} //makes sure the program doesn't quit before the sound plays

		System.out.println("done");

		c.close();
	}
}
