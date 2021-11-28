package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import application.model.Leaderboard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LeaderboardController {
	@FXML
	private Label name1,name2,name3,name4;
	@FXML
	private Label points1,points2,points3,points4;
	@FXML
    private ChoiceBox<String> symbolSelect;
	 @FXML
	private AnchorPane mainPane;
	private Leaderboard board;
	
	@FXML
	public void initialize() {
		board=new Leaderboard();
		try {
			board.generateBoard("points2.txt");
			ArrayList<String> names=board.getNames();
			ArrayList<Integer> scores=board.getScores();
			int i=0;
			int j=0;
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
			for(int score:scores) {
				if(j==0) {
					points1.setText(String.valueOf(score));
				}
				else if(j==1) {
					points2.setText(String.valueOf(score));
				}
				else if(j==2) {
					points3.setText(String.valueOf(score));
				}
				else if(j==3){
					points4.setText(String.valueOf(score));
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
	
	public String getNumSymbols()
    {
    	String s = symbolSelect.getValue();
    	String newS=s.substring(0, s.indexOf(' '));
    	return newS;
    }
	
	@FXML
	public void newBoard() {
		board=new Leaderboard();
		String symbols=getNumSymbols();
		String fileName="points"+symbols+".txt";
		try {
			board.generateBoard(fileName);
			ArrayList<String> names=board.getNames();
			ArrayList<Integer> scores=board.getScores();
			int i=0;
			int j=0;
			name1.setText("Jane Doe");
			name2.setText("John Doe");
			name3.setText("Jane Doe");
			name4.setText("John Doe");
			points1.setText("0pts");
			points2.setText("0pts");
			points3.setText("0pts");
			points4.setText("0pts");
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
			for(int score:scores) {
				if(j==0) {
					points1.setText(String.valueOf(score));
				}
				else if(j==1) {
					points2.setText(String.valueOf(score));
				}
				else if(j==2) {
					points3.setText(String.valueOf(score));
				}
				else if(j==3){
					points4.setText(String.valueOf(score));
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
	
	@FXML
    void switchToLogin(ActionEvent event) throws IOException{
    	URL url = new File("src/application/view/Login.fxml").toURI().toURL();
    	mainPane = FXMLLoader.load(url);
        Scene scene = new Scene(mainPane);// pane you are GOING TO show
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
        scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
    	
        window.setScene(scene);
        window.show();
    }
}
