/* The goal of this class is to process a common CSV file, identify the selected employee's name,
 * filter the amount of tickets handled by their productivity values, and return a final value
 * to be used for further processing.
 */

package svarogapp;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ProductivityCSVReader {
	
	public static String agentName;
	public static String csvFile;
	private static String line = "";
	private static String csvSplitBy = ",";
	
   /* Constant array of Zendesk queues that shall be later filtered and checked in the CSV file
	* For this specific task, the queues were split between those that count for twice the productivity
	* and those that don't.
	* Specific values have been replaced with generics.
	*/
	
	private final static String[] RAW_DOUBLE_TASKS = {"Game Play", "Advanced Game Play"};
	private final static String[] RAW_SIMPLE_TASKS = {"Billing"};
	
	//Final amount of tickets value to be used by Controller class
	public static double totalProd = 0;
	
	public static void main(String[] args) {
    	
		//Values sent to lists for easier processing
		List<String> doubleProductiveTasks = new ArrayList<String>();
		for(String t : RAW_DOUBLE_TASKS) {
			doubleProductiveTasks.add(t);
		}
		
		List<String> simpleProductiveTasks = new ArrayList<String>();
		for(String t : RAW_SIMPLE_TASKS) {
			simpleProductiveTasks.add(t);
		}
		
		int tempProd = 0;

    	List<Integer> doubleProd = new ArrayList<Integer>();
    	List<Integer> simpleProd = new ArrayList<Integer>();
    	List<Integer> allProd = new ArrayList<Integer>();
	
    	try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile),"UTF-8"))) {
    		while ((line = br.readLine()) != null) {
        	
    			String[] splitCsv = line.split(csvSplitBy);
    			    			            
    				//File is checked for matches with the agent's(employees) name and queues worked.
    				//Productivity values for queues that count for double productivity are filtered separately.
    				if(splitCsv[0].equals(agentName) && doubleProductiveTasks.contains(splitCsv[2])) {
    					int result = Integer.parseInt(splitCsv[4]);
    					result = result*2;
    					doubleProd.add(result);
    				}
            
    				if(splitCsv[0].equals(agentName) && simpleProductiveTasks.contains(splitCsv[2])) {
    					int result = Integer.parseInt(splitCsv[4]);
    					simpleProd.add(result);
    				}

    		}
    		
			allProd.addAll(doubleProd);
			allProd.addAll(simpleProd);

			for (int x : allProd) {
				tempProd+=x;
			}	
			
			ProductivityCSVReader.totalProd = (double)tempProd;

    	} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		
    }
	
}
