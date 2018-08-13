package dissertationPackage;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class XYLocationPlot extends Application {
	
	private double[][] plotData;
	// = new double[][] {{1.0, 2.0, 3.0, 4.0, 4.5, 5.6}, {1.0, 2.0, 3.0, 4.0, 4.5, 5.6}, {1.5, 2.2, 3.9, 4.3, 4.5, 5.7}};
	
	public XYLocationPlot(double[][] data) {
		if (data.equals(null)) {
			new java.lang.NullPointerException("Data not loaded, please load a .csv file");
		}
		plotData = data;
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		// Generate display format and grid.
		mainStage.setTitle("XYGraph Test");
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
		
		//axes
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setAutoRanging(false);
		xAxis.setLabel("X Axis");
		xAxis.setLowerBound(findLowerBound(plotData[3]) - 30);
		xAxis.setUpperBound(findUpperBound(plotData[3]) + 10);
		yAxis.setAutoRanging(false);
		yAxis.setLabel("Y Axis");
		yAxis.setLowerBound(findLowerBound(plotData[5]) - 10);
		yAxis.setUpperBound(findUpperBound(plotData[5]) + 10);
		
		
		final ScatterChart<Number, Number> scatterChart = new ScatterChart<Number, Number>(xAxis, yAxis);
		scatterChart.setTitle("XY Location Chart");
		scatterChart.setMaxSize(600, 600);
		scatterChart.setMinSize(600, 600);
		scatterChart.setPrefSize(600, 600);
		
		//series holds the data for the plot
		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		series.setName("Location - Bird's eye view");
		
		//add data to the series for plotting
		for(int i = 1; i < plotData.length; i += 1) {
			series.getData().add(new XYChart.Data<Number, Number>(plotData[i][2], plotData[i][4]));
			System.out.println("Column " + i + " loaded.");
		}
		
		scatterChart.getData().add(series);
		
		// Add all created items to grid
		grid.add(scatterChart, 0, 2);
		grid.add(buttonBox, 0, 4);
		
		grid.setGridLinesVisible(true);
		
		Scene scene = new Scene(grid, 1000, 700);
		
		mainStage.setScene(scene);
		mainStage.show();
	}
	
	private double findLowerBound(double[] arr) {
		double lower = arr[0];
		for (double i : arr) {
			if (lower > i) {
				lower = i;
			}
		}
		return lower;
	}
	
	private double findUpperBound(double[] arr) {
		double upper = arr[0];
		for (double i : arr) {
			if (upper < i) {
				upper = i;
			}
		}
		return upper;
	}
}
