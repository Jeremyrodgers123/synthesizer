import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SpeakerWidget implements inputableWidget, SourceWidget {
	private Circle circle;
	private String type;
	public boolean allowManyInputCables;
	public boolean allowManyOutputCables;
	private ArrayList<Cable> inputCables;
	public SpeakerWidget(){
		circle = new Circle(30);
		circle.setFill(Color.YELLOW);
		type = "Speaker";
		inputCables = new ArrayList<Cable>();
		allowManyInputCables = false;
		allowManyOutputCables = false;
	}

	public void addInputCable(Cable c, Pane canvas) {
		System.out.println(inputCables.size());
		if(allowManyInputCables || inputCables.size() < 1) {
			inputCables.add(c);
		}else {
			canvas.getChildren().remove(c.line);
		}
	}
	
	@Override
	public Source getSoundObj() {

		return null;
	}

	@Override
	public Circle getInputCircle() {
		return circle;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public Circle getOutputCircle() {
		// TODO Auto-generated method stub
		return null;
	}
}
