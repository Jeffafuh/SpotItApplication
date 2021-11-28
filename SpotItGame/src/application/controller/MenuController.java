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
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuController {
	//instantiate action objects
	@FXML
    private AnchorPane mainPane;

    @FXML
    private Label titleLabel;

    @FXML
    private Button createButton;

    @FXML
    private Button playButton;

    @FXML
    private Button leaderboardButton;

    @FXML
    private Label backgroundPrompt;

    @FXML
    private Button confirmButton;

    @FXML
    private ColorPicker colorPick;

    @FXML
    private ColorPicker textPick;

    @FXML
    private Label textPrompt;
    
    @FXML
    /**
     * initializes the color selection dropdown menu
     */
    public void initialize()
    {	
    	//set color of background
    	ArrayList<String> colors = dataIO.readColor();
    	String b = colors.get(0);
    	String t = colors.get(1);
    	mainPane.styleProperty().setValue("-fx-background: #"+ b.substring(2, b.length()) +";");
    	//set color of text
    	String tFill = "-fx-text-fill: #" + t.substring(2, t.length()) +";";
    	titleLabel.setStyle(tFill);
    	textPrompt.setStyle(tFill);
    	backgroundPrompt.setStyle(tFill);
    	playButton.setStyle(tFill);
    	leaderboardButton.setStyle(tFill);
    	createButton.setStyle(tFill);
    	confirmButton.setStyle(tFill);
    }
    
    @FXML
    /**
     * changes the color when confirm button clicked
     * @param event
     */
    void changeColor(ActionEvent event) {
    	Color bColor = colorPick.getValue();
    	Color tColor = textPick.getValue();
    	dataIO.writeColor(bColor.toString(), tColor.toString());
    	
    	//set color of background
    	ArrayList<String> colors = dataIO.readColor();
    	String b = colors.get(0);
    	String t = colors.get(1);
    	mainPane.styleProperty().setValue("-fx-background: #"+ b.substring(2, b.length()) +";");
    	//set color of text
    	String tFill = "-fx-text-fill: #" + t.substring(2, t.length()) +";";
    	titleLabel.setStyle(tFill);
    	textPrompt.setStyle(tFill);
    	backgroundPrompt.setStyle(tFill);
    	playButton.setStyle(tFill);
    	leaderboardButton.setStyle(tFill);
    	createButton.setStyle(tFill);
    	confirmButton.setStyle(tFill);
    }
    
    @FXML
    /**
     * method to switch to the leaderboard screen
     * @param event
     * @throws IOException
     */
    void switchLeaderboards(ActionEvent event) throws IOException{
    	URL url = new File("src/application/view/Leaderboard.fxml").toURI().toURL();
    	mainPane = FXMLLoader.load(url);
        Scene scene = new Scene(mainPane);// pane you are GOING TO show
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
        scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
    	
        window.setScene(scene);
        window.show();

    }

    @FXML
    /**
     * method that handles switching to the create screen
     * @param event
     * @throws IOException
     */
    void switchToCreate(ActionEvent event) throws IOException {
    	URL url = new File("src/application/view/CreateDeck.fxml").toURI().toURL();
    	mainPane = FXMLLoader.load(url);
        Scene scene = new Scene(mainPane);// pane you are GOING TO show
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
        scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
    	
        window.setScene(scene);
        window.show();
    }

    @FXML
    /**
     * method that handles switching to the game screen
     * @param event
     * @throws IOException
     */
    void switchToPlay(ActionEvent event) throws IOException{
    	URL url = new File("src/application/view/GameStart.fxml").toURI().toURL();
    	mainPane = FXMLLoader.load(url);
        Scene scene = new Scene(mainPane);// pane you are GOING TO show
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
        scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
    	
        window.setScene(scene);
        window.show();
    }
    

}
