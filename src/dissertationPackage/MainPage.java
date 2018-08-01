package dissertationPackage;

import java.io.IOException;
import java.util.Arrays;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainPage extends Application {
	
	private double[][] plotData;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("Main Page");
		stage.show();
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Text sceneTitle = new Text("Welcome to the main page, there should eventually be options here I hope.");
		grid.add(sceneTitle, 0, 0, 2, 1);
		
		Button loadButton = new Button("Load Data");
		grid.add(loadButton, 0, 1);
		loadButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {
				try {
					DataImporter dataImporter = new DataImporter();
					plotData = dataImporter.read(0);
					System.out.println(Arrays.toString(plotData[0]));
					System.out.flush();
					System.out.println(Arrays.toString(plotData[1]));
					System.out.flush();
					System.out.println(Arrays.toString(plotData[2]));
					System.out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		Button testButton = new Button("XY Location Graph");
		grid.add(testButton, 0, 2);
		testButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {
				XYLocationPlot xyPlot = new XYLocationPlot(plotData);
				try {
					xyPlot.start(stage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		Scene scene = new Scene(grid, 600, 300);
		stage.setScene(scene);
	}
	
	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
