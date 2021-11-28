package application;
	
import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

/**
 * Java class containing the main method to run the program and display the application.
 * 
 * @author Jeff Dong, Bhavya Gokana, Anaya Hartfield, Joy Kiprotich, Vamshi Ponnala
 * UTSA CS 3443 - Final Group Project
 * Fall 2021
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			URL url = new File("src/application/view/Login.fxml").toURI().toURL();
	    	AnchorPane root = FXMLLoader.load(url);
			Scene scene = new Scene(root,1200,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
