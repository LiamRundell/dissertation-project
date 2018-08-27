package dissertationPackage;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import com.opencsv.CSVReader;

public class DataImporter {
	
	private String csvPath = null;
	private HashMap<String, Integer> centreObjectMap = new HashMap<String, Integer>();	
	
	/**
	 * DataImporter Class, used to read the CSV files output from the HMD headset using the read() command.
	 */
	public DataImporter() {
		//TODO allow .csv path to be passed as argument.
		csvPath = "F:\\Dropbox\\Dissertation\\Data\\30520171458_cameralog.csv";
	}
	
	/**
	 * Getter to return the HashMap containing the objects seen in the map.
	 */
	public HashMap<String, Integer> getCentreObjectMap() {
		return centreObjectMap;
	}
	
	/**
	 * Method which returns the all data from the specified .csv file. Data is returned as a double matrix.
	 * 
	 * @return Double matrix of data extracted from .csv
	 * @throws IOException
	 */
	public double[][] read(int type) throws IOException {
		// Create reader and csv reader, they are instantiated in try block. Created here for 'finally' access.
		Reader reader = null;
		CSVReader csvReader = null;
		
		// 2D Arraylist for double-expandable matrix for reading into.
		ArrayList<ArrayList<String>> theData = new ArrayList<ArrayList<String>>();
		
		// Instantiate read using passed .csv location
		try {
			reader = Files.newBufferedReader(Paths.get(csvPath));
			csvReader = new CSVReader(reader);
			String[] record;
			
			// Ints for tracking progress through data (col) and map value of objects (mapInt)
			int col = 0;
			int mapVal = 0;
			
			// Cycle through data and read into ArrayList. Break if null
			// !Weird nullPointerException if I try to iterate using readnext() is null. Hence if statement to break. 
			while (true) {
				theData.add(new ArrayList<String>());
				record = csvReader.readNext();
				
				if(record == null) {
				break;
				}
				else {
					// Iterate through column list, add each to theData at same index.
					for (int i = 0; i < record.length; i++) {
						theData.get(col).add(record[i]);
					}
				}
				// Check object in centre view against map and add if not present
				if (!centreObjectMap.containsKey(record[12])) {
					centreObjectMap.put(record[12], mapVal++);
				}
				col++;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			reader.close();
			csvReader.close();
		}
		System.out.println(centreObjectMap);
		
		// Convert ArrayList to double array using fixData() and return.
		double[][] arrayData = fixData(theData);
		return arrayData;
	}
	
	/**
	 * Private method to convert matrix array list of strings into double array.
	 * 
	 * @param data - Arraylist from read()
	 * @return 2D double array
	 */
	private double[][] fixData(ArrayList<ArrayList<String>> data) {
		double[][] returnArray = new double[data.size()][data.get(0).size()];
		
		for (int i = 0; i < data.size(); i++) {
			for (int k = 0; k < data.get(i).size(); k++) {
				
				if (k == 12) {
					returnArray[i][k] = centreObjectMap.get(data.get(i).get(k));
				}
				else if (isStringNumeric(data.get(i).get(k))) {
					returnArray[i][k] = Double.parseDouble(data.get(i).get(k));
				}
				else {
					//System.out.println("Not a number hit.");
					//System.out.println("Row: " + i + " - Col: " + k);
					returnArray[i][k] = 0.0;
				}
			}
		}
		return returnArray;
	}
	
	/**
	 * Basic check if number on String, returns true if number, can be negative or double. Regex.
	 * Form considered correct: (-)##(.##)
	 * 
	 * @param check String to check
	 * @return boolean - true if number
	 */
	public static boolean isStringNumeric (String check) {
		return check.matches("-?[0-9]+(\\.[0-9]*)?");
	}
}