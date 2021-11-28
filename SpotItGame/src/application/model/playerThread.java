package application.model;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Main thread for the player used in the versus mode
 * 
 * @author Jeff Dong
 * Fall 2021
 */
public class playerThread extends gameThread{
	
	/**
	 * Class constructor, initializes all fields and updates the cards/counters
	 * Calculates the minimum player symbol size
	 * 
	 * @param username Username of the player
	 * @param order Order of the FPP of the deck
	 * @param d Deck shared by everyone
	 * @param playerDeck Deck used by the player
	 * @param deckPane Pane for the main Deck
	 * @param playerPane Pane for the player
	 * @param cardCounter Card counter for the main deck
	 * @param pCardCounter Card counter for the player
	 */
	public playerThread(String username, int order, Deck d, Deck playerDeck, Pane deckPane, Pane playerPane, Label cardCounter, Label pCardCounter) {
		super(username, order, d, playerDeck, deckPane, playerPane, cardCounter, pCardCounter);
		
		setMinPSize((-55.0/7.0) * order + 105);
		displayCards();
		updateCounters();
	}
	
	/**
	 * Main thread logic
	 * Continues to work/live as long as the deck is not empty
	 */
	@Override
	protected Integer call() throws Exception {

		while(!getD().isDeckEmpty())
		{
			System.out.print("");
		}
	
		return getPlayerDeck().getDeckSize();
	}
	
	/**
	 * Displays a single card onto the pane specified
	 */
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
        		randSize = (Math.random()*(90-getMinPSize())+getMinPSize());
        	}
        	else
        	{
        		randSize = (Math.random()*(60-getMinDSize())+getMinDSize());
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
	
	/**
	 * When the user clicks on an image, check and see if that symbol matches one on the deck pane
	 * 
	 * @param e Image clicked
	 */
	public void check(MouseEvent e)
    {
    	ImageView n = (ImageView)e.getTarget();
    	loadedImage img = (loadedImage)n.getImage();
    	
    	Platform.runLater(() -> {
    		gameThread.checkMatch(img.getPath(), this);
    	});
    }
}
