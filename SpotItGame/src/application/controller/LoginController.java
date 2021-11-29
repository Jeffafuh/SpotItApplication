package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import application.model.dataIO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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
    private TextField name;

    @FXML
    private Label invalid;
    
    @FXML
    private AnchorPane mainPane;
    
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
