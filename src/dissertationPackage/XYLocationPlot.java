package dissertationPackage;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class XYLocationPlot extends Application {
	
	private double[][] plotData = new double[][] {{1.0, 2.0, 3.0, 4.0, 4.5, 5.6}, {1.0, 2.0, 3.0, 4.0, 4.5, 5.6}, {1.5, 2.2, 3.9, 4.3, 4.5, 5.7}};
	
	public XYLocationPlot(double[][] data) {
		// plotData = data;
	}

	@Override
	public void start(Stage xyStage) throws Exception {
		xyStage.setTitle("XYGraph Test");
		xyStage.show();
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		//axes
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("X Axis");
		yAxis.setLabel("Y Axis");
		
		final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		lineChart.setTitle("XY Location Chart");
		
		//series holds the data for the plot
		XYChart.Series series = new XYChart.Series<>();
		series.setName("data");
		
		//add data to the series for plotting
		for(int i = 1; i < plotData[0].length; i++) {
			series.getData().add(new XYChart.Data(plotData[1][i], plotData[2][i]));
			System.out.println(plotData[1][i]);
		}
		
		grid.add(lineChart, 0, 0);
		
		grid.setGridLinesVisible(true);
		
		Scene scene = new Scene(grid, 1000, 700);
		
		xyStage.setScene(scene);
		xyStage.show();
	}
}
