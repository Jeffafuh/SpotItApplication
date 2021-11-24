package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import application.model.Card;
import application.model.Deck;
import application.model.Symbol;
import application.model.dataIO;
import application.model.loadedImage;
import application.model.playerThread;
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

public class VersusGameController {
	
	@FXML
    private Button replayButton;
	
	@FXML
    private Label cardCounter;

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
    private AnchorPane mainPane;
    
    private Deck d;
    
    private Deck playerDeck;
    
    private double minPSize;
    private double minDSize;

    public void initialize()
    {	
    	ArrayList<String> data = dataIO.readGameData();
    	d = new Deck();
    	playerDeck = new Deck();
    	
    	int order = Integer.parseInt(data.get(0));
    	minDSize = (-25.0/7.0) * order + 67;
    	minPSize = (-55.0/7.0) * order + 105;
    	d.constructNewDeck(order);
    	d.shuffleDeck();
    	d.adjustDeck(Integer.parseInt(data.get(1))-(order*order+order+1));
    	
    	playerDeck.push(d.pop());
    	cardCounter.setText("Cards Remaining: "+d.getDeckSize());

    	initPlayers(order);
    	
    	displayCard(playerDeck.peek(),playerPane, true);
    	displayCard(d.peek(),deckPane, false);
    }
    
    public void initPlayers(int order)
    {
    	Deck temp = new Deck();
    	temp.push(d.pop());
    	Thread t0 = new Thread(new playerThread(order, d, temp, deckPane, AIPane1, cardCounter));
    	t0.setDaemon(true);
    	t0.start();
    	
    	temp = new Deck();
    	temp.push(d.pop());
    	Thread t1 = new Thread(new playerThread(order, d, temp, deckPane, AIPane2, cardCounter));
    	t1.setDaemon(true);
    	t1.start();
    	
    	temp = new Deck();
    	temp.push(d.pop());
    	Thread t2 = new Thread(new playerThread(order, d, temp, deckPane, AIPane3, cardCounter));
    	t2.setDaemon(true);
    	t2.start();
    }
    
    public void check(MouseEvent e)
    {
    	ImageView n = (ImageView)e.getTarget();
    	loadedImage img = (loadedImage)n.getImage();
    	
    	boolean match = checkMatch(img.getPath(), deckPane);
    	
    	if(match)
    	{
    		playerDeck.push(d.pop());
    		if(!d.isDeckEmpty())
    		{
    			displayCard(playerDeck.peek(), playerPane, true);
    			displayCard(d.peek(), deckPane, false);
    		}
    		else gameEnd();
    		
    		cardCounter.setText("Cards Remaining: "+d.getDeckSize());
    	}
    }
    
    public void gameEnd()
    {
    	replayButton.setOpacity(1);
    	displayCard(new Card(), deckPane, false);
    }
    
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
    
    public void displayCard(Card c, Pane p, boolean isPlayer)
    {
    	p.getChildren().clear();
    	Circle circle = new Circle(p.getPrefHeight()/2);
    	circle.setTranslateX(p.getPrefHeight()/2);
    	circle.setTranslateY(p.getPrefHeight()/2);
    	circle.setFill(Color.WHITE);
    	circle.setStroke(Color.BLACK);
    	
    	ArrayList<Symbol> s = c.getSymbolList();
    	for(Symbol symb : s)
    	{
    		ImageView i = new ImageView();
 
    		try {
        		loadedImage img = new loadedImage(symb.getSymbol().getPath());
        		i.setImage(img);
        	}
    		catch(Exception e) { e.printStackTrace(); }
    		
        	double randSize;
        	if(isPlayer)
        	{
        		i.setId("symbolImage");
        		i.setOnMouseClicked(MouseEvent -> check(MouseEvent));
        		randSize = (Math.random()*(90-minPSize)+minPSize);
        	}
        	else
        	{
        		randSize = (Math.random()*(60-minDSize)+minDSize);
        	}
        	
        	i.setPreserveRatio(true);
        	i.setRotate(Math.random()*360);
    		i.setFitHeight(randSize);
        	i.setFitWidth(randSize);
        	
        	int counter = 0;
        	boolean intersects;
        	do {
        		intersects = false;
	        	double[] point = randPoint(p.getPrefHeight()/2);
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
    
    public double[] randPoint(double r)
    {
    	double[] point = new double[2];
    	double angle = Math.random() * 2 * Math.PI;
    	double hyp = Math.sqrt(Math.random()) * (19.0/26.0) * r;
    	point[0] = (45.0/52.0) * r+(Math.cos(angle) * hyp);
    	point[1] = (45.0/52.0) * r+(Math.sin(angle) * hyp);
    	return point;
    }
    
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

