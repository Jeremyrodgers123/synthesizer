public class Volume implements Filter{

	double scale;
	Source inputSource;
	AudioClip result;
	public Volume(double s){
		scale = s;
	}
	
	public AudioClip generate(){
		AudioClip original = inputSource.getSound();
		int newSampleRate = original.getSampleRate();
		double newDuration = original.getDuration();
		result = new AudioClip(newDuration ,newSampleRate);
		
		for(int i = 0; i < original.getSampleRate(); i++) {
			int sample = original.getSample(i);
			//System.out.println("sample: "+sample);
			int newSampleVal = (int)(sample * scale);
			//System.out.println("newSampleVal: "+newSampleVal);
			result.setSample(i, newSampleVal);
		}
		
		System.out.println("newSampleVal: "+ result.getSample(result.getSampleRate()-1));
		System.out.println("oldSampleVal: "+ original.getSample(original.getSampleRate()-1));
		return result;
	}
	
	public void setVolume(double sc) {
		scale = sc;
		generate();
	}
		
	@Override
	public void connectInput(Source input){
		inputSource = input;
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
