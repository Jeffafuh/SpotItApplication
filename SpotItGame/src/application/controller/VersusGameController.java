package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import application.model.Deck;
import application.model.dataIO;
import application.model.gameThread;
import application.model.playerThread;
import application.model.aiThread;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Main controller for VersusGame.fxml
 * Handles the logic for running the versus player mode
 * 
 * @author Jeff Dong
 * Fall 2021
 */
public class VersusGameController {
	
	@FXML
    private Button replayButton;
	
	@FXML
    private Label cardCounter;
	
	@FXML
    private Label pCardCounter;
	
	@FXML
    private Label winnerText;

	@FXML
    private Pane deckPane;

    @FXML
    private Pane playerPane;
    
    @FXML
    private Pane AIPane1;
    
    @FXML
    private Pane AIPane3;

    @FXML
    private Pane AIPane2;
    
    @FXML
    private Label CardCounter3;

    @FXML
    private Label CardCounter2;

    @FXML
    private Label CardCounter1;

    @FXML
    private AnchorPane mainPane;
    
    private Deck d;
    private String username;
    private aiThread ai1, ai2, ai3;
    private playerThread player;
    private int joinCnt;

    /**
     * Reads in the game settings and creates the deck accordingly
     * Calls the method to initialize and start all player/ai threads
     */
    public void initialize()
    {	
    	username = dataIO.readUsername();
    	ArrayList<String> data = dataIO.readGameData();
    	d = new Deck();
    	
    	int order = Integer.parseInt(data.get(0));
    	d.constructNewDeck(order);
    	d.shuffleDeck();
    	d.adjustDeck(Integer.parseInt(data.get(1))-(order*order+order+1));

    	startGame(order);
    }
    
    /**
     * Assigns random names to the ai's and creates/starts all threads required for the game
     * 
     * @param order Order of the deck used
     */
    public void startGame(int order)
    {
    	String[] names = {"Jeff", "Bhavya", "Anaya", "Joy", "Vamshi"};
    	ArrayList<Integer> rand = new ArrayList<Integer>();
    	for(int i = 0; i < names.length; i++)
    		rand.add(i);
    	Collections.shuffle(rand);
    	
    	Deck temp = new Deck();
    	temp.push(d.pop());
    	ai1 = new aiThread(names[rand.get(0)], order, d, temp, deckPane, AIPane1, cardCounter, CardCounter1);
    	Thread t0 = new Thread(ai1);
    	t0.setDaemon(true);
    	t0.start();
    	
    	temp = new Deck();
    	temp.push(d.pop());
    	ai2 = new aiThread(names[rand.get(1)], order, d, temp, deckPane, AIPane2, cardCounter, CardCounter2);
    	Thread t1 = new Thread(ai2);
    	t1.setDaemon(true);
    	t1.start();
    	
    	temp = new Deck();
    	temp.push(d.pop());
    	ai3 = new aiThread(names[rand.get(2)], order, d, temp, deckPane, AIPane3, cardCounter, CardCounter3);
    	Thread t2 = new Thread(ai3);
    	t2.setDaemon(true);
    	t2.start();
    	
    	temp = new Deck();
    	temp.push(d.pop());
    	player = new playerThread(username, order, d, temp, deckPane, playerPane, cardCounter, pCardCounter);
    	Thread p = new Thread(player);
    	p.setDaemon(true);
    	p.start();
    	
    	ai1.setOnSucceeded(e -> {
    		gameEnd(e);
    	});
    	ai2.setOnSucceeded(e -> {
    		gameEnd(e);
    	});
    	ai3.setOnSucceeded(e -> {
    		gameEnd(e);
    	});
    	player.setOnSucceeded(e -> {
    		gameEnd(e);
    	});
    	
    	joinCnt = 0;
    }
    
    /**
     * Waits from a response from all four threads before commencing the final tally of the winner
     * 
     * If all four threads have not finished, display a waiting message
     * Otherwise, find the winner and display their name/info
     */
    public void gameEnd(WorkerStateEvent e)
    {
    	joinCnt++;
    	if(joinCnt == 4)
    	{
    		replayButton.setOpacity(1);
    		ArrayList<gameThread> winners = findMaxThread(ai1, ai2, ai3, player);
    		
    		String outString = "";
    		switch(winners.size())
    		{
    		case 1:
    			outString += "WINNER!\n";
    			outString += "With "+winners.get(0).getPlayerDeck().getDeckSize()+" cards in their deck...\n";
    			outString += winners.get(0).getUsername()+"!";
    			break;
    		default://more than 1 winner
    			outString += "A TIE!\n";
    			outString += "With "+winners.get(0).getPlayerDeck().getDeckSize()+" cards in their decks...\n";
    			outString += winners.get(0).getUsername();
    			for(int i = 1; i < winners.size() - 1; i++)
    			{
    				outString += ", "+winners.get(i).getUsername();
    			}
    			outString += " and "+winners.get(winners.size()-1).getUsername()+"!";
    			break;
    		}
        	winnerText.setText(outString);
    	}
    	else
    	{
    		String outText = "Calculating results";
    		for(int i = 0; i < joinCnt; i++)
    		{
    			outText += ".";
    		}
    		winnerText.setText(outText);
    	}
    }
    
    /**
     * Given a list of gameThreads, find out which one(s) have the biggest deck size and return them
     * 
     * @param a List of all gameThreads to be considered
     * @return gameThreads with the highest number of cards in their decks
     */
    public ArrayList<gameThread> findMaxThread(gameThread... a)
    {
    	ArrayList<gameThread> max = new ArrayList<gameThread>();
    	max.add(a[0]);
    	
    	for(int i = 1; i < a.length; i++)
    	{
    		if(a[i].getPlayerDeck().getDeckSize() > max.get(0).getPlayerDeck().getDeckSize())
    		{
    			max.clear();
    			max.add(a[i]);
    		}
    		else if(a[i].getPlayerDeck().getDeckSize() == max.get(0).getPlayerDeck().getDeckSize())
    		{
    			max.add(a[i]);
    		}
    	}
    	
    	return max;
    }
    
    /**
     * Reloads the .fxml file to start a new game given the same settings
     */
    @FXML
    void resetGame(ActionEvent event) throws IOException{
    	URL url = new File("src/application/view/VersusGame.fxml").toURI().toURL();
    	mainPane = FXMLLoader.load(url);
        Scene scene = new Scene(mainPane);// pane you are GOING TO show
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
        scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
    	
        window.setScene(scene);
        window.show();
    }
    
    /**
     * Switches the screen back to the game config menu
     */
    @FXML
    void switchToGameStart(ActionEvent event) throws IOException{
    	d.emptyDeck();
    	
    	URL url = new File("src/application/view/GameStart.fxml").toURI().toURL();
    	mainPane = FXMLLoader.load(url);
        Scene scene = new Scene(mainPane);// pane you are GOING TO show
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
        scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
    	
        window.setScene(scene);
        window.show();
    }
    
}

