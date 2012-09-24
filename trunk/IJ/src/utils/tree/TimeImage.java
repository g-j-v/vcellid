package utils.tree;

public class TimeImage extends PositionImage{

	private String timeId;
	
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
