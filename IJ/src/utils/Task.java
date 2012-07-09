package utils;

import java.awt.Toolkit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.SwingWorker;

public class Task extends SwingWorker<Void, Void> {
    /*
     * Main task. Executed in background thread.
     */
	private int maxPositions;
	private AtomicInteger position;
	
	public Task(int maxPositions){
		this.maxPositions = maxPositions;
		this.position = new AtomicInteger(0);
	}
	
    @Override
    public Void doInBackground() {
        
        int progress = 0;
        //Initialize progress property.
        setProgress(0);
        int oldPostition = 0;
        while (progress < maxPositions) {
            //Sleep for up to one second.
        	progress = position.get();
            if(progress > oldPostition){
            	System.out.println("Task is modifying progress to " + progress);
                setProgress(progress);
                oldPostition = progress;
            }
        }
        return null;
    }

    /*
     * Executed in event dispatching thread
     */
    @Override
    public void done() {
        Toolkit.getDefaultToolkit().beep();
    }

	public AtomicInteger getPosition() {
		return position;
	}

	public void setPosition(AtomicInteger position) {
		this.position = position;
	}

	public int getMaxPositions() {
		return maxPositions;
	}
	
}
