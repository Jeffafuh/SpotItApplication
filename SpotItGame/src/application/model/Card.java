package application.model;
import java.util.ArrayList;

public class Card {

	private ArrayList<Symbol> symbols;
	
	public Card()
	{
		symbols = new ArrayList<Symbol>();
	}
	
	public void addSymbol(Symbol s)
	{
		symbols.add(s);
	}
	
	public ArrayList<Symbol> getSymbolList()
	{
		return symbols;
	}
	
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
