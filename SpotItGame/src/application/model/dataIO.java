package application.model;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class dataIO {
	
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
	
	public static void writeDeckViewData(int N, int numCards)
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
	
	public static ArrayList<String> readDeckViewData()
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
	public static void writeColor(String selectedColor)
	{
		try{
			File fileOut = new File("data/gameColor.txt");
			FileWriter out = new FileWriter(fileOut);

			out.write(selectedColor);
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
		String s = "";
		
		try{
			Scanner in = new Scanner(new File("data/gameColor.txt"));
			
			String line = in.nextLine();
			String[] data = line.split(" ");
			for(String c: data)
				s+=c;
			
			in.close();
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return s;
	}
	
}
