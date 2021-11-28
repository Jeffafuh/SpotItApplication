package application.model;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Abstract thread class used for the player and ai threads
 * Contains general information regarding displaying/generating cards
 * 
 * @author Jeff Dong
 * Fall 2021
 */
public abstract class gameThread extends Task<Integer>{
	
	private Deck d, playerDeck;
	private Pane deckPane;
	private Pane playerPane;
	private Label cardCounter;
	private Label pCardCounter;
	
	private double minPSize;
	private double minDSize;
	private String username;
	
	/**
	 * Class constructor, initializes all fields and calculates the minimum deck symbol size
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
	
	/**
	 * Displays a single card onto the pane specified
	 */
	public abstract void displayCard(Card c, Pane p, boolean isPlayer);
	
	/**
	 * Gets the minimum player symbol size for each card
	 * 
	 * @return Minimum size
	 */
	public double getMinPSize() {
		return minPSize;
	}

	/**
	 * Sets the minimum player symbol size for each card
	 * 
	 * @param minPSize Minimum size
	 */
	public void setMinPSize(double minPSize) {
		this.minPSize = minPSize;
	}

	/**
	 * Gets the minimum deck symbol size for each card
	 * 
	 * @return Minimum size
	 */
	public double getMinDSize() {
		return minDSize;
	}

	/**
	 * Sets the minimum deck symbol size for each card
	 * 
	 * @param minDSize Minimum size
	 */
	public void setMinDSize(double minDSize) {
		this.minDSize = minDSize;
	}
	
	/**
	 * Gets the main deck shared by all players
	 * 
	 * @return Deck
	 */
	public Deck getD() {
		return d;
	}

	/**
	 * Gets the deck used by the player
	 * 
	 * @return Deck
	 */
	public Deck getPlayerDeck() {
		return playerDeck;
	}

	/**
	 * Gets the main deck pane shared by all players
	 * 
	 * @return Deck Pane
	 */
	public Pane getDeckPane() {
		return deckPane;
	}

	/**
	 * Gets the deck pane used by the player
	 * 
	 * @return Player Pane
	 */
	public Pane getPlayerPane() {
		return playerPane;
	}

	/**
	 * Gets the current name of the player using the thread
	 * 
	 * @return Username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Given a path to an image, check if that matches a symbol on the player card for the gameThread
	 * If it does match, update the decks accordingly
	 * 
	 * @param path File path to the Symbol's image
	 * @param g Player to be checked against
	 */
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
	
	/**
	 * Generates a random point inside a circle with radius r
	 * 
	 * @param r Radius of the circle
	 * @return Double array containing the point inside the circle
	 */
	public double[] randPoint(double r)
    {
    	double[] point = new double[2];
    	double angle = Math.random() * 2 * Math.PI;
    	double hyp = Math.sqrt(Math.random()) * (19.0/26.0) * r;
    	point[0] = (45.0/52.0) * r+(Math.cos(angle) * hyp);
    	point[1] = (45.0/52.0) * r+(Math.sin(angle) * hyp);
    	return point;
    }
	
	/**
	 * Updates the number of cards currently in the player deck and the main deck
	 */
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
	
	/**
	 * Displays the top card of each deck on their respective panes
	 */
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
}
