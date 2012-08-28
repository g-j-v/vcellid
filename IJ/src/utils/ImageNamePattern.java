package utils;

import java.util.ArrayList;
import java.util.List;

public class ImageNamePattern {

	private static ImageNamePattern instance;
	
	private String BrightfieldChannelPattern;
	private String FluorChannelPattern;
	private String PositionPattern;
	private String TimePattern;
	private String separator;

	public ImageNamePattern(){
		BrightfieldChannelPattern = "BF";
		FluorChannelPattern = "FP";
		PositionPattern = "Position";
		TimePattern = "time_";
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
		PositionPattern = "Position";
		TimePattern = "time_";
		separator = "_";
	}
	
	public List<String> generatePatternList(){
		//TODO: Ver como validar los tipos de canales de fluor
		List<String> patterns = new ArrayList<String>();
		// Pattern para los fields
		patterns.add("("+ BrightfieldChannelPattern +"|CFP|GFP|YFP)");
		// Pattern para la posicion
		patterns.add(PositionPattern + "\\d*");
		// Pattern para el tiempo
		patterns.add( TimePattern + "\\d*.tif");

		return patterns;
	}
	
}
