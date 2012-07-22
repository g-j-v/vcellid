package utils;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class SegmentationValues {

	private static SegmentationValues segmentationValues;
	
	private double maxDistValue;
	private double maxSplitValue;
	private double minPixelsValue;
	private double maxPixelsValue;
	private double backgroundRejectValue;
	private double trackingComparisonValue;
	
	//To store the last selected button.
	private int cellAlignmentButtonSelected;
	private int frameAlignmentButtonSelected;
	
	private boolean advancedParametersEnabled;
	private String advancedParameters;

	
	public static SegmentationValues getInstance(){
		if(segmentationValues == null){
			segmentationValues = new SegmentationValues();
		}
		return segmentationValues;
	}
	
	public SegmentationValues(){
		
		maxDistValue = 50.0;
		maxSplitValue = 5.0;
		minPixelsValue = 100.0;
		maxPixelsValue = 100.0;
		backgroundRejectValue = 5.0;
		trackingComparisonValue = 5.0;
		
		cellAlignmentButtonSelected = 1;
		frameAlignmentButtonSelected = 1;
		
		advancedParametersEnabled = false;
		advancedParameters = "";
	}

	public double getMaxDistValue() {
		return maxDistValue;
	}

	public void setMaxDistValue(double maxDistValue) {
		this.maxDistValue = maxDistValue;
	}

	public double getMaxSplitValue() {
		return maxSplitValue;
	}

	public void setMaxSplitValue(double maxSplitValue) {
		this.maxSplitValue = maxSplitValue;
	}

	public double getMinPixelsValue() {
		return minPixelsValue;
	}

	public void setMinPixelsValue(double minPixelsValue) {
		this.minPixelsValue = minPixelsValue;
	}

	public double getMaxPixelsValue() {
		return maxPixelsValue;
	}

	public void setMaxPixelsValue(double maxPixelsValue) {
		this.maxPixelsValue = maxPixelsValue;
	}

	public double getBackgroundRejectValue() {
		return backgroundRejectValue;
	}

	public void setBackgroundRejectValue(double backgroundRejectValue) {
		this.backgroundRejectValue = backgroundRejectValue;
	}

	public double getTrackingComparisonValue() {
		return trackingComparisonValue;
	}

	public void setTrackingComparisonValue(double trackingComparisonValue) {
		this.trackingComparisonValue = trackingComparisonValue;
	}

	public int getCellAlignmentButtonSelected() {
		return cellAlignmentButtonSelected;
	}

	public void setCellAlignmentButtonSelected(int cellAlignmentButtonSelected) {
		this.cellAlignmentButtonSelected = cellAlignmentButtonSelected;
	}

	public int getFrameAlignmentButtonSelected() {
		return frameAlignmentButtonSelected;
	}

	public void setFrameAlignmentButtonSelected(int frameAlignmentButtonSelected) {
		this.frameAlignmentButtonSelected = frameAlignmentButtonSelected;
	}

	public boolean isAdvancedParametersEnabled() {
		return advancedParametersEnabled;
	}

	public void setAdvancedParametersEnabled(boolean advancedParametersEnabled) {
		this.advancedParametersEnabled = advancedParametersEnabled;
	}

	public String getAdvancedParameters() {
		return advancedParameters;
	}

	public void setAdvancedParameters(String advancedParameters) {
		this.advancedParameters = advancedParameters;
	}

	
}
