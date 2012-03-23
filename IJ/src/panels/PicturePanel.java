package panels;

import ij.gui.ImageCanvas;

import javax.swing.JPanel;

public class PicturePanel extends JPanel{
	
	private ImageCanvas image;

	public PicturePanel(ImageCanvas image){
		this.setImage(image);
		
	}

	

	public ImageCanvas getImage() {
		return image;
	}

	public void setImage(ImageCanvas image) {
		this.image = image;
	}

	

	
}
