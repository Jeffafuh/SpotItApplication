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
    private Pane rightPane;

    @FXML
    private Pane leftPane;

    @FXML
    private AnchorPane mainPane;
    
    private Deck d;
    
    private int minSize;

    public void initialize()
    {	
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

    	Thread t = new Thread(new GameTimer(timer,d));
    	t.setDaemon(true);
    	t.start();
    }
    
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
    }
    
    public void gameEnd()
    {
    	replayButton.setOpacity(1);
    	submitButton.setOpacity(1);
    	displayCard(new Card(), leftPane);
    	displayCard(new Card(), rightPane);
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
    
    public double[] randPoint()
    {
    	double[] point = new double[2];
    	double angle = Math.random() * 2 * Math.PI;
    	double hyp = Math.sqrt(Math.random()) * 190;
    	point[0] = 225+(Math.cos(angle) * hyp);
    	point[1] = 225+(Math.sin(angle) * hyp);
    	return point;
    }
    
    @FXML
    void submitScore(ActionEvent event) {

    }
    
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

