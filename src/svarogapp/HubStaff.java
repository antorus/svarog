/* The goal of this class is to process a Hubstaff Weekly Timetable CSV file, identify the
 * productive tasks tracked and return the total amount of hours as a rounded decimal 
 * for further processing.
 */

package svarogapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HubStaff {
	
	//Utility variables for splitting files.
	public static String csvFile;
	private static String line = "";
	private static String csvSplitBy = ",";
	
	//Constant array of Hubstaff productive tasks in order to separate them from the unproductive ones
	//in the CSV file.
	//Specific values have been replaced with generics.
	private final static String[] RAW_TASKS = {"Billing", "Game Play", "Security"};
	
	//Final value of the productive hours worked.
	public static double totalHours = 0;					

	public static void main(String[] args) {	
		
		List<String> productiveTasks = new ArrayList<String>();
		
		//Array elements added to list for easier processing.
		for(String t : RAW_TASKS) {
			productiveTasks.add(t);
		}
		
		double tempTotalHours = 0;
		
		//Read the file input from the JAVAFX GUI in Controller class.
	    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	    	
	    	List<Double> splitTime = new ArrayList<Double>();

	    	//Skip header line.
	    	@SuppressWarnings("unused")
			String headerLine = br.readLine();
	    	
	        while ((line = br.readLine()) != null) {
	        	
	            String[] splitCsv = line.split(csvSplitBy);
	            
	            //Check for productive tasks in the respective column, separate the amount into
	            //hours and minutes, and parse them as a total hours decimal.
	            if(productiveTasks.contains(splitCsv[3])) {
	            	String[] a = splitCsv[12].split(":");
	            	double d = Double.parseDouble(a[0]) + Double.parseDouble(a[1]) / 60;
	            	splitTime.add(d);
	            }
	            
	        }
	        
	        //Add list elements between themselves.
    	    for(double d : splitTime) {
    	    	tempTotalHours+=d;
    	    }
    	    
    	    //Round productive hours result to 2 decimal spaces.
    	    HubStaff.totalHours = Math.round(tempTotalHours * 100.0) / 100.0;
	                		
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
}
