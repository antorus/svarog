package svarogapp;
	
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;

public class Main extends Application {
	
	public static void alert() {

		Alert alert = new Alert(AlertType.NONE,"", ButtonType.OK);
			alert.setTitle("ERROR!");
		    alert.setContentText("You need to enter your name and both files to continue!");
		    alert.getDialogPane().setPrefSize(200, 100); 
		    alert.showAndWait();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			
		    Parent root = FXMLLoader.load(getClass().getResource("Design.fxml"));
			Scene scene = new Scene(root);
						
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("icon.jpg")));
			primaryStage.setTitle("Svarog");
			primaryStage.setScene(scene);
			primaryStage.show();

			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

