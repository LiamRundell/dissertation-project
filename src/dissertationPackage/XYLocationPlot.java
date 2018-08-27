package dissertationPackage;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
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
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(findLowerBound(plotData, 4) - 10);
		yAxis.setUpperBound(findUpperBound(plotData, 4) + 10);
		xAxis.setAutoRanging(false);
		xAxis.setLowerBound(findLowerBound(plotData, 2) - 10);
		xAxis.setUpperBound(findUpperBound(plotData, 2) + 10);
		yAxis.setTickLabelsVisible(false);
		xAxis.setTickLabelsVisible(false);
		
		
		final ScatterChart<Number, Number> scatterChart = new ScatterChart<Number, Number>(xAxis, yAxis);
		scatterChart.setId("xyloc");
		scatterChart.setTitle("XY Location Chart");
		scatterChart.setMaxSize(600, 600);
		scatterChart.setMinSize(600, 600);
		scatterChart.setPrefSize(600, 600);
		
		//series holds the data for the plot
		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		series.setName("Location - Bird's eye view");
		
		//add data to the series for plotting, calculate average per half-second (10 rows) for speed
		for(int i = 0; i < plotData.length / 10; i++) {
			double totalx = 0;
			int countx= 0;
			double totaly = 0;
			int county= 0;
			
			for(int k = 0; k < 10; k++) {
				try {
					totalx += plotData[(i * 10) + k][2];
					totaly += plotData[(i * 10) + k][4];
					countx++;
					county++;
				}
				catch (ArrayIndexOutOfBoundsException e1) {
					break;
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// Use totals and counts to get average and add to series
			series.getData().add(new XYChart.Data<Number, Number>((totalx / countx), (totaly / county)));
		}
		
		scatterChart.getData().add(series);
		
		// Add all created items to grid
		grid.add(scatterChart, 0, 2);
		grid.add(buttonBox, 0, 4);
		
		// grid.setGridLinesVisible(true);
		
		Scene scene = new Scene(grid, 1000, 700);
		
		mainStage.setScene(scene);
		scene.getStylesheets().add("XYChartCSS.css");
		mainStage.show();
	}
	
	public static double findLowerBound(double[][] arr, int col) {
		double lower = arr[0][0];
		for (double[] i : arr) {
			if (lower > i[col]) {
				lower = i[col];
			}
		}
		//System.out.println("lower: " + lower);
		return lower;
	}
	
	public static double findUpperBound(double[][] arr, int col) {
		double upper = arr[0][0];
		for (double[] i : arr) {
			if (upper < i[col]) {
				upper = i[col];
			}
		}
		//System.out.println("upper: " + upper);
		return upper;
	}
}
