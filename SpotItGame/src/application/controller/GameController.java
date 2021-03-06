package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import application.model.Card;
import application.model.Deck;
import application.model.GameTimer;
import application.model.Symbol;
import application.model.dataIO;
import application.model.loadedImage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Main controller for Game.fxml
 * Handles the logic for running the single player mode
 * 
 * @author Jeff Dong
 * Fall 2021
 */
public class GameController {
	
	@FXML
    private Button submitButton;
	
	@FXML
    private Button replayButton;
	
	@FXML
    private Label cardCounter;
	
	@FXML
    private Label timer;
	
	@FXML
    private Label timerPenalty;
	
	@FXML
    private Label submitLabel;

	@FXML
    private Pane rightPane;

    @FXML
    private Pane leftPane;
    
    @FXML
    private Button backButton;

    @FXML
    private AnchorPane mainPane;
    
    private Deck d;
    private GameTimer t;
    private int minSize;
    
    /**
     * Reads in the game settings and creates the deck accordingly
     * Displays both initial cards and starts the timer thread
     */
    public void initialize()
    {	
    	//set color of background
    	ArrayList<String> colors = dataIO.readColor();
    	String b = colors.get(0);
    	String textColor = colors.get(1);
    	mainPane.styleProperty().setValue("-fx-background: #"+ b.substring(2, b.length()) +";");
    	//set color of text
    	String tFill = "-fx-text-fill: #" + textColor.substring(2, textColor.length()) +";";
    	timer.setStyle(tFill);
    	submitLabel.setStyle(tFill);
    	timerPenalty.setStyle(tFill);
    	cardCounter.setStyle(tFill);
    	submitButton.setStyle(tFill);
    	replayButton.setStyle(tFill);
    	backButton.setStyle(tFill);
    	
    	ArrayList<String> data = dataIO.readGameData();
    	d = new Deck();
    	int order = Integer.parseInt(data.get(0));
    	minSize = -10*order+120;
    	d.constructNewDeck(order);
    	d.shuffleDeck();
    	d.adjustDeck(Integer.parseInt(data.get(1))-(order*order+order+1));
    	displayCard(d.pop(),leftPane);
    	displayCard(d.peek(),rightPane);
    	cardCounter.setText("# of matches remaining: "+d.getDeckSize());

    	t = new GameTimer(timerPenalty, timer, d);
    	Thread thread = new Thread(t);
    	thread.setDaemon(true);
    	thread.start();
    }
    
    /**
	 * When the user clicks on an image, check and see if that symbol matches one on the deck pane
	 * 
	 * If it matches, adjust the decks accordingly.
	 * Otherwise, add a penalty of 5 seconds to the timer
	 * 
	 * @param e Image clicked
	 */
    public void check(MouseEvent e)
    {
    	ImageView n = (ImageView)e.getTarget();
    	loadedImage img = (loadedImage)n.getImage();
    	
    	boolean match = false;
    	if(n.getParent().getId().equals("leftPane"))
    	{
    		match = checkMatch(img.getPath(), rightPane);
    	}
    	else//clicked right pane
    	{
    		match = checkMatch(img.getPath(), leftPane);
    	}
    	
    	if(match)
    	{
    		d.pop();
    		Card toDisplay = d.peek();
    		if(!d.isDeckEmpty())
    		{
    			if(Math.random() < 0.5)
	    			displayCard(toDisplay, leftPane);
	    		else
	    			displayCard(toDisplay, rightPane);
    		}
    		else gameEnd();
    		cardCounter.setText("# of matches remaining: "+d.getDeckSize());
    	}
    	else
    	{
    		t.addTime(5);
    		timerPenalty.setOpacity(1);
    		timerPenalty.setLayoutX(e.getSceneX()+25);
    		timerPenalty.setLayoutY(e.getSceneY()+25);
    	}
    }
    
    /**
     * Clears all cards and display the replay & submit buttons
     */
    public void gameEnd()
    {
    	replayButton.setOpacity(1);
    	submitButton.setOpacity(1);
    	displayCard(new Card(), leftPane);
    	displayCard(new Card(), rightPane);
    }
    
    /**
	 * Given a path to an image, check if that matches a symbol on the pane specified
	 * 
	 * @param path File path to the Symbol's image
	 * @param p Pane to be checked against
	 * @return Whether or not the symbol matches
	 */
    public boolean checkMatch(String path, Pane p)
    {
    	boolean match = false;
    	ObservableList<Node> children = p.getChildren();
    	
    	for(int i = 1; i < children.size(); i++)
    	{
    		ImageView temp = (ImageView)children.get(i);
    		loadedImage curImg = (loadedImage)temp.getImage();
    		if(curImg.getPath().contentEquals(path))
    		{
    			match = true;
    			break;
    		}
    	}
    	
    	return match;
    }
    
    /**
	 * Displays a single card onto the pane specified
	 */
    public void displayCard(Card c, Pane p)
    {
    	p.getChildren().clear();
    	Circle circle = new Circle(260);
    	circle.setTranslateX(260);
    	circle.setTranslateY(260);
    	circle.setFill(Color.WHITE);
    	circle.setStroke(Color.BLACK);
    	
    	ArrayList<Symbol> s = c.getSymbolList();
    	for(Symbol symb : s)
    	{
    		ImageView i = new ImageView();
    		int randSize = (int)(Math.random()*(100-minSize)+minSize);
    		i.setPreserveRatio(true);
    		i.setFitHeight(randSize);
        	i.setFitWidth(randSize);
 
    		try {
        		loadedImage img = new loadedImage(symb.getSymbol().getPath());
        		i.setImage(img);
        	}
    		catch(Exception e) { e.printStackTrace(); }
    		
        	i.setRotate(Math.random()*360);
        	i.setId("symbolImage");
        	i.setOnMouseClicked(MouseEvent -> check(MouseEvent));
        	int counter = 0;
        	
        	boolean intersects;
        	do {
        		intersects = false;
	        	double[] point = randPoint();
	        	i.setTranslateX(point[0]);
	        	i.setTranslateY(point[1]);
	        	
	        	for(Node n : p.getChildren())
	        	{
	        		if(i.getBoundsInParent().intersects(n.getBoundsInParent()))
	        		{
	        			intersects = true;
	        		}
	        	}
	        	counter++;
        	}while(intersects && counter < 1000);
        	
        	p.getChildren().add(i);
    	}
    	
    	p.getChildren().add(0, circle);
    }
    
    /**
	 * Generates a random point inside a circle with radius r
	 * 
	 * @param r Radius of the circle
	 * @return Double array containing the point inside the circle
	 */
    public double[] randPoint()
    {
    	double[] point = new double[2];
    	double angle = Math.random() * 2 * Math.PI;
    	double hyp = Math.sqrt(Math.random()) * 190;
    	point[0] = 225+(Math.cos(angle) * hyp);
    	point[1] = 225+(Math.sin(angle) * hyp);
    	return point;
    }
    
    /**
     * Submits the username and time of the user after completing a game
     * Displays a message indicating that the score has been submitted
     */
    @FXML
    void submitScore(ActionEvent event) {
    	String username = dataIO.readUsername();
    	ArrayList<String> data = dataIO.readGameData();
    	dataIO.writeGameScore(Integer.parseInt(data.get(0))+1, username, t.getTime());
    	submitButton.setOpacity(0);
    	submitLabel.setText("Score submitted!");
    }
    
    /**
     * Reloads the .fxml file to start a new game given the same settings
     */
    @FXML
    void resetGame(ActionEvent event) throws IOException{
    	URL url = new File("src/application/view/Game.fxml").toURI().toURL();
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

