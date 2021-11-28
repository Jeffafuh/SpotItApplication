package application.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 * Main thread for the timer in the single player mode
 * Stores time in miliseconds
 * 
 * @author Jeff Dong
 * Fall 2021
 */
public class GameTimer implements Runnable{
	
    private Label timer, penalty;
    private Deck d;
    private long milisec;
    
    /**
     * Class constructor, initializes all fields
     * 
     * @param penalty Label used to display the penalty for wrong clicks
     * @param t Label used to display the time
     * @param d Main deck used in the game
     */
    public GameTimer(Label penalty, Label t, Deck d)
    {
    	milisec = 0;
    	timer = t;
    	this.d = d;
    	this.penalty = penalty;
    }
    
    /**
     * Adds a specified number of seconds to the timer
     * 
     * @param seconds Number of seconds to be added
     */
    public void addTime(int seconds)
    {
    	milisec += seconds*1000;
    }
    
    /**
     * Given a time in miliseconds, return a formatted string containing how much time as elapsed.
     * Formatted as Hours:Minutes:Seconds:Miliseconds
     * 
     * @return Formatted time string
     */
    public String getFormattedTime()
    {
    	Date d = new Date(milisec);
    	SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss:S");
    	df.setTimeZone(TimeZone.getTimeZone("GMT"));

	    return df.format(d);
    }
    
    /**
     * Returns the raw value of the time in miliseconds
     * 
     * @return Time elapsed by the timer
     */
    public long getTime()
    {
    	return milisec;
    }

    /**
     * Main thread logic
     * Continues to run while the deck is not empty, adds time one milisecond at a time
     * Also manages the fade/display of the penalty label
     */
	@Override
	public void run() {
		try {
			while(!d.isDeckEmpty())
			{
				Platform.runLater(new Runnable() {
					public void run()
					{
						timer.setText(getFormattedTime());
						if(penalty.getOpacity() > 0)
						{
							penalty.setOpacity(penalty.getOpacity()-0.002);
						}
						else
						{
							penalty.setLayoutX(100);
							penalty.setLayoutY(0);
						}
					}
				});
				milisec++;
				Thread.sleep(1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
