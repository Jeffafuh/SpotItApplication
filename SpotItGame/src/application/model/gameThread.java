package application.model;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class gameThread extends Task<Integer>{
	
	private Deck d, playerDeck;
	private Pane deckPane;
	private Pane playerPane;
	private Label cardCounter;
	private Label pCardCounter;
	
	private double minPSize;
	private double minDSize;
	private String username;
	
	public gameThread(String username, int order, Deck d, Deck playerDeck, Pane deckPane, Pane playerPane, Label cardCounter, Label pCardCounter) {
		this.d = d;
		this.playerDeck = playerDeck;
		this.deckPane = deckPane;
		this.playerPane = playerPane;
		this.cardCounter = cardCounter;
		this.pCardCounter = pCardCounter;
		this.username = username;
		
		minDSize = (-25.0/7.0) * order + 67;
	}
	
	public abstract void displayCard(Card c, Pane p, boolean isPlayer);
	
	public double getMinPSize() {
		return minPSize;
	}

	public void setMinPSize(double minPSize) {
		this.minPSize = minPSize;
	}

	public double getMinDSize() {
		return minDSize;
	}

	public void setMinDSize(double minDSize) {
		this.minDSize = minDSize;
	}
	
	public Deck getD() {
		return d;
	}

	public Deck getPlayerDeck() {
		return playerDeck;
	}

	public Pane getDeckPane() {
		return deckPane;
	}

	public Pane getPlayerPane() {
		return playerPane;
	}

	public String getUsername() {
		return username;
	}

	public static synchronized void checkMatch(String path, gameThread g)
    {
    	boolean match = false;
    	ObservableList<Node> children = g.getDeckPane().getChildren();
    	
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
    	
    	if(match)
    	{
    		g.getPlayerDeck().push(g.getD().pop());
    		g.displayCards();	
    		g.updateCounters();
    	}
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
	
	public void updateCounters()
    {
		Platform.runLater(new Runnable() {
			public void run()
			{
				cardCounter.setText("Deck\nCards Remaining: "+d.getDeckSize());
				pCardCounter.setText(username+"\nCards in deck: "+playerDeck.getDeckSize());
			}
		});
    }
	
	public void displayCards()
	{
		Platform.runLater(new Runnable() {
			public void run()
			{
				displayCard(playerDeck.peek(), playerPane, true);
	    	    displayCard(d.peek(), deckPane, false);
			}
		});
	}

	public void clearCards()
	{
		Platform.runLater(new Runnable() {
			public void run()
			{
				displayCard(new Card(), playerPane, true);
			    displayCard(new Card(), deckPane, false);
			}
		});
	}
}
