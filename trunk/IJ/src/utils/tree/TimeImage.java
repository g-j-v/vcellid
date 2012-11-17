package utils.tree;

public class TimeImage extends PositionImage{

	private String timeId;
	
	/**
	 * Constructor used to define a time image.
	 * @param channel	the channel of the image
	 * @param positionId	the position to which this image belongs
	 * @param out	if it is an output image
	 * @param timeId	the time of the image
	 * @param empty	if it is a fake image, used to complete the tree.
	 */
	public TimeImage(String channel, String positionId, boolean out, String timeId, boolean empty) {
		super(channel, positionId, out,empty);
		this.setTimeId(timeId);
	}

	public String getTimeId() {
		return timeId;
	}

	public void setTimeId(String timeId) {
		try{
			Integer.valueOf(timeId);
		}catch(NumberFormatException e){
			throw e;
		}
		this.timeId = timeId;
	}

}
