package application.model;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class representing a deck of cards for the game Spot It!
 * Contains a list of symbols used to construct the deck & a list of cards in the deck
 * 
 * @author Jeff Dong
 * Fall 2021
 */
public class Deck {

	private ArrayList<Card> cards;
	private ArrayList<Symbol> symbols;
	
	/**
	 * Class constructor, initializes both lists to empty lists
	 */
	public Deck()
	{
		cards = new ArrayList<Card>();
		symbols = new ArrayList<Symbol>();
	}
	
	/**
	 * Shuffles the list of cards in the deck into a random order
	 */
	public void shuffleDeck()
	{
		Collections.shuffle(cards);
	}
	
	/**
	 * Empties the list of cards in the deck
	 */
	public void emptyDeck()
	{
		cards.clear();
	}
	
	/**
	 * Performs a peek function to the list of cards, identical to a stack
	 * 
	 * @return First card of the deck
	 */
	public Card peek()
	{
		if(!isDeckEmpty())
			return cards.get(0);
		else return new Card();
	}
	
	/**
	 * Performs a pop function to the list of cards, identical to a stack
	 * 
	 * @return First card of the deck
	 */
	public Card pop()
	{
		if(!isDeckEmpty())
			return cards.remove(0);
		else return new Card();
	}
	
	/**
	 * Adds a card to the front of the deck
	 * 
	 * @param c Card to be added to the front
	 */
	public void push(Card c)
	{
		cards.add(0,c);
	}
	
	/**
	 * Checks whether or not if the deck is empty
	 * 
	 * @return True if the deck is empty, false otherwise
	 */
	public boolean isDeckEmpty()
	{
		return cards.isEmpty();
	}
	
	/**
	 * Returns the number of cards in the deck
	 * 
	 * @return Size of the cards list
	 */
	public int getDeckSize()
	{
		return cards.size();
	}
	
	/**
	 * Returns the number of symbols used for the deck
	 * 
	 * @return Size of the symbols list
	 */
	public int getSymbolSize()
	{
		return symbols.size();
	}
	
	/**
	 * Adds or removes cards to the deck based on the specified value provided.
	 * 
	 * If the number to adjust the deck is positive, copy cards and add them to the deck
	 * If the number is negative, remove cards from the deck
	 * 
	 * @param diff Number of cards to be added/removed from the deck
	 */
	public void adjustDeck(int diff)
	{
		if(diff < 0)
		{
			while(diff < 0)
			{
				pop();
				diff++;
			}
		}
		else if (diff > 0)
		{
			int cnt = 0;
			while(diff > 0)
			{
				cards.add(cards.get(cnt));
				diff--;
				cnt = (cnt+1)%symbols.size();
			}
		}
		
		shuffleDeck();
	}
	
	/**
	 * Given a number of symbols, reads from the "images" directory and pulls out numSymbols amount of files to store as Symbols at random using getRandSymbol()
	 * Currently, there is no safe-guard against if the number of files < numSymbols (resulting in an infinite loop)
	 * 
	 * @param numSymbols Number of symbols to be initialized
	 */
	public void initializeSymbols(int numSymbols)
	{
		for(int i = 0; i < numSymbols; i++)
		{
			Symbol toAdd = Symbol.getRandSymbol();
			
			while(symbols.contains(toAdd))
			{
				toAdd = Symbol.getRandSymbol();
			}
			
			symbols.add(toAdd);
		}
	}
	
	/**
	 * Returns a formatted string containing all of the cards in the deck and their symbols
	 */
	public String toString()
	{
		String output = "";
		output += symbols.size() +" Symbols: ";
		for(int i = 0; i < symbols.size(); i++)
		{
			output += symbols.get(i)+" ";
		}
		output += "\n";
		
		for(int i = 0; i < cards.size(); i++)
		{
			output += cards.get(i)+"\n";
		}
		return output;
	}
	
	/**
	 * Given an order of the FPP, generate a new set of symbols and construct the deck using those symbols using a FPP
	 *  **IMPORTANT** Order must be prime or a power of a prime for a valid deck (Will generate regardless however)
	 * 
	 * @param order Order of the FPP to be constructed
	 */
	public void constructNewDeck(int order)
	{
		int numCards = (int)Math.pow(order, 2)+order+1;
		initializeSymbols(numCards);
		reconstructDeck(order);
	}
	
	/**
	 * Reconstructs the deck using the same set of symbols used prior
	 * Must call constructNewDeck() to initialize symbols before "reconstructing" the deck using the same symbols
	 * Alternatively, given an order <= numSymbols, you can construct a smaller deck using the same set of symbols
	 *  **IMPORTANT** Order must be prime or a power of a prime for a valid deck (Will generate regardless however)
	 * 
	 * @param order Order of the FPP to be constructed
	 */
	public void reconstructDeck(int N)
	{
		emptyDeck();
		
		Card toAdd;
		
		for(int i = 0; i < N+1; i++) 
		{
			toAdd = new Card();
			toAdd.addSymbol(symbols.get(0));
			
			for(int j = 0; j < N; j++) 
			{
				toAdd.addSymbol(symbols.get((j+1)+(i*N)));
			}
			
			cards.add(toAdd);
		}

		for(int i = 0; i < N; i++) 
		{
			for (int j = 0; j < N; j++) 
			{
				toAdd = new Card();
				toAdd.addSymbol(symbols.get(i+1));

				for (int k = 0; k < N; k++) 
				{
					int val = N+1 + N*k + FPPHelper.add(FPPHelper.mul(i, k, N), j, N);
					toAdd.addSymbol(symbols.get(val));
				}
				cards.add(toAdd);
			}
		}
	}
}
