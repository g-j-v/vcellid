/**
 * Class to represent an ImageNode of the tree
 */


package utils;

public class ImageNode {

	PositionNode position;
	TimeNode time;
	Channel channel;
	String imageName;
	boolean fake;
	
	/**
	 * Constructor
	 * @param position to which this image belongs
	 * @param time to which this image belongs
	 * @param name of the image
	 * @param fake if there is no real image
	 */
	public ImageNode(PositionNode position,TimeNode time,String name,boolean fake){
		this.position = position;
		this.time = time;
		this.imageName = name;
		this.channel = channel();
		this.fake = fake;
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

	private Channel channel(){
		if(imageName.substring(0,2).toLowerCase().equals("BF")){
			return Channel.BF;
		}else if(imageName.substring(0,3).toLowerCase().equals("YFP")){
			return Channel.YFP;
		}else if(imageName.substring(0,3).toLowerCase().equals("CFP")){
			return Channel.CFP;
		}else{
			return null;
		}
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
	
	public boolean isFake() {
		return fake;
	}
	
	public void setFake(boolean fake) {
		this.fake = fake;
	}

	@Override
	public String toString(){
		return imageName;
	}
}
