package dissertationPackage;

import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataImporter {
	
	private String csvPath = null;
	
	
	/**
	 * DataImporter Class, used to read the CSV files output from the HMD headset using the read() command.
	 */
	public DataImporter() {
		//TODO allow .csv path to be passed as argument.
		csvPath = "F:\\Dropbox\\Dissertation\\Data\\258201794712_cameralog.csv";
	}
	
	/**
	 * Method which returns the relevant data from the specified .csv file. Data is returned as a double matrix.
	 * Almost all data are returned with 'time' as first column.
	 * 
	 * @param type, int specifying type of data required as follows:
	 * 		0 - xy Location Graph
	 * @return Double matrix of data extracted from .csv
	 * @throws IOException
	 */
	public double[][] read(int type) throws IOException {
		// Create reader and csv reader, they are instantiated in try block. Created here for 'finally' access.
		Reader reader = null;
		CSVReader csvReader = null;
		
		// 2D Arraylist for double-expandable matrix for reading into.
		ArrayList<ArrayList<String>> theData = new ArrayList<ArrayList<String>>();
		
		// Specify data required based off type, number of columns and hard-coded column number.
		int colNum = 0;
		ArrayList<Integer> colList = new ArrayList<Integer>();
		colList.add(0);
		
		if (type == 0) {
			colNum = 3;
			colList.add(2);
			colList.add(4);
		}
		else {
			System.out.println("Invalid report type");
		}
		
		for (int i = 0; i < colNum; i++) {
			theData.add(new ArrayList<String>());
		}
		
		// Instantiate read using passed .csv location
		try {
			reader = Files.newBufferedReader(Paths.get(csvPath));
			csvReader = new CSVReader(reader);
			String[] record;
			
			// Cycle through data and read into ArrayList. Break if null
			// !Weird nullPointerException if I try to iterate using readnext() is null. Hence if statement. 
			//while (true) {
			for (int k = 0; k<100; k++) {
				record = csvReader.readNext();
				
				if(record == null) {
					break;
				}
				else {
					// Iterate through column list, add each to theData at same index.
					for (int i = 0; i < colNum; i++) {
						theData.get(i).add(record[colList.get(i)]);
					}
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			reader.close();
			csvReader.close();
		}
		
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
				if (isStringNumeric(data.get(i).get(k))) {
					returnArray[i][k] = Double.parseDouble(data.get(i).get(k));
				}
				else {
					System.out.println("Not a number hit.");
					System.out.println("Col: " + i + " - Row: " + k);
					returnArray[i][k] = 0.0;
				}
			}
		}
		return returnArray;
	}
	
	/**
	 * Basic check if number on String, returns true if number, can be negative or double. Regex.
	 * 
	 * @param check String to check
	 * @return boolean - true if number
	 */
	public static boolean isStringNumeric (String check) {
		return check.matches("-?[0-9]+(\\.[0-9]*)?");
	}
}
