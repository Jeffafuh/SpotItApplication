package application.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class GameTimer implements Runnable{
	
    private Label timer, penalty;
    private Deck d;
    private long milisec;
    
    public GameTimer(Label penalty, Label t, Deck d)
    {
    	milisec = 0;
    	timer = t;
    	this.d = d;
    	this.penalty = penalty;
    }
    
    public void addTime(int seconds)
    {
    	milisec += seconds*1000;
    }
    
    public String getFormattedTime()
    {
    	Date d = new Date(milisec);
    	SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss:S");
    	df.setTimeZone(TimeZone.getTimeZone("GMT"));

	    return df.format(d);
    }
    
    public long getTime()
    {
    	return milisec;
    }

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
