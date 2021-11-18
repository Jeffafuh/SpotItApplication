package application.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class GameTimer implements Runnable{
	
    private Label timer;
    private Deck d;
    private long milisec;
    
    public GameTimer(Label t, Deck d)
    {
    	milisec = 0;
    	timer = t;
    	this.d = d;
    }
    
    public String getFormattedTime()
    {
    	Date d = new Date(milisec);
    	SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss:S");
    	df.setTimeZone(TimeZone.getTimeZone("GMT"));

	    return df.format(d);
    }

	@Override
	public void run() {
		try {
			while(!d.isDeckEmpty())
			{
				System.out.println(milisec);
				Platform.runLater(new Runnable() {
					public void run()
					{
						timer.setText(getFormattedTime());
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
