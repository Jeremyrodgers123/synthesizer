
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.shape.Circle;

public class VolumeWidget extends FilterWidget implements inputableWidget, SourceWidget{
	
	
	private Volume volumeFilter;
	private Label volumeValLabel;
	private Slider volumeSlider = new Slider(0,1,1);
	private String type;
	
	public VolumeWidget(int width, int height, String eTitle) {
		super(width, height, eTitle);
		volumeFilter = new Volume(1);
		type = "Volume";
		allowManyOutputCables = false;
		allowManyInputCables = false;
		Label sliderTitle = new Label("Volume");
		volumeValLabel = new Label(Double.toString(1));
		
		addComponentToGrid(volumeValLabel, 1, 3, 3, 1);
		addComponentToGrid(sliderTitle, 1, 2, 3, 1);
		addComponentToGrid(volumeSlider, 1, 4, 3, 1);
		
		
		volumeSlider.valueProperty().addListener((observable, oldValue, newValue)->{
			double newVal = (double)newValue;
			if(volumeFilter.inputSource != null) {
				volumeFilter.setVolume(newVal);
				updateSound();
				String labelVal = myFormatter.format(newVal);
				volumeValLabel.setText(labelVal);
			}else {
				System.out.println("Connect an input first");
			}
		});
	}
		@Override
		public Source getSoundObj() {
			return volumeFilter;
		}
		@Override
		public Circle getOutputCircle() {
			return outputCircle;
		}
		@Override
		public Circle getInputCircle() {
			return inputCircle;
		}
		@Override
		public String getType() {
			return type;
		}


	
}
