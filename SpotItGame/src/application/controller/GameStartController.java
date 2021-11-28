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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main controller for GameStart.fxml
 * Handles the logic for configuring a game of Spot It!
 * 
 * @author Jeff Dong
 * Fall 2021
 */
public class GameStartController {
	
	@FXML
    private Slider modeSelect;
	
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
    
    @FXML
    private Label symbolsPrompt;

    @FXML
    private Label cardPrompt;
    
    @FXML
    private Label modeTitle;

    @FXML
    private Label burndownSelect;

    @FXML
    private Label classicSelect;

    @FXML
    private Button backButton;

    @FXML
    private Label defaultPrompt;
    
    //thread for counter
    
    /**
     * Adds all options to the selection menu, selects the first option by default
     */
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
    	
    	//set color of background
    	ArrayList<String> colors = dataIO.readColor();
    	String b = colors.get(0);
    	String t = colors.get(1);
    	mainPane.styleProperty().setValue("-fx-background: #"+ b.substring(2, b.length()) +";");
    	//set color of text
    	String tFill = "-fx-text-fill: #" + t.substring(2, t.length()) +";";
    	errorText.setStyle(tFill);
    	startButton.setStyle(tFill);
    	numCards.setStyle(tFill);
    	modeTitle.setStyle(tFill);;
    	burndownSelect.setStyle(tFill);;
    	symbolsPrompt.setStyle(tFill);
    	cardPrompt.setStyle(tFill);
    	classicSelect.setStyle(tFill);;
    	backButton.setStyle(tFill);;
    	defaultPrompt.setStyle(tFill);;
    	
    	
    }
    
    /**
     * Gets the number of symbols requested by the selection menu
     * 
     * @return Number of symbols on the symbol select
     */
    public int getNumSymbols()
    {
    	String s = symbolSelect.getValue();
    	int a = Integer.parseInt(s.substring(0, s.indexOf(' ')));
    	return a;
    }

    /**
     * Attempts to start a new game given the current configurations
     * 
     * If successful, write the game settings and switch to the respective screen
     * Otherwise, display an error message below the button
     */
    @FXML
    void startGame(ActionEvent event) throws IOException{
    	try {
    		int N = getNumSymbols()-1;
    		int cardNum;
    		
    		if(numCards.getText().length() == 0)
    			cardNum = N*N+N+1;
    		else cardNum = Integer.parseInt(numCards.getText());
    		
    		if(modeSelect.getValue() == 0)
    		{
    			if(cardNum < 2)
        		{
        			throw new Exception("Please have more than 1 card in the deck.");
        		}
    			dataIO.writeGameData(N, cardNum);
    			switchToGame(event);
    		}
    		else {
    			if(cardNum < 5)
        		{
        			throw new Exception("Please have more than 5 cards in the deck.");
        		}
    			dataIO.writeGameData(N, cardNum);
    			switchToVersusGame(event);
    		}
    	}
    	catch(NumberFormatException e)
    	{
    		errorText.setText("Please enter a valid number.");
    	}
    	catch(Exception e)
    	{
    		errorText.setText(e.getMessage());
    	}
    }
    
    /**
     * Switches the scene to the single player game
     */
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
    
    /**
     * Switches the scene to the versus player game
     */
    void switchToVersusGame(ActionEvent event) throws IOException
    {
    	URL url = new File("src/application/view/VersusGame.fxml").toURI().toURL();
    	mainPane = FXMLLoader.load(url);
        Scene scene = new Scene(mainPane);// pane you are GOING TO show
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
        scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
    	
        window.setScene(scene);
        window.show();
    }
    
    /**
     * Switches the scene to the main menu
     */
    @FXML
    void switchToMenu(ActionEvent event) throws IOException{
    	URL url = new File("src/application/view/Menu.fxml").toURI().toURL();
    	mainPane = FXMLLoader.load(url);
        Scene scene = new Scene(mainPane);// pane you are GOING TO show
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
        scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
    	
        window.setScene(scene);
        window.show();
    }

}
