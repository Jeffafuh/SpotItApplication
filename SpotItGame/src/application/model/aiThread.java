package application.model;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class aiThread extends gameThread{
	
	public aiThread(String username, int order, Deck d, Deck playerDeck, Pane deckPane, Pane playerPane, Label cardCounter, Label pCardCounter) {
		super(username, order, d, playerDeck, deckPane, playerPane, cardCounter, pCardCounter);
		
		setMinPSize((-15.0/7.0) * order + 44);
		displayCards();
		updateCounters();
	}
	
	@Override
	protected Integer call() throws Exception {

		while(!getD().isDeckEmpty())
		{
			int randSeconds = (int)(Math.random()*5+3);
			Thread.sleep(6 * 1000);
			
			if(getD().isDeckEmpty())
	    	{
	    		break;
	    	}
			
			Card top = getPlayerDeck().peek();
			int randSymb = 1+(int)(Math.random()*(top.getSymbolList().size()));
			
			ImageView n = (ImageView)getPlayerPane().getChildren().get(randSymb);
	    	loadedImage img = (loadedImage)n.getImage();
	    	
	    	Platform.runLater(() -> {
	    		n.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.color(1, 0, 0), 20, 0.5, 0.0, 0.0));
	    	});
	    	Thread.sleep(500);
	    	Platform.runLater(() -> {
	    		n.setEffect(null);
	    	});
	    	
	    	if(getD().isDeckEmpty())
	    	{
	    		break;
	    	}
	    	
	    	Platform.runLater(() -> {
	    		gameThread.checkMatch(img.getPath(), this);
	    	});
		}
	
		return getPlayerDeck().getDeckSize();
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
        		randSize = (Math.random()*(40-getMinPSize())+getMinPSize());
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
}
