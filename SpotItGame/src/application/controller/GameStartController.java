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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameStartController {
	
	@FXML
    private Label errorText;
	
	@FXML
    private TextField numCards;

    @FXML
    private Button startButton;
    
    @FXML
    private ChoiceBox<String> symbolSelect;
    
    @FXML
    private AnchorPane mainPane;
    
    //thread for counter
    
    @FXML
    public void initialize()
    {
    	symbolSelect.getItems().add("2 (Default 3 Cards)");
    	symbolSelect.getItems().add("3 (Default 7 Cards)");
    	symbolSelect.getItems().add("4 (Default 13 Cards)");
    	symbolSelect.getItems().add("5 (Default 21 Cards)");
    	symbolSelect.getItems().add("6 (Default 31 Cards)");
    	symbolSelect.getItems().add("8 (Default 57 Cards)");
    	symbolSelect.getItems().add("9 (Default 73 Cards)");
    	symbolSelect.getItems().add("10 (Default 91 Cards)");
    	symbolSelect.getSelectionModel().select(0);
    }
    
    public int getNumSymbols()
    {
    	String s = symbolSelect.getValue();
    	int a = Integer.parseInt(s.substring(0, s.indexOf(' ')));
    	return a;
    }

    @FXML
    void startGame(ActionEvent event) throws IOException{
    	try {
    		int N = getNumSymbols()-1;
    		int cardNum;
    		
    		if(numCards.getText().length() == 0)
    			cardNum = N*N+N+1;
    		else cardNum = Integer.parseInt(numCards.getText());
    		
    		if(cardNum < 2)
    		{
    			throw new Exception("Please enter a number > 1");
    		}
    		
    		dataIO.writeGameData(N, cardNum);
    		switchToGame(event);
    	}
    	catch(NumberFormatException e)
    	{
    		errorText.setText("Please enter a valid number");
    	}
    	catch(Exception e)
    	{
    		errorText.setText(e.getMessage());
    	}
    }
    
    void switchToGame(ActionEvent event) throws IOException
    {
    	URL url = new File("src/application/view/Game.fxml").toURI().toURL();
    	mainPane = FXMLLoader.load(url);
        Scene scene = new Scene(mainPane);// pane you are GOING TO show
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
        scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
    	
        window.setScene(scene);
        window.show();
    }

}
