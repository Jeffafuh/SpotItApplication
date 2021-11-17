package application.model;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

public class GameTimer extends Task{
	
    private Label timer;
    private Deck d;
    private int seconds;
    
    public GameTimer(Label t, Deck d)
    {
    	seconds = 0;
    	timer = t;
    	this.d = d;
    }

	@Override
	protected Object call() throws Exception {
		try {
			while(!d.isDeckEmpty())
			{
				Platform.runLater(new Runnable() {
					public void run()
					{
						timer.setText(String.valueOf(seconds));
					}
				});
				seconds++;
				Thread.sleep(1000);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
