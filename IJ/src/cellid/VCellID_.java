/**
 * Main class for VCellId plugin for ImageJ
 */

/**
 * @author Alejandro Petit - Gisela De La Villa
 */

package cellid;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import cellid.help.Help;
import cellid.image.ImagesSetup;
import cellid.image.LoadImages;
import cellid.image.LoadImagesPatterns;
import cellid.path.CellIdPath;


import utils.run.CellIdRunner;

public class VCellID_ extends ij.plugin.frame.PlugInFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * VCellID buttons
	 */
	JButton cellIdPathButton;
//	JButton loadImagesButton;
	JButton imagesSetupButton;
	JButton imagesNameButton;
	JButton helpButton;
//	JButton segmentationButton;
	
	
	private static int BUTTON_WIDTH = 150;
	private static int BUTTON_HEIGHT = 30;
	private static int BUTTON_SPACING = 10;

	/**
	 * Default constructor
	 */
	public VCellID_() {
		super("VCellId");
	}

	public void run(String arg) {

		//Verificamos el tipo de sistema operativo
		if(isWindows()){
			CellIdRunner.getInstance().setCellIdPath("C:\\VCell-ID\\bin\\cell.exe");			
		}else if(isMac()){
			CellIdRunner.getInstance().setCellIdPath("/opt/local/bin/cell");
		}else if(isUnix()){
			CellIdRunner.getInstance().setCellIdPath("");
		}

		cellIdPathButton = new JButton("CellID Path");
		cellIdPathButton.addActionListener(new CellIdPath());
//		cellIdPathButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		
//		loadImagesButton = new JButton("Load Images");
//		loadImagesButton.addActionListener(new LoadImages());
//		loadImagesButton.setBounds(cellIdPathButton.getX(), cellIdPathButton.getY()+BUTTON_HEIGHT+BUTTON_SPACING,BUTTON_WIDTH, BUTTON_HEIGHT);
		
		imagesSetupButton = new JButton("Images Setup");
		imagesSetupButton.addActionListener(new ImagesSetup());
//		imagesSetupButton.setBounds(loadImagesButton.getX(), loadImagesButton.getY()+BUTTON_HEIGHT+BUTTON_SPACING,BUTTON_WIDTH, BUTTON_HEIGHT);
		
		imagesNameButton = new JButton("Images Name");
		imagesNameButton.addActionListener(new LoadImagesPatterns());
//		imagesNameButton.addActionListener();

		helpButton = new JButton("Help");
		helpButton.addActionListener(new Help());
//		imagesNameButton.addActionListener();
		
//		segmentationButton = new JButton("Segmentation");
//		segmentationButton.addActionListener(new Segmentation());
//		segmentationButton.setBounds(imagesSetupButton.getX(), imagesSetupButton.getY()+BUTTON_HEIGHT+BUTTON_SPACING,BUTTON_WIDTH, BUTTON_HEIGHT);

		// For layout purposes, put the buttons in a separate panel
		JPanel buttonPanel = new JPanel(); // use FlowLayout
		
		Box buttonBox = Box.createVerticalBox();
		buttonBox.add(cellIdPathButton);
		buttonBox.add(Box.createVerticalStrut(BUTTON_SPACING));
//		buttonBox.add(loadImagesButton);
//		buttonBox.add(Box.createVerticalStrut(BUTTON_SPACING));
		buttonBox.add(imagesSetupButton);
		buttonBox.add(Box.createVerticalStrut(BUTTON_SPACING));
		buttonBox.add(imagesNameButton);
		buttonBox.add(Box.createVerticalStrut(BUTTON_SPACING));
		buttonBox.add(helpButton);
		buttonBox.add(Box.createVerticalStrut(BUTTON_SPACING));
//		buttonBox.add(segmentationButton);

		buttonPanel.add(buttonBox);
		// Add the buttons and the log to this panel.
		add(buttonPanel, BorderLayout.PAGE_START);
		setSize(20+BUTTON_WIDTH, 6 *BUTTON_HEIGHT+5*BUTTON_SPACING);
		setResizable(false);
		setVisible(true);
	}
	
	/**
	 * 
	 * @return TRUE if the Operating System in which ImageJ is running is Windows.
	 */
	private static boolean isWindows() {
		 
		String os = System.getProperty("os.name").toLowerCase();
		// windows
		return (os.indexOf("win") >= 0);
 
	}
	
	/**
	 * 
	 * @return TRUE if the Operating System in which ImageJ is running is Mac OS.
	 */
	private static boolean isMac() {
		 
		String os = System.getProperty("os.name").toLowerCase();
		// Mac
		return (os.indexOf("mac") >= 0);
 
	}
 
	/**
	 * 
	 * @return TRUE if the Operating System in which ImageJ is running is Unix or linux.
	 */
	private static boolean isUnix() {
 
		String os = System.getProperty("os.name").toLowerCase();
		// linux or unix
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);
 
	}
}