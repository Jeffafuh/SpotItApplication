package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import application.model.Leaderboard;
import application.model.dataIO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main controller for Leaderboard.fxml
 * Handles the logic for displaying the leaderboard
 * 
 * @author Joy Kiprotich
 * Fall 2021
 */
public class LeaderboardController {
	
	@FXML
	private Label name1,name2,name3,name4;
	@FXML
	private Label points1,points2,points3,points4;
	@FXML
    private ChoiceBox<String> symbolSelect;
	
	@FXML
    private AnchorPane mainPane;
	@FXML
	private Leaderboard board;
	
	/**
     * Adds all options to the selection menu, selects the first option by default
     * Generates a new leaderboard on startup
     */
	@FXML
	public void initialize() {
		  symbolSelect.getItems().add("2 Symbols");
    	symbolSelect.getItems().add("3 Symbols");
    	symbolSelect.getItems().add("4 Symbols");
    	symbolSelect.getItems().add("5 Symbols");
    	symbolSelect.getItems().add("6 Symbols");
    	symbolSelect.getItems().add("8 Symbols");
    	symbolSelect.getItems().add("9 Symbols");
    	symbolSelect.getItems().add("10 Symbols");
    	symbolSelect.getSelectionModel().select(0);
    	
    	newBoard();
	}
	
	/**
     * Gets the number of symbols requested by the selection menu
     * 
     * @return Number of symbols on the symbol select
     */
	public String getNumSymbols()
    {
    	String s = symbolSelect.getValue();
    	String newS=s.substring(0, s.indexOf(' '));
    	return newS;
    }
	
	/**
	 * Generates a new leaderboard based on the number of symbols selected
	 */
	@FXML
	public void newBoard() {
		board=new Leaderboard();
		String symbols=getNumSymbols();
		String fileName="points"+symbols+".txt";
		try {
			board.generateBoard(fileName);
			ArrayList<String> names=board.getNames();
			ArrayList<Integer> scores=board.getScores();
			Collections.reverse(names);
			Collections.reverse(scores);
			int i=0;
			int j=0;
			name1.setText("Jane Doe");
			name2.setText("John Doe");
			name3.setText("Jane Doe");
			name4.setText("John Doe");
			points1.setText("00:00:00:000");
			points2.setText("00:00:00:000");
			points3.setText("00:00:00:000");
			points4.setText("00:00:00:000");
			for(String name:names) {
				if(i==0) {
					name1.setText(name);
				}
				else if(i==1) {
					name2.setText(name);
				}
				else if(i==2) {
					name3.setText(name);
				}
				else if(i==3){
					name4.setText(name);
				}
				else {
					break;
				}
				i+=1;
			}
			Date d = new Date();
			SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss:S");
			df.setTimeZone(TimeZone.getTimeZone("GMT"));
			for(int score:scores) {
				d.setTime(score);
				if(j==0) {
					points1.setText(df.format(d));
				}
				else if(j==1) {
					points2.setText(df.format(d));
				}
				else if(j==2) {
					points3.setText(df.format(d));
				}
				else if(j==3){
					points4.setText(df.format(d));
				}
				else {
					break;
				}
				j+=1;
			}
			
		}catch( IOException e ) {
			System.out.println( "Error loading the file - please check its location." );
			e.printStackTrace();
		}
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
