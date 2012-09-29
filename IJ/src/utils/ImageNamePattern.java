package utils;

import java.util.ArrayList;
import java.util.List;

import utils.tree.PositionImage;
import utils.tree.TimeImage;

public class ImageNamePattern {

	private static ImageNamePattern instance;
	
	private String BrightfieldChannelPattern;
	private String FluorChannelPattern;
	private String PositionPattern;
	private boolean timeFlag;
	private String TimePattern;
	private String separator;
	private String extension;

	public ImageNamePattern(){
		BrightfieldChannelPattern = "BF";
		FluorChannelPattern = "FP";
		PositionPattern = "Position";
		timeFlag = true;
		TimePattern = "time_";
		separator = "_";
		extension = ".tif";
		
	}
	
	public static ImageNamePattern getInstance(){
		if(instance == null){
			instance = new ImageNamePattern();
		}
		return instance;
	}
	
	public String generateImageName(PositionImage image){
		String name = "";
		if(image.isEmpty()){
			name += "EMPTY_";
		}
		name += image.getChannel();
		name += separator;
		name += (PositionPattern + image.getPositionId());
		name += separator;
		if(image instanceof TimeImage){
			name += (TimePattern + ((TimeImage)image).getTimeId());
		}
		name += extension;
		if(image.isOut()){
			name += (".out" + extension);
		}
		return name;
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
		timeFlag = true;
		TimePattern = "time_";
		separator = "_";
	}
	
	public List<String> generatePatternList(){
		//TODO: Ver como validar los tipos de canales de fluor
		List<String> patterns = new ArrayList<String>();
		// Pattern para los fields
		patterns.add("("+ BrightfieldChannelPattern +"|([A-Z])" + FluorChannelPattern + ")");
		// Pattern para la posicion
			patterns.add(separator + PositionPattern + "\\d*");
		// Pattern para el tiempo
		if(timeFlag){
			patterns.add(separator + TimePattern + "\\d*");	
		}
		patterns.add(extension +"(|.out"+extension+")");

		return patterns;
	}

	public boolean isTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(boolean timeFlag) {
		this.timeFlag = timeFlag;
	}

}
