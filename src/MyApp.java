//TODO: on bar adjustment, update the current sound

import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MyApp extends Application{
	
	ArrayList<ElementWidget> elementLibrary = new ArrayList<ElementWidget>();//Is there a better type then element list
	BorderPane canvas = new BorderPane();
	Group group = new Group();
	Button playBtn;
	Point2D mouseStartPos;
	boolean lineDrawable = false;
	Cable currentCable;
	Source currentSound;
	SpeakerWidget speaker;
	
	public void addToLibrary(ElementWidget element) {
		elementLibrary.add(element);
	}
	
	private void setTheBorder(Pane element) {	
		element.setBorder(new Border(new BorderStroke(
				Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Jeremy's Synthesizer");
		BorderPane borderPane = new BorderPane();
		setTheBorder(borderPane);
		Scene scene = new Scene(borderPane, 1200, 800);
		
		//Set top border
		
		//Set Right Border
		VBox rightSideBar = new VBox(10);
		Button addSWBtn = new Button("Add SineWave");	
		Button addFilterBtn = new Button("Add Volume Filter");
		Button addMixerBtn = new Button("Add Mixer");
		Button addWhiteNoiseBtn = new Button("Add WhiteNoise");
		
		addSWBtn.setMaxWidth(Double.MAX_VALUE);
		addFilterBtn.setMaxWidth(Double.MAX_VALUE);
		addMixerBtn.setMaxWidth(Double.MAX_VALUE);
		addWhiteNoiseBtn.setMaxWidth(Double.MAX_VALUE);
		
		rightSideBar.getChildren().add(addSWBtn);
		rightSideBar.getChildren().add(addFilterBtn);
		rightSideBar.getChildren().add(addMixerBtn);
		rightSideBar.getChildren().add(addWhiteNoiseBtn);
		
		borderPane.setRight(rightSideBar);
		
		addSWBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //create new element
            	SineWaveWidget sineWidget = new SineWaveWidget(300,100, "Sine Wave");
            	//add it to the library
            	addToLibrary(sineWidget);
            	//draw element to the screen
            	group.getChildren().add(sineWidget);       	
            }
        });
		
		//Add filter btn and listener;
		addFilterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //create new element
            	VolumeWidget volumeWidget = new VolumeWidget(300,100, "Volume Filter");
            	//add it to the library
            	addToLibrary(volumeWidget);
            	//draw element to the screen
            	group.getChildren().add(volumeWidget);       	
            }
        });
		
		//Add mixer btn and listener;
		addMixerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //create new element
            	MixerWidget mixerWidget = new  MixerWidget(300,100, "Mixer");
            	//add it to the library
            	addToLibrary(mixerWidget);
            	//draw element to the screen
            	group.getChildren().add(mixerWidget);       	
            }
        });
		
		addWhiteNoiseBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //create new element
            	WhiteNoiseWidget whiteNoiseWidget  = new WhiteNoiseWidget(300,100, "White Noise");
            	//add it to the library
            	addToLibrary(whiteNoiseWidget);
            	//draw element to the screen
            	group.getChildren().add(whiteNoiseWidget);       	
            }
        });
		
		//Set Bottom Border
		playBtn = new Button("Play");
		HBox bottomBar = new HBox();	
		bottomBar.getChildren().add(playBtn);
		borderPane.setBottom(bottomBar);
		playBtn.setDisable(true);
		//setTheBorder(bottomBar);
		//Set Left Border
		
		//Set Center 
		
		SineWaveWidget sineGui = new SineWaveWidget(300,100, "Sine Wave");
		
		addToLibrary(sineGui);
		
		for(ElementWidget elem : elementLibrary ) {
			group.getChildren().add(elem);
		}
		
		canvas.getChildren().add(group);
	    borderPane.setCenter(canvas);
	    
	   
	    //Speaker
	    speaker = new SpeakerWidget();
		canvas.setRight(speaker.getInputCircle());
		Insets insets = new Insets(15);
		canvas.setPadding(insets);
	    canvas.setOnMousePressed(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				//if mouse down w/in input circle, start line.
				
				mouseStartPos = new Point2D( e.getX(), e.getY());
				Point2D mouseStartInScene = canvas.localToScene(mouseStartPos);
				
				SourceWidget sourceWidget = getElement(mouseStartInScene);
				if(sourceWidget == null) {return;};
				
				Circle outputCircle = sourceWidget.getOutputCircle();
				Bounds oCircleBoundsInScene = outputCircle.localToScene(outputCircle.getBoundsInLocal());

				if(oCircleBoundsInScene.contains(mouseStartInScene)){
					Point2D oCircleCtr = new Point2D(outputCircle.getCenterX(), outputCircle.getCenterY());
					oCircleCtr = outputCircle.localToScene(oCircleCtr);
					lineDrawable = true;
					currentCable = new Cable(oCircleCtr.getX(),oCircleCtr.getY(), canvas);
					currentCable.setOutputCircle(outputCircle);
					currentCable.sourceElement = sourceWidget;
				};		
			}
			 
		 });
	    
		canvas.setOnMouseDragged(new EventHandler<MouseEvent> () {

			@Override
			public void handle(MouseEvent e) {
				//draw line to mouse

				if(lineDrawable) {
					Point2D mousePointInScene = canvas.localToScene(new Point2D(e.getX(), e.getY()));
					Point2D screenMouseCoor = canvas.localToScene(mousePointInScene);
					currentCable.updateLineEnd(screenMouseCoor.getX(), screenMouseCoor.getY());
				}		
			}	
		});
		
	    canvas.setOnMouseReleased(new EventHandler<MouseEvent> () {

			@Override
			public void handle(MouseEvent e) {
				
				if(lineDrawable) {
					//TODO: better name for mouseStartPos;
					Point2D mouseEndPos = new Point2D( e.getX(), e.getY());
					mouseStartPos = mouseEndPos;
					Point2D mouseStartInScene = canvas.localToScene(mouseStartPos);
					
					//return's source Widget
						SourceWidget endWidget = getElement(mouseStartInScene);
						 inputableWidget endingWidget;
					try {
						  endingWidget = (inputableWidget) endWidget;
					}catch(ClassCastException error) {
						if(currentCable != null) {
							canvas.getChildren().remove(currentCable.line);
						}
						return;
					}
					
					if(endingWidget == null) {
						
						canvas.getChildren().remove(currentCable.line);
		
					}else if(endingWidget.getType()== "Speaker") {
						
						connectSound(endingWidget.getType(), currentCable.sourceElement.getSoundObj());
						((ElementWidget) currentCable.sourceElement).addOutputCable(currentCable, canvas);
						
						currentCable.centerLineInCircle(speaker.getInputCircle(), false);
						speaker.addInputCable(currentCable, canvas);
						
					}else {
						
						Circle inputCircle = endingWidget.getInputCircle();
						System.out.println(currentCable.getInputCircle());
						if(currentCable.getInputCircle() != null) {
							canvas.getChildren().remove(currentCable.line);
							return;
						}
							
						currentCable.setInputCircle(inputCircle);
						
						currentCable.receivingElement = endingWidget;
						((ElementWidget) currentCable.receivingElement).addInputCable(currentCable, canvas);
						((ElementWidget) currentCable.sourceElement).addOutputCable(currentCable, canvas);
						
						//center the line in the circle
						currentCable.centerLineInCircle(inputCircle, false);
						
						Source source = currentCable.sourceElement.getSoundObj();
						String widgetType = currentCable.receivingElement.getType();
	
						//connectSound
						connectSound(widgetType, source);
						
					}			
					currentCable = null;
					lineDrawable = false;			
				}
			}
	    	
	    });
    
        playBtn.setOnAction(
    		new EventHandler<ActionEvent>(){
    			@Override
    			public void handle(ActionEvent e) {
    				try {
    					System.out.println("The current Sound is: " + currentSound);
						AudioPlayer newPlayer= new AudioPlayer(currentSound);
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
    			}
    		}    		
        );
     
        primaryStage.setScene(scene);
		primaryStage.show();
	
	}
	
	private SourceWidget getElement(Point2D mousePoint) {
		
		for( ElementWidget element : elementLibrary) {
			//change element coordinates to parent
			if(element.getBoundsInParent().contains(mousePoint)) {
				return (SourceWidget)element;
			}else if (speaker.getInputCircle().getBoundsInParent().contains(mousePoint)) {
				return speaker;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	public String findType(inputableWidget elem) {
		return elem.getType();
	}
	
	public void connectSound(String widgetType, Source source) {
		if(widgetType =="Mixer") {
			SoundMixer mixer = (SoundMixer) currentCable.receivingElement.getSoundObj();
			mixer.connectInput(source);
			currentSound = mixer;
			
		}else if (widgetType == "Volume") {
			Volume volume = (Volume) currentCable.receivingElement.getSoundObj();
			volume.connectInput(source);
			currentSound = volume;
		}else if (widgetType == "Speaker") {
			 playBtn.setDisable(false);
			 currentSound = source;
		}
	}

}
