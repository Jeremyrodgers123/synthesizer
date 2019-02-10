import javax.sound.sampled.LineUnavailableException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.shape.Circle;

public class SineWaveWidget extends ElementWidget implements SourceWidget {
	public AdjustableSource SineWave;
	private Slider frequencySlider = new Slider(200,3300,440);
	private Label sliderVal = new Label(Integer.toString(10));
	
	public SineWaveWidget(int width, int height, String eTitle) {
		super(width, height, eTitle);
		SineWave = new SineWave(440);
		allowManyInputCables = false;
		Label sliderTitle = new Label("Frequency");
		sliderVal = new Label(Integer.toString(440));
		addComponentToGrid(sliderVal, 1, 3, 3, 1);
		addComponentToGrid(sliderTitle, 1, 2, 3, 1);
		addComponentToGrid(frequencySlider, 1, 4, 3, 1);
		
		Button playBtn = new Button("Play Sound");
		addComponentToGrid(playBtn, 1, 5, 3, 1);
		playBtn.setOnAction(
			new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent e) {
					try {
						AudioPlayer newPlayer= new AudioPlayer(SineWave);
					} catch (LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
		});
		
		frequencySlider.valueProperty().addListener((observable, oldValue, newValue)->{
			double newVal = (double)newValue;
			SineWave.setFrequency(newVal);
			updateSound();
			String labelVal = myFormatter.format(newVal);
			sliderVal.setText(labelVal);
			//SineWave.updateFrequency();
		});
	
	}
	
	public void setFrequency(double newFreq) {
		SineWave.setFrequency(newFreq);
	}

	@Override
	public Source getSoundObj() {
		// TODO Auto-generated method stub
		return SineWave;
	}
	@Override
	public Circle getOutputCircle() {
		return outputCircle;
	}



}
