import javafx.scene.shape.Circle;

public class MixerWidget extends ElementWidget implements inputableWidget, SourceWidget{
	private SoundMixer mixer;
	public String type;
	public MixerWidget(int width, int height, String eTitle) {
		super(width, height, eTitle);
		mixer = new SoundMixer();
		type = "Mixer";
		allowManyOutputCables = false;
	}

	@Override
	public Circle getInputCircle() {
		return inputCircle;
	}

	@Override
	public Source getSoundObj() {
		// TODO Auto-generated method stub
		return mixer;
	}
	@Override
	public Circle getOutputCircle() {
		return outputCircle;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

}
