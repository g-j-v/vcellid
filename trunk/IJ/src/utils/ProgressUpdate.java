package utils;

import javax.swing.ProgressMonitor;

public class ProgressUpdate implements Runnable{

	ProgressMonitor progressMonitor;
	int value;
	
	public ProgressUpdate(ProgressMonitor progressMonitor, int value){
		this.progressMonitor = progressMonitor;
		this.value = value;
	}
	
	@Override
	public void run() {
		if (progressMonitor.isCanceled()) {
	    	  progressMonitor.close();
	        return;
	    }
		progressMonitor.setProgress(value);
		progressMonitor.setNote("Operation is " + value + "% complete");
		
	}

}
