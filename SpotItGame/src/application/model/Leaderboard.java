package application.model;

import java.io.*;
import java.util.*;

public class Leaderboard {
	private ArrayList<String> names;
	private ArrayList<Integer> scores;
	
	public Leaderboard() {
		names=new ArrayList<String>();
		scores=new ArrayList<Integer>();
	}
	
	public ArrayList<String> getNames() {
		return names;
	}
	
	public void setNames(ArrayList<String> names) {
		this.names = names;
	}

	public ArrayList<Integer> getScores() {
		return scores;
	}

	public void setScores(ArrayList<Integer> scores) {
		this.scores = scores;
	}

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
