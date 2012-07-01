package utils;

public class ImageNode {

	PositionNode position;
	TimeNode time;
	Channel channel;
	String imageName;
	
	
	public ImageNode(PositionNode position,TimeNode time,String name){
		this.position = position;
		this.time = time;
		this.imageName = name;
		this.channel = Channel.valueOf(name.substring(0,3));
	}


	public PositionNode getPosition() {
		return position;
	}


	public void setPosition(PositionNode position) {
		this.position = position;
	}


	public TimeNode getTime() {
		return time;
	}


	public void setTime(TimeNode time) {
		this.time = time;
	}


	public Channel getChannel() {
		return channel;
	}


	public void setChannel(Channel channel) {
		this.channel = channel;
	}


	public String getImageName() {
		return imageName;
	}


	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	
}
