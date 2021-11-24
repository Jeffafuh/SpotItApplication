package application.model;
import java.util.ArrayList;

/**
 * Class representing a single card in a Spot It! deck.
 * Contains a list of symbols that are on the card
 * 
 * @author Jeff Dong
 * Fall 2021
 */
public class Card {

	private ArrayList<Symbol> symbols;
	
	/**
	 * Class constructor, initializes the symbols list to an empty list
	 */
	public Card()
	{
		symbols = new ArrayList<Symbol>();
	}
	
	/**
	 * Adds a symbol to the card
	 * 
	 * @param s Symbol to be added to the list
	 */
	public void addSymbol(Symbol s)
	{
		symbols.add(s);
	}
	
	/**
	 * Returns the list of symbols used on the card
	 * 
	 * @return ArrayList of symbols
	 */
	public ArrayList<Symbol> getSymbolList()
	{
		return symbols;
	}
	
	/**
	 * Return a formatted string containing a list of all of the symbols file paths that are on the card
	 */
	public String toString()
	{
		String output = "[ ";
		for(int i = 0; i < symbols.size(); i++)
		{
			output += symbols.get(i)+" ";
		}
		output += "]";
		return output;
	}
}
