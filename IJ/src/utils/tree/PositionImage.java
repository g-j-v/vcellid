package utils.tree;

import javax.naming.directory.InvalidAttributesException;

import cellid.image.LoadImagesPatterns;

import utils.ImageNamePattern;

public class PositionImage {

	private String channel;
	private String positionId;
	private boolean out;
	private boolean empty;
	
	public PositionImage(String channel, String positionId, boolean out, boolean empty){
		this.channel = channel.toUpperCase();
		setPositionId(positionId);
		this.out = out;
		this.empty = empty;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		try{
			Integer.valueOf(positionId);
		}catch(NumberFormatException e){
			throw e;
		}
		this.positionId = positionId;
	}

	public boolean isOut() {
		return out;
	}

	public void setOut(boolean out) {
		this.out = out;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
	
	public boolean isBF(){
		return channel.equals(ImageNamePattern.getInstance().getBrightfieldChannelPattern());
	}
	
	public boolean isFL(){
		return channel.substring(1, 3).equals(ImageNamePattern.getInstance().getFluorChannelPattern());
	}
	
	@Override
	public String toString(){
		return ImageNamePattern.getInstance().generateImageName(this);
	}
	
}
