package dissertationPackage;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
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
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

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
		// TODO Place on grid with onclick labels
		// TODO Onioning?
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
		
		// Axes
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(XYLocationPlot.findLowerBound(plotData, 4) - 10);
		yAxis.setUpperBound(XYLocationPlot.findUpperBound(plotData, 4) + 10);
		xAxis.setAutoRanging(false);
		xAxis.setLowerBound(XYLocationPlot.findLowerBound(plotData, 2) - 10);
		xAxis.setUpperBound(XYLocationPlot.findUpperBound(plotData, 2) + 10);
		yAxis.setTickLabelsVisible(false);
		xAxis.setTickLabelsVisible(false);
		
		// Generate chart and set size
		final ScatterChart<Number, Number> scatterChart = new ScatterChart<Number, Number>(xAxis, yAxis);
		scatterChart.setId("viewDirect");
		scatterChart.setTitle("View Direction Chart");
		scatterChart.setMaxSize(500, 500);
		scatterChart.setMinSize(500, 500);
		scatterChart.setPrefSize(500, 500);
		
		// Series holds the data for the plot
		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		series.setName("Location");
		
		// Add data to the series for plotting, calculate average per half-second (10 rows) for speed
		ArrayList<ArrayList<Double>> plotList = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < 4; i++) {
			plotList.add(new ArrayList<Double>());
		}
		
		// Average most of the data (but time) for speed of processing
		for(int i = 0; i < plotData.length / 10; i++) {
			double totalx = 0;
			int countx= 0;
			double totaly = 0;
			int county= 0;
			double totalz = 0;
			int countz= 0;
			
			for(int k = 0; k < 10; k++) {
				try {
					// x coord
					totalx += plotData[(i * 10) + k][2];
					// y coord
					totaly += plotData[(i * 10) + k][4];
					// view direction
					totalz += plotData[(i * 10) + k][6];
					countx++;
					county++;
					countz++;
				}
				catch (ArrayIndexOutOfBoundsException e1) {
					break;
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		// Use totals and counts to get average and add to series
		plotList.get(0).add(plotData[i * 10][0]);
		plotList.get(1).add(totalx / countx);
		plotList.get(2).add(totaly / county);
		plotList.get(3).add(totalz / countz);
		}
		
		// Add series to Chart
		scatterChart.getData().add(series);
		
		// Generate slider for time
		final Slider slider = new Slider(0, plotList.get(0).size() - 1, 0);
	    slider.setTooltip(new Tooltip("Time Slider"));
	    slider.setPrefWidth(600);
	    slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
	    
	    // Action on slider movement
	    slider.valueProperty().addListener(new ChangeListener<Number>() {
	        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
	        	// Clear the previous item in the series
	        	series.getData().clear();
	        	
	        	// Datapoint holds location in xy, fetched from plotList calculated above
	        	XYChart.Data<Number, Number> dataPoint = 
	        			new XYChart.Data<Number, Number>(plotList.get(1).get(Math.round(newValue.intValue())),
	        					plotList.get(2).get(Math.round(newValue.intValue())));
	        	
	        	// Generate arc for view-direction representation, rotated to face North on generation, then rotated using plotList data
	        	Arc arc = new Arc(0, 0, 70, 70, 50, 80);
	        	arc.getTransforms().add(Transform.rotate(plotList.get(3).get(Math.round(newValue.intValue())), 0, 0));
	        	arc.setType(ArcType.ROUND);
	        	dataPoint.setNode(arc);
	        	series.getData().add(dataPoint);
	        }
	    });
	    
	    // Add all created items to grid
	    grid.add(scatterChart, 0, 2);
	 	grid.add(slider, 0, 3);
	 	grid.add(buttonBox, 0, 4);
		
		
		Scene scene = new Scene(grid, 1000, 700);
		scene.getStylesheets().add("XYChartCSS.css");
		
		mainStage.setScene(scene);
		mainStage.show();
	}
}
