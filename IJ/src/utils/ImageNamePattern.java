package utils;

import java.util.ArrayList;
import java.util.List;

public class ImageNamePattern {

	private static ImageNamePattern instance;
	
	private String BrightfieldChannelPattern;
	private String FluorChannelPattern;
	private boolean positionFlag;
	private String PositionPattern;
	private boolean timeFlag;
	private String TimePattern;
	private boolean separatorFlag;
	private String separator;

	public ImageNamePattern(){
		BrightfieldChannelPattern = "BF";
		FluorChannelPattern = "FP";
		positionFlag = true;
		PositionPattern = "Position";
		timeFlag = true;
		TimePattern = "time_";
		separatorFlag = true;
		separator = "_";
	}
	
	public static ImageNamePattern getInstance(){
		if(instance == null){
			instance = new ImageNamePattern();
		}
		return instance;
	}

	public String getBrightfieldChannelPattern() {
		return BrightfieldChannelPattern;
	}

	public void setBrightfieldChannelPattern(String brightfieldChannelPattern) {
		BrightfieldChannelPattern = brightfieldChannelPattern;
	}

	public String getFluorChannelPattern() {
		return FluorChannelPattern;
	}

	public void setFluorChannelPattern(String fluorChannelPattern) {
		FluorChannelPattern = fluorChannelPattern;
	}

	public String getPositionPattern() {
		return PositionPattern;
	}

	public void setPositionPattern(String positionPattern) {
		PositionPattern = positionPattern;
	}

	public String getTimePattern() {
		return TimePattern;
	}

	public void setTimePattern(String timePattern) {
		TimePattern = timePattern;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}
	
	public void restoreDefault(){
		BrightfieldChannelPattern = "BF";
		FluorChannelPattern = "FP";
		positionFlag = true;
		PositionPattern = "Position";
		timeFlag = true;
		TimePattern = "time_";
		separatorFlag = true;
		separator = "_";
	}
	
	public List<String> generatePatternList(){
		//TODO: Ver como validar los tipos de canales de fluor
		List<String> patterns = new ArrayList<String>();
		// Pattern para los fields
		patterns.add("("+ BrightfieldChannelPattern +"|([A-Z])FP)");
		// Pattern para la posicion
		if(positionFlag){
			patterns.add(PositionPattern + "\\d*");
		}
		// Pattern para el tiempo
		if(timeFlag){
			patterns.add( TimePattern + "\\d*.tif");			
		}
		return patterns;
	}

	public boolean isPositionFlag() {
		return positionFlag;
	}

	public void setPositionFlag(boolean positionFlag) {
		this.positionFlag = positionFlag;
	}

	public boolean isTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(boolean timeFlag) {
		this.timeFlag = timeFlag;
	}

	public boolean isSeparatorFlag() {
		return separatorFlag;
	}

	public void setSeparatorFlag(boolean separatorFlag) {
		this.separatorFlag = separatorFlag;
	}
	
}
