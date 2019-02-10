import javafx.scene.shape.Circle;

public class WhiteNoiseWidget extends ElementWidget implements SourceWidget {

	WhiteNoise whiteNoise;
	public WhiteNoiseWidget(int width, int height, String eTitle) {
		super(width, height, eTitle);
		whiteNoise = new WhiteNoise();
		controlTitle.setPrefWidth(100);
	}

	@Override
	public Circle getOutputCircle() {
		return outputCircle;
	}

	@Override
	public Source getSoundObj() {
		return whiteNoise;
	}

}
