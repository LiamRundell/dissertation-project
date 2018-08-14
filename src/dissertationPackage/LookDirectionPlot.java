package dissertationPackage;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
		
		ObservableList<PieChart.Data> pieChartData =
			FXCollections.observableArrayList(
			new PieChart.Data("View", 80),
			new PieChart.Data("-", 240)
			);
		
		// Generate pie chart
		final PieChart lookPieChart = new PieChart(pieChartData);
		lookPieChart.setTitle("Look Direction Chart");
		lookPieChart.setMaxSize(500, 500);
		lookPieChart.setMinSize(500, 500);
		lookPieChart.setPrefSize(500, 500);
		lookPieChart.setLegendVisible(false);
		
		lookPieChart.setStartAngle(135);
		
		
		// Add all created items to grid
		grid.add(lookPieChart, 0, 2);
		grid.add(buttonBox, 0, 4);
		
		grid.setGridLinesVisible(true);
		
		Scene scene = new Scene(grid, 1000, 700);
		
		mainStage.setScene(scene);
		mainStage.show();
	}
}
