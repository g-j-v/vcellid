
package CellId;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import utils.CellIdRunner;

/**
 * Class used to select CellId path for running
 * @author Alejandro Petit - Gisela De La Villa
 */
public class CellIdPath extends ij.plugin.frame.PlugInFrame implements ActionListener{
	
	/**
	 * Default Constructor
	 */
	public CellIdPath() {
		super("CellID Path");
	}

	/**
	 * When called, opens a file chooser and sets the location of the CellId
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		JFileChooser fc = new JFileChooser();
//		System.out.println(CellIdRunner.getInstance().getCellIdPath());
		fc.setSelectedFile(new File(CellIdRunner.getInstance().getCellIdPath()));
		fc.addChoosableFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				return ".exe";
			}
			
			@Override
			public boolean accept(File f) {
				String filename = f.getName();
				return filename.endsWith(".exe");
			}
		});
		int returnVal = fc.showOpenDialog(CellIdPath.this);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return;
		}

		CellIdRunner.getInstance().setCellIdPath(fc.getSelectedFile().getAbsolutePath());
		
	}
}
