import java.util.ArrayList;

public class SoundMixer implements Mixer{

	ArrayList<Source> sources;
	AudioClip result;
	public SoundMixer(){
		sources = new ArrayList<Source>();
	}
	
	public void generate() {
		
		int maxSampleRate = 0;
		double maxDuration = 0;
		for(Source source : sources) {
			int currentSampleRate = source.getSound().getSampleRate();
			double currentDuration = source.getSound().getDuration();
			if(currentSampleRate > maxSampleRate) maxSampleRate = currentSampleRate;
			if(currentDuration > maxDuration) maxDuration = currentDuration;
		}
		
		result = new AudioClip(maxDuration, maxSampleRate);
		
		for(int i = 0; i < maxSampleRate; i++) {
			//check the max value, if all sources are past max, break
			int sampleVal = 0;
			for(Source source : sources) {
				if(i > source.getSound().getSampleRate()) continue;
				sampleVal += source.getSound().getSample(i);
			}
			result.setSample(i, sampleVal);
		}
		
	}
	@Override
	public void connectInput(Source input){
		sources.add(input);
		generate();
	}

	@Override
	public AudioClip getSound() {
		return result;
	}

	@Override
	public void regenerate() {
		generate();
		
	}

}
