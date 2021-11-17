package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GameController {


    @FXML
    private TextField name;

    @FXML
    private Label invalid;
    
    @FXML
    void start(ActionEvent event) {
    	
    	if(name.getText().equals("")) {
    		invalid.setText("Please enter your name");
    	}
    	else {
    		System.out.println("Game Started");
    	}
    	
    }


}
