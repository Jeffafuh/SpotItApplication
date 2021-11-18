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
	
	
	public playerThread(Deck d, Deck playerDeck, Pane deckPane, Pane playerPane, Label cardCounter) {
		this.d = d;
		this.playerDeck = playerDeck;
		this.deckPane = deckPane;
		this.playerPane = playerPane;
		this.cardCounter = cardCounter;
		
		displayCard(playerDeck.peek(), playerPane);
	}
	
	public void displayCard(Card c, Pane p)
    {
    	p.getChildren().clear();
    	Circle circle = new Circle(160);
    	circle.setTranslateX(160);
    	circle.setTranslateY(160);
    	circle.setFill(Color.WHITE);
    	circle.setStroke(Color.BLACK);
    	
    	ArrayList<Symbol> s = c.getSymbolList();
    	for(Symbol symb : s)
    	{
    		ImageView i = new ImageView();
    		int randSize = (int)(Math.random()*30+30);
    		i.setPreserveRatio(true);
    		i.setFitHeight(randSize);
        	i.setFitWidth(randSize);
 
    		try {
        		loadedImage img = new loadedImage(symb.getSymbol().getPath());
        		i.setImage(img);
        	}
    		catch(Exception e) { e.printStackTrace(); }
    		
        	i.setRotate(Math.random()*360);
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
	
	public double[] randPoint()
    {
    	double[] point = new double[2];
    	double angle = Math.random() * 2 * Math.PI;
    	double hyp = Math.sqrt(Math.random()) * 100;
    	point[0] = 100+(Math.cos(angle) * hyp);
    	point[1] = 100+(Math.sin(angle) * hyp);
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
			if(checkMatch(img.getPath(), deckPane))
			{
				playerDeck.push(d.pop());
	    		
	    		Platform.runLater(new Runnable() {
	    			public void run()
	    			{
	    				displayCard(playerDeck.peek(), playerPane);
	    	    		displayCard(d.peek(), deckPane);
	    				cardCounter.setText("Cards Remaining: "+d.getDeckSize());
	    			}
	    		});
			}
			else
			{
				n.setEffect(null);;
			}
		}
		
		return playerDeck.getDeckSize();
	}

}
