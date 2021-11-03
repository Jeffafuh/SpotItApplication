package application.model;
import java.util.ArrayList;

public class Deck {

	private ArrayList<Card> cards;
	private ArrayList<Symbol> symbols;
	
	public Deck()
	{
		cards = new ArrayList<Card>();
		symbols = new ArrayList<Symbol>();
	}
	
	public void emptyDeck()
	{
		cards.clear();
	}
	
	public Card pop()
	{
		if(!isDeckEmpty())
			return cards.remove(0);
		else return new Card();
	}
	
	public void push(Card c)
	{
		cards.add(c);
	}
	
	public boolean isDeckEmpty()
	{
		return cards.isEmpty();
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
	public void reconstructDeck(int order)
	{
		emptyDeck();
		
		//first card
		Card toAdd = new Card();
		for(int i = 0; i < order+1; i++)
		{
			toAdd.addSymbol(symbols.get(i));
		}
		cards.add(toAdd);
		
		//next n cards
		for(int i = 1; i <= order; i++)
		{
			toAdd = new Card();
			toAdd.addSymbol(symbols.get(0));
			
			for(int j = 0; j < order; j++)
			{
				toAdd.addSymbol(symbols.get(order*i+j+1));
			}
			cards.add(toAdd);
		}
		
		//add the remaining n^2 cards
		for(int i = 1; i <= order; i++)
		{
			for(int j = 1; j <= order; j++)
			{
				toAdd = new Card();
				toAdd.addSymbol(symbols.get(i));
				
				for(int k = 1; k <= order; k++)
				{
					int index = order+2+order*(k-1)+(((i-1)*(k-1)+j-1)%order);
					toAdd.addSymbol(symbols.get(index-1));
				}
				cards.add(toAdd);
			}
		}
	}
}
