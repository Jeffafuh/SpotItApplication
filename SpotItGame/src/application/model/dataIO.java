package application.model;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Static class used for the purposes of reading/writing data to and from the data directory
 * 
 * @author Jeff Dong
 * Fall 2021
 */
public class dataIO {
	
	/**
	 * Write a users score in the single player mode to the corresponding file
	 * 
	 * Opens file "pointsX.txt" under the data directory, where X is the number of symbols the user played with
	 * Appends to the end of the file in the format "<Username>-<miliseconds>"
	 * 
	 * @param numSymbols Number of symbols the user played with
	 * @param username Username of the user
	 * @param miliseconds Time elapsed in miliseconds for the player while playing
	 */
	public static void writeGameScore(int numSymbols, String username, long miliseconds)
	{
		try{
			String fileName = "data/points" + numSymbols + ".txt";
			File fileOut = new File(fileName);
			FileWriter out = new FileWriter(fileOut, true);
			
			String outString = username+"-"+miliseconds+"\n";

			out.write(outString);
			out.close();
		}
		catch(Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * Writes a username to the "user.txt" file under the data directory
	 * If the file exists, truncate it, otherwise create a blank file
	 * 
	 * @param username Username to be stored
	 */
	public static void writeUsername(String username)
	{
		try{
			String fileName = "data/user.txt";
			File fileOut = new File(fileName);
			FileWriter out = new FileWriter(fileOut);
			
			String outString = username;

			out.write(outString);
			out.close();
		}
		catch(Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * Reads the username from "user.txt" under the data directory
	 * 
	 * @return Username stored
	 */
	public static String readUsername()
	{
		String username = "";
		try{
			Scanner in = new Scanner(new File("data/user.txt"));
			
			username = in.nextLine();
			username.trim();
			
			in.close();
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return username;
	}
	
	/**
	 * Writes the game settings to be used for the game
	 * 
	 * @param N Order of the deck to be used
	 * @param numCards Number of actual cards in the deck
	 */
	public static void writeGameData(int N, int numCards)
	{
		try{
			File fileOut = new File("data/gameSettings.txt");
			FileWriter out = new FileWriter(fileOut);
			
			String outString = N+" "+numCards;

			out.write(outString);
			out.close();
		}
		catch(Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * Reads the game settings stored in the data directory
	 * 
	 * @return List of all parameters to be used for the game creation
	 */
	public static ArrayList<String> readGameData()
	{
		ArrayList<String> a = new ArrayList<String>();
		
		try{
			Scanner in = new Scanner(new File("data/gameSettings.txt"));
			
			String line = in.nextLine();
			String[] data = line.split(" ");
			for(String s: data)
				a.add(s);
			
			in.close();
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return a;
	}
	
	/**
	 * writes the selected color to the file
	 * @param selectedColor
	 */
	public static void writeColor(String c)
	{
		try{
			File fileOut = new File("data/gameColor.txt");
			FileWriter out = new FileWriter(fileOut);

			out.write(c);
			out.close();
		}
		catch(Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * reads the color from the color file and returns a string
	 * @return
	 */
	public static String readColor()
	{
		String color = "";
		
		try{
			Scanner in = new Scanner(new File("data/gameColor.txt"));
			
			String line = in.nextLine();
			String[] data = line.split(" ");
			for(String c: data)
				color += c;
			in.close();
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return color;
	}
	
}
