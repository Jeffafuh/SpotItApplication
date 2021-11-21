package application.controller;

import java.io.IOException;

import java.util.*;
import application.model.Leaderboard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LeaderboardController {
	@FXML
	private Label name1,name2,name3,name4;
	@FXML
	private Label points1,points2,points3,points4;
	private Leaderboard board;
	
	@FXML
	public void initialize() {
		board=new Leaderboard();
		try {
			board.generateBoard("points.txt");
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
	}
}
