package dissertationPackage;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.transform.Rotate;

public class LookDirectionPlot extends Application {
	
	private double[][] plotData;
	
	public LookDirectionPlot(double[][] data) {
		if (data.equals(null)) {
			new java.lang.NullPointerException("Data not loaded, please load a .csv file");
		}
		plotData = data;
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		// Generate display format and grid.
		mainStage.setTitle("Direction of look plot");
		mainStage.show();
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		// Generate bottom buttons
		HBox buttonBox = new HBox(50);
		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		
		Button previousButton = new Button("Previous Chart");
		Button nextButton = new Button("Next Chart");
		Button listButton = new Button("List of Charts");
		Button closeButton = new Button("Close");
		
		buttonBox.getChildren().add(previousButton);
		buttonBox.getChildren().add(listButton);
		buttonBox.getChildren().add(closeButton);
		buttonBox.getChildren().add(nextButton);
		
		// Create the viewcone and place it
		Image viewCone = new Image(getClass().getResourceAsStream("ViewCone.png"));
		ImageView iv1 = new ImageView(viewCone);
        iv1.setPreserveRatio(true);
        iv1.setSmooth(true);
        iv1.setCache(true);
        iv1.setFitWidth(400);
		
        // Pane to hold Image
	    Pane pane = new Pane(iv1);
		pane.setPrefWidth(400);
		pane.setPrefHeight(400);
		
		// Generate slider for time
		final Slider slider = new Slider(0, plotData.length - 1, 0);
	    slider.setTooltip(new Tooltip("Time Slider"));
	    slider.setPrefWidth(600);
	    slider.valueProperty().addListener(new ChangeListener<Number>() {
	        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
	        	iv1.setRotate(plotData[Math.round(newValue.intValue())][6]);
	        }
	    });
	    
	    // Add all created items to grid
	 	grid.add(pane, 0, 2);
	 	grid.add(slider, 0, 3);
	 	grid.add(buttonBox, 0, 4);
		
		
		Scene scene = new Scene(grid, 1000, 700);
		
		mainStage.setScene(scene);
		mainStage.show();
	}
}
