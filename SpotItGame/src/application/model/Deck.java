package application.model;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	public ArrayList<Card> cards;
	private ArrayList<Symbol> symbols;
	
	public Deck()
	{
		cards = new ArrayList<Card>();
		symbols = new ArrayList<Symbol>();
	}
	
	public void shuffleDeck()
	{
		Collections.shuffle(cards);
	}
	
	public void emptyDeck()
	{
		cards.clear();
	}
	
	public Card peek()
	{
		if(!isDeckEmpty())
			return cards.get(0);
		else return null;
	}
	
	public Card pop()
	{
		if(!isDeckEmpty())
			return cards.remove(0);
		else return null;
	}
	
	public void push(Card c)
	{
		cards.add(c);
	}
	
	public boolean isDeckEmpty()
	{
		return cards.isEmpty();
	}
	
	public int getDeckSize()
	{
		return cards.size();
	}
	
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
