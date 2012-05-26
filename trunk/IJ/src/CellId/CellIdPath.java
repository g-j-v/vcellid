/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CellId;

import ij.io.OpenDialog;

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import utils.CellIdRunner;
import utils.Finder;

/**
 * 
 * @author alejandropetit
 */
public class CellIdPath extends ij.plugin.frame.PlugInFrame implements ActionListener{

	private javax.swing.JScrollPane jScrollPane1;
	
	public CellIdPath() {
		super("CellID Path");
	}

	public void run(String arg) {
	}

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
//		String url = fc.getSelectedFile().getAbsolutePath();
//
//		jScrollPane1 = new javax.swing.JScrollPane();
//
//		jScrollPane1.setName("jScrollPane1"); // NOI18N
//		
//		jScrollPane1.add(new TextArea(url));
//
//		add(jScrollPane1);
//		setSize(200, 100);
//		show();
	}
}
