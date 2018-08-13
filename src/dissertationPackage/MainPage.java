package dissertationPackage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainPage extends Application {
	
	private double[][] plotData;
	private DataImporter dataImporter;
	private double keyObject = 0.0;
	
	@Override
	public void start(Stage stage) {
		
		stage.setTitle("Main Page");
		stage.show();
		
		Text sceneTitle = new Text("Welcome to the main page, there should eventually be options here I hope.");
		
		// XYLocation Chart
		Button xyLocButton = new Button("XY Location Graph");
		xyLocButton.setDisable(true);
		xyLocButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {
				try {
					XYLocationPlot xyPlot = new XYLocationPlot(plotData);
					xyPlot.start(stage);
				} 
				catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		// Drop-down for key-object choice
		ComboBox<String> keyObjectCombo = new ComboBox<String>();
		keyObjectCombo.setDisable(true);
		
		// Key object view chart
		Button koViewButton = new Button("Key Object View Graph");
		koViewButton.setDisable(true);
		koViewButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {
				// Check an item is selected
				if (keyObjectCombo.getValue().equals(null)) {
					System.out.println("Please pick a key item");
					return;
				}
				
				try {
					HashMap<String, Integer> centreObjectMap = dataImporter.getCentreObjectMap();
					keyObject = centreObjectMap.get(keyObjectCombo.getValue());
					KeyObjectViewPlot keyViewPlot = 
							new KeyObjectViewPlot(plotData, centreObjectMap, keyObject);
					keyViewPlot.start(stage);
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		// Travel Speed plot
		Button speedTimeButton = new Button("Travel Speed Plot");
		speedTimeButton.setDisable(true);
		speedTimeButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {
				try {
					TravelSpeedPlot speedPlot = new TravelSpeedPlot(plotData);
					speedPlot.start(stage);
				} 
				catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		// Travel Speed plot
		Button lookDirButton = new Button("Look Direction Button");
		lookDirButton.setDisable(true);
		lookDirButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {
				try {
					LookDirectionPlot speedPlot = new LookDirectionPlot(plotData);
					speedPlot.start(stage);
				} 
				catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		// Button for loading .csv file, instantiates the DataImporter, should activate other buttons once processed.
		Button loadButton = new Button("Load Data");
		loadButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {
				try {
					dataImporter = new DataImporter();
					plotData = dataImporter.read(0);
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					// Enable plot choice buttons
					xyLocButton.setDisable(false);
					koViewButton.setDisable(false);
					speedTimeButton.setDisable(false);
					lookDirButton.setDisable(false);
					
					// Populate and enable key object choice now dataImporter exists
					// generateObjectList() defined for neatness
					ObservableList<String> keyObjects = FXCollections.observableArrayList(generateObjectList());
					keyObjectCombo.setItems(keyObjects);
					keyObjectCombo.setDisable(false);
				}
			}
		});
		
		// Establish grid for formatting and add all elements to it.
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		grid.add(sceneTitle, 0, 0, 2, 1);
		grid.add(loadButton, 0, 1);
		grid.add(xyLocButton, 0, 2);
		grid.add(koViewButton, 0, 3);
		grid.add(keyObjectCombo, 1, 3);
		grid.add(speedTimeButton, 0, 4);
		grid.add(lookDirButton, 0, 5);
		
		// Finalise
		Scene scene = new Scene(grid, 700, 500);
		stage.setScene(scene);
	}

	
	private String[] generateObjectList() {
		HashMap<String, Integer> map;
		String[] keyList = {"Data not yet loaded"};
		
		try {
			map = dataImporter.getCentreObjectMap();
			keyList = new String[map.size()];
			int index = 0;
			
			for (String key : map.keySet()) {
				keyList[index] = key;
				index++;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return keyList;
	}
}

