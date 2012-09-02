/**
 * Singleton class to store segmentation values.
 * @author Alejandro Petit
 */
package utils;

public class SegmentationValues {

	private static SegmentationValues instance;
	
	private double maxDistValue;
	private double maxSplitValue;
	private double minPixelsValue;
	private double maxPixelsValue;
	private double backgroundRejectValue;
	private double trackingComparisonValue;
	
	//ImageSetup Parameters
	private boolean BFasFLflag;
	
	
	//To store the last selected button.
	private int cellAlignmentButtonSelected;
	private int frameAlignmentButtonSelected;
	
	private boolean advancedParametersEnabled;
	private String advancedParameters;

	/**
	 * 
	 * @return instance of SegmentationValues
	 */
	public static SegmentationValues getInstance(){
		if(instance == null){
			instance = new SegmentationValues();
		}
		return instance;
	}
	
	/**
	 * Constructor
	 */
	public SegmentationValues(){
		
		maxDistValue = 8.0;
		maxSplitValue = 0.5;
		minPixelsValue = 75.0;
		maxPixelsValue = 1500.0;
		backgroundRejectValue = 1.0;
		trackingComparisonValue = 0.2;
		
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
