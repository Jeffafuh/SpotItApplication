package application;
import java.io.File;

public class Symbol {
	
	private File file;
	
	public Symbol(File f)
	{
		this.file = f;
	}
	
	public File getSymbol()
	{
		return file;
	}
	
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
		File imageFile = new File("images");
		File[] fileList = imageFile.listFiles();
		
		int randInt = (int)(Math.random()*fileList.length);
		Symbol toAdd = new Symbol(fileList[randInt]);
		
		return toAdd;
	}
	
	public String toString()
	{
		String output = "";
		output += file.getPath();
		return output;
	}

}
