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
	/**
	 * private void createNodes(DefaultMutableTreeNode top) {
	 * 
	 * DefaultMutableTreeNode category = null; DefaultMutableTreeNode book =
	 * null;
	 * 
	 * category = new DefaultMutableTreeNode("Books for Java Programmers");
	 * top.add(category);
	 * 
	 * //original Tutorial book = new DefaultMutableTreeNode(
	 * "The Java Tutorial: A Short Course on the Basics"); category.add(book);
	 * 
	 * //Tutorial Continued book = new DefaultMutableTreeNode(
	 * "The Java Tutorial Continued: The Rest of the JDK"); category.add(book);
	 * 
	 * //JFC Swing Tutorial book = new DefaultMutableTreeNode(
	 * "The JFC Swing Tutorial: A Guide to Constructing GUIs");
	 * category.add(book);
	 * 
	 * //...add more books for programmers...
	 * 
	 * category = new DefaultMutableTreeNode("Books for Java Implementers");
	 * top.add(category);
	 * 
	 * //VM book = new
	 * DefaultMutableTreeNode("The Java Virtual Machine Specification");
	 * category.add(book);
	 * 
	 * //Language Spec book = new
	 * DefaultMutableTreeNode("The Java Language Specification");
	 * category.add(book); }
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(CellIdPath.this);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return;
		}

		
		String url = fc.getSelectedFile().getAbsolutePath();

		jScrollPane1 = new javax.swing.JScrollPane();

		jScrollPane1.setName("jScrollPane1"); // NOI18N
		
		jScrollPane1.add(new TextArea(url));

		add(jScrollPane1);
		setSize(200, 100);
		show();
	}
}
