package CellId;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import utils.CellIdRunner;

public class VCellID_ extends ij.plugin.frame.PlugInFrame {

	// Botones del VCellId original

	JButton cellIdPathButton;
	JButton loadImagesButton;
	JButton imagesSetupButton;
	JButton segmentationButton;
	
	
	private static int BUTTON_WIDTH = 150;
	private static int BUTTON_HEIGHT = 30;
	private static int BUTTON_SPACING = 10;

	public VCellID_() {
		super("VCellId");
	}

	public void run(String arg) {

		CellIdRunner.getInstance().setCellIdPath("C:\\VCell-ID\\bin\\cell.exe");
		cellIdPathButton = new JButton("CellID Path");
		cellIdPathButton.addActionListener(new CellIdPath());
//		cellIdPathButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		
		loadImagesButton = new JButton("Load Images");
		loadImagesButton.addActionListener(new LoadImages());
//		loadImagesButton.setBounds(cellIdPathButton.getX(), cellIdPathButton.getY()+BUTTON_HEIGHT+BUTTON_SPACING,BUTTON_WIDTH, BUTTON_HEIGHT);
		
		imagesSetupButton = new JButton("Images Setup");
		imagesSetupButton.addActionListener(new ImagesSetup());
//		imagesSetupButton.setBounds(loadImagesButton.getX(), loadImagesButton.getY()+BUTTON_HEIGHT+BUTTON_SPACING,BUTTON_WIDTH, BUTTON_HEIGHT);
		
//		segmentationButton = new JButton("Segmentation");
//		segmentationButton.addActionListener(new Segmentation());
//		segmentationButton.setBounds(imagesSetupButton.getX(), imagesSetupButton.getY()+BUTTON_HEIGHT+BUTTON_SPACING,BUTTON_WIDTH, BUTTON_HEIGHT);

		// For layout purposes, put the buttons in a separate panel
		JPanel buttonPanel = new JPanel(); // use FlowLayout
		
		Box buttonBox = Box.createVerticalBox();
		buttonBox.add(cellIdPathButton);
		buttonBox.add(Box.createVerticalStrut(BUTTON_SPACING));
		buttonBox.add(loadImagesButton);
		buttonBox.add(Box.createVerticalStrut(BUTTON_SPACING));
		buttonBox.add(imagesSetupButton);
		buttonBox.add(Box.createVerticalStrut(BUTTON_SPACING));
//		buttonBox.add(segmentationButton);

		buttonPanel.add(buttonBox);
		// Add the buttons and the log to this panel.
		add(buttonPanel, BorderLayout.PAGE_START);
		setSize(10+BUTTON_WIDTH, 4*BUTTON_HEIGHT+5*BUTTON_SPACING);
		show();
	}
}
