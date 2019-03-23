package svarogapp;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Controller {
	
	@FXML
	private VBox rootNode;
	
	@FXML
	private ComboBox<String> boxOfNames;
	
    FileChooser fileChooser = new FileChooser();
	
	private static String agentName = "";
	private static String productivityCSVFile;
	private static String hubstaffCSVFile;
	private static String helpContent = "To start with, enter your full name and submit both of your CSV files.\n"
			+ "Make sure that your CSV files are for the same period of time!\n"
			+ "Any questions or suggestions you can mail me directly.";
		
	@FXML
	private TextField inputName;
	
	@FXML
	private Text yourName;
	
	@FXML
	private Text showGMtoolsFilePath;
	
	@FXML
	private Text showHubstaffFilePath;
	
	@FXML
	private Text showRPH;
	
	@FXML
	private MenuItem menuClose;
	
	@FXML
	private void handleComboBoxAction(ActionEvent event) {
	  agentName = boxOfNames.getSelectionModel().getSelectedItem();
	  yourName.setText("Your name is: " + agentName);
	  ProductivityCSVReader.agentName = boxOfNames.getSelectionModel().getSelectedItem();
	}
	
	@FXML
	public void close(ActionEvent event) {
	    Stage stage = (Stage) rootNode.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	public void showHelp(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION,"", ButtonType.CLOSE);
		alert.setTitle("HALP!");
		alert.setHeaderText("");
		alert.setContentText(helpContent);
		alert.show();
		
	}
	
	@FXML
	private void openGMCSV(ActionEvent event) {
        File file = fileChooser.showOpenDialog(rootNode.getScene().getWindow());
        if (file != null) {
        	
            productivityCSVFile = file.getPath();
            ProductivityCSVReader.csvFile = file.getPath();
            showGMtoolsFilePath.setText(productivityCSVFile);
        }
	}
	
	@FXML
	private void openHubCSV(ActionEvent event) {
        File file = fileChooser.showOpenDialog(rootNode.getScene().getWindow());
        if (file != null) {
            hubstaffCSVFile = file.getPath();
            HubStaff.csvFile = file.getPath();
            showHubstaffFilePath.setText(hubstaffCSVFile);
        }
	}
	
	@FXML
	private void setAgentName(ActionEvent event) {
		agentName = inputName.getText();
		ProductivityCSVReader.agentName = inputName.getText();
		yourName.setText("Your name is: " + agentName);
	}
	
	/* Calculates final productivity value (replies per hour) utilizing the values in the respective
	 * classes. Rounds it to one less decimal and displays it on the GUI.
	 */
	
	@FXML
	private void calcRPH(ActionEvent event) throws Exception {
		if(productivityCSVFile != null && hubstaffCSVFile != null && agentName != null) {
			ProductivityCSVReader.main(null);
			HubStaff.main(null);
			double rawRPH = ProductivityCSVReader.totalProd/HubStaff.totalHours;
			double RPH = Math.round(rawRPH * 10) / 10.0;
			showRPH.setText("Your RPH is: " + RPH);
		} else if(productivityCSVFile == null || hubstaffCSVFile == null || agentName == null) {
			Main.alert();
		}
	}
	
	//Adding list of employees to ComboBox to facilitate selection and avoid input errors. 
	@FXML
	public void initialize() {		
		boxOfNames.getItems().addAll("Agent Name 1", "Agent Name 2", "Agent Name 3");
	}
}
