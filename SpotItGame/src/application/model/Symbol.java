package application.model;
import java.io.File;

/**
 * Class representing a symbol to go on a card
 * Contains a file to the image that the symbol represents
 * 
 * @author Jeff Dong
 * Fall 2021
 */
public class Symbol {
	
	private File file;
	
	/**
	 * Class constructor, sets the file data member
	 * 
	 * @param f File object to the image of the symbol
	 */
	public Symbol(File f)
	{
		this.file = f;
	}
	
	/**
	 * Returns the File of the image for the symbol
	 * 
	 * @return File of the image
	 */
	public File getSymbol()
	{
		return file;
	}
	
	/**
	 * Given another Symbol object, compares the Files contained by both symbols.
	 * 
	 * @return  If the Files are equal, return true
	 *			Return false otherwise
	 */
	@Override
	public boolean equals(Object o)
	{
		Symbol s = (Symbol)o;
		if(s.getSymbol().equals(file))
			return true;
		return false;
	}
	
	/**
	 * Opens the images directory and pulls a random file from the list of images.
	 * Creates a new symbol using that randomly pulled file and returns it.
	 * 
	 * @return A new symbol containing the randomly picked image file
	 */
	public static Symbol getRandSymbol()
	{
		File imageFile = new File("symbolImages");
		File[] fileList = imageFile.listFiles();
		
		int randInt = (int)(Math.random()*fileList.length);
		Symbol toAdd = new Symbol(fileList[randInt]);
		
		return toAdd;
	}
	
	/**
	 * Returns a formatted string containing the file path of the image for the symbol
	 */
	public String toString()
	{
		String output = "";
		output += file.getPath();
		return output;
	}

}
