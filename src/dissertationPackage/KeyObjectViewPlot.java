/**
 * 
 */
package dissertationPackage;

import java.util.HashMap;
import java.util.Map.Entry;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 *
 */
public class KeyObjectViewPlot extends Application {
	
	private double[][] plotData;
	private HashMap<String, Integer> keyObjectMap; //TODO Arrange for on-the-fly changing of key object with dropdown
	private double target;
	private String targetStr = "";
	
	public KeyObjectViewPlot (double[][] data, HashMap<String, Integer> map, double argTarget) {
		if (data.equals(null)) {
			new java.lang.NullPointerException("Data not loaded, please load a .csv file");
		}
		if (map.equals(null)) {
			new java.lang.NullPointerException("No object map, unable to load Object View Plot");
		}
		plotData = data;
		keyObjectMap = map;
		target = argTarget;
		targetStr = getKey();
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		// Generate display format and grid.
		mainStage.setTitle("Key Object in View Plot");
		mainStage.show();
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		// Introductory Text
		Text introText = new Text();
		introText.setText("Current Key Object: " + targetStr);
		
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
		final CategoryAxis yAxis = new CategoryAxis();
		xAxis.setLabel("Time");
		yAxis.setLabel("In View?");
		
		final LineChart<Number, String> koChart = new LineChart<Number, String>(xAxis, yAxis);
		koChart.setTitle("Key Object in view Tracker in half-second?");
		koChart.setMaxSize(800, 400);
		koChart.setMinSize(800, 400);
		koChart.setPrefSize(800, 400);
		
		//series holds the data for the plot
		Series<Number, String> series = new XYChart.Series<>();
		series.setName("Target object in centre of view?");
		
		//add data to the series for plotting
		for(int i = 0; i < plotData.length / 10; i++) {
			String inView = "False";
			
			for (int k = 0; k < 10; k++) {
				try {
					if (plotData[(i * 10) + k][12] == target) {
						inView = "True";
					}
				}
				catch (ArrayIndexOutOfBoundsException e) {
					break;
				}
			}
			series.getData().add(new Data<Number, String>(plotData[i * 10][0], inView));
		}
		
		koChart.getData().add(series);
		
		// Add all created items to grid
		grid.add(introText, 0, 1);
		grid.add(koChart, 0, 2);
		grid.add(buttonBox, 0, 4);
		
		// grid.setGridLinesVisible(true);
		
		Scene scene = new Scene(grid, 1000, 700);
		
		mainStage.setScene(scene);
		mainStage.show();
	}
	
	private String getKey() {
		for (Entry<String, Integer> entry : keyObjectMap.entrySet()) {
			if (entry.getValue() == target) {
				return (entry.getKey());
			}
		}
		return ("Could not find key object");
	}
}
