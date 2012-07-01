package utils;

public class DisplayRangeObject {

	private double min;
	private double max;
	private int channels;
	
	public DisplayRangeObject(double min, double max, int channels) {
		super();
		this.min = min;
		this.max = max;
		this.channels = channels;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public int getChannels() {
		return channels;
	}

	public void setChannels(int channels) {
		this.channels = channels;
	}
	
}
