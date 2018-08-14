package dissertationPackage;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TravelSpeedPlot extends Application {
	
	private double[][] plotData;

	public TravelSpeedPlot(double[][] data) {
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
		xAxis.setLabel("Time");
		yAxis.setLabel("Speed");
		
		final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		lineChart.setTitle("Movement Speed Chart");
		lineChart.setMaxSize(800, 400);
		lineChart.setMinSize(800, 400);
		lineChart.setPrefSize(800, 400);
		
		//series holds the data for the plot
		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		series.setName("Movement Speed /TBC");
		
		//add data to the series for plotting
		for(int i = 1; i < plotData.length; i += 1) {
			series.getData().add(new XYChart.Data<Number, Number>(plotData[i][0], plotData[i][31]));
			System.out.println("Column " + i + " loaded.");
		}
		
		lineChart.getData().add(series);
		
		// Add all created items to grid
		grid.add(lineChart, 0, 2);
		grid.add(buttonBox, 0, 4);
		
		Scene scene = new Scene(grid, 1000, 700);
		
		mainStage.setScene(scene);
		mainStage.show();
	
	}

}
