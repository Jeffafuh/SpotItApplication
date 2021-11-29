package application.model;

import java.io.*;
import java.util.*;

/**
 * Class responsible for gathering data for the leaderboard
 * 
 * @author Joy Kiprotich
 * Fall 2021
 */
public class Leaderboard {
	private ArrayList<String> names;
	private ArrayList<Integer> scores;
	
	/**
	 * Class constructor, initializes list to empty lists
	 */
	public Leaderboard() {
		names=new ArrayList<String>();
		scores=new ArrayList<Integer>();
	}
	
	/**
	 * Gets the list containing all usernames in the leaderboard
	 * 
	 * @return List of Strings
	 */
	public ArrayList<String> getNames() {
		return names;
	}
	
	/**
	 * Sets the list of usernames in the leaderboard
	 * 
	 * @param names Usernames to be set
	 */
	public void setNames(ArrayList<String> names) {
		this.names = names;
	}

	/**
	 * Gets the list of scores in the leaderboard
	 * 
	 * @param names Scores to be set
	 */
	public ArrayList<Integer> getScores() {
		return scores;
	}

	/**
	 * Sets the list of scores in the leaderboard
	 * 
	 * @param names Scores to be set
	 */
	public void setScores(ArrayList<Integer> scores) {
		this.scores = scores;
	}

	/**
	 * Given a filename, read in all of the data and update the names & score lists respectively
	 */
	public void generateBoard(String fileName) throws IOException{
		String fName="data/"+fileName;
		int count=0;
		File f1=new File(fName);
		Scanner scr=new Scanner(f1);
		while(scr.hasNextLine()) {
			String line=scr.nextLine();
			String tempName=line.split("-")[0];
			int score=Integer.parseInt(line.split("-")[1]);
			if(count==0) {
				names.add(tempName);
				scores.add(score);
				count+=1;
			}
			else {
				Boolean inserted=false;
				int i=0;
				for(int s: scores) {
					if(score>s) {
						scores.add(i,score);
						names.add(i,tempName);
						inserted=true;
						break;
					}
					i+=1;
				}
				if(!inserted) {
					names.add(tempName);
					scores.add(score);
				}
				count+=1;
			}
		}
		scr.close();
	}

}
