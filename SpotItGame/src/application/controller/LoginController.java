package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import application.model.dataIO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main controller for Login.fxml
 * Handles the logic for logging into the application
 * 
 * @author Bhavya Gokana
 * Fall 2021
 */
public class LoginController {

	@FXML
    private Label title;

    @FXML
    private Button startButton;
    
    @FXML
    private TextField name;

    @FXML
    private Label invalid;
    
    @FXML
    private AnchorPane mainPane;
    
    /**
     * initializes the text colors
     */
    public void initialize() {
    	//set color of text
    	ArrayList<String> colors = dataIO.readColor();
    	String t = colors.get(1);
    	String tFill = "-fx-text-fill: #" + t.substring(2, t.length()) +";";
    	title.setStyle(tFill);
    	startButton.setStyle(tFill);
    }
    /**
     * If the user has entered a valid string, store the username and switch to the menu screen
     * Otherwise, display an error message
     */
    @FXML
    void login(ActionEvent event) throws IOException {
    	
    	if(name.getText().equals("")) {
    		invalid.setOpacity(1);
    	}
    	else {
    		dataIO.writeUsername(name.getText());
    		
    		URL url = new File("src/application/view/Menu.fxml").toURI().toURL();
        	mainPane = FXMLLoader.load(url);
            Scene scene = new Scene(mainPane);// pane you are GOING TO show
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
            scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
        	
            window.setScene(scene);
            window.show();
            
    		
    		
    	}
    	
    }

}
