package application.model;

import javafx.scene.control.Label;

public class GameTimer extends Thread{
	
    private Label timer;
    private int seconds;
    
    public GameTimer(Label t)
    {
    	seconds = 0;
    	timer = t;
    }
	
	@Override
	public void run()
	{
		while(true) {
			try {
				timer.setText(String.valueOf(seconds));
				Thread.sleep(1000);
				seconds++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
