import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Cable {
	public Line line;
	private Circle outputCircle;
	private Circle inputCircle;
	public SourceWidget sourceElement;
	public inputableWidget receivingElement;
	
	public Cable (double lineStartX, double lineStartY, Pane canvas) {
		line = new Line();
		line.setStartX(lineStartX);
		line.setStartY(lineStartY);
		line.setEndX(lineStartX);
		line.setEndY(lineStartY);
		addToScene(canvas);
		
	}
	
	public void updateLineEnd(double lineEndX, double lineEndY ) {
		line.setEndX(lineEndX);
		line.setEndY(lineEndY);
	}
	
	public void updateLineStart(double lineStartX, double lineStartY ) {
		line.setStartX(lineStartX);
		line.setStartY(lineStartY);
	}

	public void setOutputCircle(Circle output) {
		outputCircle = output;
	}
	
	public void centerLineInCircle(Circle circleRef, boolean isLineStart) {
		Point2D circleCenter = circleRef.localToScene(new Point2D(circleRef.getCenterX(), circleRef.getCenterY()));
		if(isLineStart) {
			updateLineStart(circleCenter.getX(), circleCenter.getY());
		}else {
			updateLineEnd(circleCenter.getX(), circleCenter.getY());
		}
	}
	
	private void addToScene(Pane elem) {
		elem.getChildren().add(line);
	}
	
	public Circle getOutputCircle() {
		return outputCircle;
	}

	public void setInputCircle(Circle input) {
		inputCircle = input;
		
	}

	public Circle getInputCircle() {
		return inputCircle;
	}
}
