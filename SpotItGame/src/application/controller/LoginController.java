package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {


    @FXML
    private TextField name;

    @FXML
    private Label invalid;
    
    @FXML
    private AnchorPane mainPane;
    
    @FXML
    void login(ActionEvent event) throws IOException {
    	
    	if(name.getText().equals("")) {
    		invalid.setText("Please enter your name");
    	}
    	else {
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
