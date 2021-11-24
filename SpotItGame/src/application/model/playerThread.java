package application.model;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class playerThread extends Task<Integer>{
	
	private Deck d, playerDeck;
	private Pane deckPane;
	private Pane playerPane;
	private Label cardCounter;
	
	private double minPSize;
	private double minDSize;
	
	public playerThread(int order, Deck d, Deck playerDeck, Pane deckPane, Pane playerPane, Label cardCounter) {
		this.d = d;
		this.playerDeck = playerDeck;
		this.deckPane = deckPane;
		this.playerPane = playerPane;
		this.cardCounter = cardCounter;
		
		minPSize = (-15.0/7.0) * order + 44;
		minDSize = (-25.0/7.0) * order + 67;
		
		displayCard(playerDeck.peek(), playerPane, true);
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
        		randSize = (Math.random()*(40-minPSize)+minPSize);
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
	
	public double[] randPoint(double r)
    {
    	double[] point = new double[2];
    	double angle = Math.random() * 2 * Math.PI;
    	double hyp = Math.sqrt(Math.random()) * (19.0/26.0) * r;
    	point[0] = (45.0/52.0) * r+(Math.cos(angle) * hyp);
    	point[1] = (45.0/52.0) * r+(Math.sin(angle) * hyp);
    	return point;
    }

	@Override
	protected Integer call() throws Exception {

		while(!d.isDeckEmpty())
		{
			int randSeconds = (int)(Math.random()*5+3);
			Thread.sleep(randSeconds * 1000);
			
			Card top = playerDeck.peek();
			int randSymb = 1+(int)(Math.random()*top.getSymbolList().size());
			
			ImageView n = (ImageView)playerPane.getChildren().get(randSymb);
	    	loadedImage img = (loadedImage)n.getImage();
			
	    	n.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.color(1, 0, 0), 20, 0.5, 0.0, 0.0));
	    	Thread.sleep(500);
	    	
	    	if(d.isDeckEmpty())
	    	{
	    		n.setEffect(null);
	    		break;
	    	}
	    	
			if(checkMatch(img.getPath(), deckPane))
			{
				playerDeck.push(d.pop());
	    		
	    		Platform.runLater(new Runnable() {
	    			public void run()
	    			{
	    				displayCard(playerDeck.peek(), playerPane, true);
	    	    		displayCard(d.peek(), deckPane, false);
	    				cardCounter.setText("Cards Remaining: "+d.getDeckSize());
	    			}
	    		});
			}
			else
			{
				n.setEffect(null);
			}
		}
		
		return playerDeck.getDeckSize();
	}

}
