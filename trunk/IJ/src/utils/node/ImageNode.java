/**
 * Class to represent an ImageNode of the tree
 */


package utils.node;

import java.util.List;

import utils.ImageNamePattern;
import utils.tree.PositionImage;


public class ImageNode {

	PositionNode position;
	TimeNode time;
	PositionImage image;
	boolean fake;
	
	/**
	 * Constructor
	 * @param position to which this image belongs
	 * @param time to which this image belongs
	 * @param name of the image
	 * @param fake if there is no real image
	 */
	public ImageNode(PositionNode position,TimeNode time,PositionImage image,boolean fake){
		this.position = position;
		this.time = time;
		this.image = image;
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
	
	
	public PositionImage getImage() {
		return image;
	}


	public void setImage(PositionImage image) {
		this.image = image;
	}

	public boolean isFake() {
		return fake;
	}
	
	public void setFake(boolean fake) {
		this.fake = fake;
	}

	@Override
	public String toString(){
		return ImageNamePattern.getInstance().generateImageName(image);
	}
}
