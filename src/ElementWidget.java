import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ElementWidget extends BorderPane {
	DecimalFormat myFormatter = new DecimalFormat("#.#");

	//components
	//private BorderPane widget;
	private VBox leftVBox;
	private VBox rightVBox;
	protected Label controlTitle;
	private GridPane centerGrid;
	protected Circle inputCircle;
	Circle outputCircle;
	Point2D mouseStartPos;
	private ArrayList<Cable> inputCables;
	private ArrayList<Cable> outputCables;
	boolean allowManyInputCables = true;
	boolean allowManyOutputCables = true;
	
	private void setTheBorder(Pane element) {	
		element.setBorder(new Border(new BorderStroke(
				Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
	}
	
	public ElementWidget(int width, int height, String eTitle){	
		setMaxWidth(width);
		setMaxHeight(height);
		controlTitle = new Label(eTitle);
		inputCables = new ArrayList<Cable>();
		outputCables = new ArrayList<Cable>();

		create();	
		
		this.setOnMousePressed( new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				mouseStartPos = new Point2D( e.getX(), e.getY());	
			}	
		});
		
		 this.setOnMouseDragged(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				//Point2D point = new Point2D(getTranslateX(), getTranslateY());
				double xoffset = getWidth()/2;
				double yoffset = getHeight()/2;
				Point2D mousePoint = new Point2D(e.getX(), e.getY());
				Bounds oCircleBoundsInScene = outputCircle.localToScene(outputCircle.getBoundsInLocal());

				Point2D mousePointInScene = localToScene(mouseStartPos);

				if(oCircleBoundsInScene.contains(mousePointInScene)){
					return;
				};
				
				Point2D mouseScenePoint = localToScene(mousePoint);			
				setTranslateX(mouseScenePoint.getX() - xoffset);
				setTranslateY(mouseScenePoint.getY()- yoffset);
				
				updateCablePosition();
				//if has cables, update cable positions
			}
        	
        });
	}
	
	protected void addComponentToGrid(Node Elem, int colIndex, int rowIndex, int colSpan, int rowSpan) {
		centerGrid.add(Elem, colIndex, rowIndex, colSpan, rowSpan);
	}
	
	private void create() {
		//Top
		
		//Right
		rightVBox = new VBox();
		setTheBorder(rightVBox);
		rightVBox.setPrefWidth(50);
		outputCircle = new Circle(15);
		outputCircle.setFill(Color.AQUAMARINE);
		rightVBox.setAlignment(Pos.CENTER);
		rightVBox.getChildren().add(outputCircle);
		setRight(rightVBox);	
		//Bottom
		
		
		//Left
		
		leftVBox = new VBox();
		setTheBorder(leftVBox);
		leftVBox.setPrefWidth(50);
		inputCircle = new Circle(15);
		inputCircle.setFill(Color.ROYALBLUE);
		leftVBox.getChildren().add(inputCircle);
		leftVBox.setAlignment(Pos.CENTER);
		setLeft(leftVBox);
		// Center
		
		centerGrid = new GridPane();
		setTheBorder(centerGrid);
		centerGrid.setVgap(5);
		
		addComponentToGrid(controlTitle, 1, 1, 3, 1);
		controlTitle.setAlignment(Pos.CENTER);//TODO: Fix positioning
		
		setCenter(centerGrid);

	}
	
	public void addInputCable(Cable c, Pane canvas) {
		if(this.allowManyInputCables || inputCables.size() < 1) {
			inputCables.add(c);
		}else {
			canvas.getChildren().remove(c.line);
		}
	}
	
	public void addOutputCable(Cable c, Pane canvas) {
		if(this.allowManyOutputCables || outputCables.size() < 1) {
			outputCables.add(c);
		}else {
			canvas.getChildren().remove(c.line);
		}	
	}
	
	public void updateAllCables(ArrayList<Cable> cables) {
		for(Cable c : cables) {
			if (c.sourceElement == this) {
				Point2D oCircleCtr = new Point2D(outputCircle.getCenterX(), outputCircle.getCenterY());
				oCircleCtr = outputCircle.localToScene(oCircleCtr);//screenCoordinates
				c.updateLineStart(oCircleCtr.getX(), oCircleCtr.getY());
			}else {
				Point2D iCircleCtr = new Point2D(inputCircle.getCenterX(), inputCircle.getCenterY());
				iCircleCtr = inputCircle.localToScene(iCircleCtr);//screenCoordinates
				c.updateLineEnd(iCircleCtr.getX(), iCircleCtr.getY());
			}
			
		}
	}
	
	public void updateCablePosition(){
		updateAllCables(inputCables);
		updateAllCables(outputCables);
	}
	
	protected void updateSound() {
		//slider is updated. 
		//Sound is already updated. Then call this method
		
		for(Cable c: outputCables) {
			try{
				if(c.receivingElement.getType() == "Speaker") return;
			}catch(NullPointerException e) {
				System.out.println("No sound element found");
				return;
			}
			
			Source soundObj = c.receivingElement.getSoundObj();
			//somehow regenerate the sound
			soundObj.regenerate();
			((ElementWidget) c.receivingElement).updateSound();
		}
		
	}


}
