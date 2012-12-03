/**
 * Main class for VCellId plugin for ImageJ
 */

/**
 * @author Alejandro Petit - Gisela De La Villa
 */

package cellid;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import cellid.help.Help;
import cellid.image.ImagesSetup;
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
	JButton imagesSetupButton;
	JButton imagesNameButton;
	JButton helpButton;
	
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
		
		
		//Creamos los botones y les asignamos los ActionListeners
		
		cellIdPathButton = new JButton("CellID Path");
		cellIdPathButton.addActionListener(new CellIdPath());
		
		imagesNameButton = new JButton("Load Images");
		imagesNameButton.addActionListener(new LoadImagesPatterns());
		
		imagesSetupButton = new JButton("Images Setup");
		imagesSetupButton.addActionListener(new ImagesSetup());

		helpButton = new JButton("Help");
		helpButton.addActionListener(new Help());

		//Creamos el contenedor para los botones y los agregamos
		JPanel buttonPanel = new JPanel(); // use FlowLayout
		buttonPanel.setLayout(new GridLayout(0, 1, 0, BUTTON_SPACING));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(BUTTON_SPACING,BUTTON_SPACING,BUTTON_SPACING,BUTTON_SPACING));
		
		buttonPanel.add(cellIdPathButton);
		buttonPanel.add(imagesSetupButton);
		buttonPanel.add(imagesNameButton);
		buttonPanel.add(helpButton);
		
		add(buttonPanel, BorderLayout.CENTER);
		
		setResizable(false);
		pack();
		setVisible(true);
	}
	
	/**
	 * 
	 * @return TRUE if the Operating System in which ImageJ is running is Windows.
	 */
	public static boolean isWindows() {
		 
		String os = System.getProperty("os.name").toLowerCase();
		// windows
		return (os.indexOf("win") >= 0);
 
	}
	
	/**
	 * 
	 * @return TRUE if the Operating System in which ImageJ is running is Mac OS.
	 */
	public static boolean isMac() {
		 
		String os = System.getProperty("os.name").toLowerCase();
		// Mac
		return (os.indexOf("mac") >= 0);
 
	}
 
	/**
	 * 
	 * @return TRUE if the Operating System in which ImageJ is running is Unix or linux.
	 */
	public static boolean isUnix() {
 
		String os = System.getProperty("os.name").toLowerCase();
		// linux or unix
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);
 
	}
}
