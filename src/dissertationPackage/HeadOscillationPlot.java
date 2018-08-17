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

public class HeadOscillationPlot extends Application {
	
	private double[][] plotData;
	
	public HeadOscillationPlot(double[][] data) {
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
		
		//axes
		final NumberAxis xAxisx = new NumberAxis();
		final NumberAxis yAxisx= new NumberAxis();
		xAxisx.setLabel("Seconds");
		yAxisx.setLabel("No. of Oscillations");
		
		final NumberAxis xAxisy = new NumberAxis();
		final NumberAxis yAxisy= new NumberAxis();
		xAxisy.setLabel("Seconds");
		yAxisy.setLabel("No. of Oscillations");
		
		final NumberAxis xAxisz = new NumberAxis();
		final NumberAxis yAxisz= new NumberAxis();
		xAxisz.setLabel("Seconds");
		yAxisz.setLabel("No. of Oscillations");
		
		// Charts
		final LineChart<Number, Number> lineChartx = new LineChart<Number, Number>(xAxisx, yAxisx);
		lineChartx.setTitle("Up-Down Oscillations Chart (Pitch)");
		lineChartx.setMaxSize(800, 200);
		lineChartx.setMinSize(800, 200);
		lineChartx.setPrefSize(800, 200);
		
		final LineChart<Number, Number> lineCharty = new LineChart<Number, Number>(xAxisy, yAxisy);
		lineCharty.setTitle("Left-Right Oscillations Chart (Bearing)");
		lineCharty.setMaxSize(800, 200);
		lineCharty.setMinSize(800, 200);
		lineCharty.setPrefSize(800, 200);
		
		final LineChart<Number, Number> lineChartz = new LineChart<Number, Number>(xAxisz, yAxisz);
		lineChartz.setTitle("Side-Side Oscillations Chart (Yaw)");
		lineChartz.setMaxSize(800, 200);
		lineChartz.setMinSize(800, 200);
		lineChartz.setPrefSize(800, 200);
		
		//series hold the data for the plots
		XYChart.Series<Number, Number> seriesx = new XYChart.Series<Number, Number>();
		seriesx.setName("Up-Down Oscillations /Second");
		XYChart.Series<Number, Number> seriesy = new XYChart.Series<Number, Number>();
		seriesx.setName("Left-Right Oscillations /Second");
		XYChart.Series<Number, Number> seriesz = new XYChart.Series<Number, Number>();
		seriesx.setName("Side-Side Oscillations /Second");
		
		double[][] oscil = calculateOscillations();
		
		//add data to the series for plotting
		for(int i = 1; i < oscil[0].length; i += 1) {
			seriesx.getData().add(new XYChart.Data<Number, Number>(i, oscil[0][i]));
			seriesy.getData().add(new XYChart.Data<Number, Number>(i, oscil[1][i]));
			seriesz.getData().add(new XYChart.Data<Number, Number>(i, oscil[2][i]));
		}
		
		lineChartx.getData().add(seriesx);
		lineCharty.getData().add(seriesy);
		lineChartz.getData().add(seriesz);
		
		// Add all created items to grid
		grid.add(lineChartx, 0, 2);
		grid.add(lineCharty, 0, 3);
		grid.add(lineChartz, 0, 4);
		grid.add(buttonBox, 0, 5);
		
		Scene scene = new Scene(grid, 1000, 750);
		
		mainStage.setScene(scene);
		mainStage.show();
		
	}

	
	private double[][] calculateOscillations() {
		double[][] oscil = new double[3][(plotData.length / 20) + 1];
		double xRot = plotData[0][5];
		double xRotPrev = plotData[0][5];
		double yRot = plotData[0][6];
		double yRotPrev = plotData[0][6];
		double zRot = plotData[0][7];
		double zRotPrev = plotData[0][7];
		
		for (int i = 0; i < plotData.length / 20; i++) {
			int countx = 0;
			int county = 0;
			int countz = 0;
			
			for (int k = 0; k < 20; k++) {
				// xrotation
				if (xRotPrev < xRot && xRot > plotData[(i*20) + k][5]) {
					countx++;
				}
				else if (xRotPrev > xRot && xRot < plotData[(i*20) + k][5]) {
					countx++;
				}
				xRotPrev = xRot;
				xRot = plotData[(i*20) + k][5];
				
				// yrotation
				if (yRotPrev < yRot && yRot > plotData[(i*20) + k][6]) {
					county++;
				}
				else if (xRotPrev > xRot && xRot < plotData[(i*20) + k][6]) {
					county++;
				}
				yRotPrev = yRot;
				yRot = plotData[(i*20) + k][6];
				
				// zrotation
				if (zRotPrev < zRot && zRot > plotData[(i*20) + k][7]) {
					countz++;
				}
				else if (zRotPrev > zRot && zRot < plotData[(i*20) + k][7]) {
					countz++;
				}
				zRotPrev = zRot;
				zRot = plotData[(i*20) + k][7];
			}
			oscil[0][i] = countx;
			oscil[1][i] = county;
			oscil[2][i] = countz;
		}
		return oscil;
	}
}
