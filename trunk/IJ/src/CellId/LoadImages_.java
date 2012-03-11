/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CellId;

import ij.io.OpenDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import utils.Finder;

/**
 * 
 * @author alejandropetit
 */
public class LoadImages_ extends ij.plugin.frame.PlugInFrame {

	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTree jTree1;

	public LoadImages_() {
		super("LoadImages");

	}

	public void run(String arg) {

		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(LoadImages_.this);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File file = fc.getSelectedFile();

		List<String> patterns = new ArrayList<String>();
		// Pattern para los fields
		patterns.add("BF|CFP|YFP");
		// Pattern para la posicion
		patterns.add("Position\\d{1,}");
		// Pattern para el tiempo
		patterns.add("time_\\d{3}\\.tif");
		
		Finder finder = new Finder(file, patterns);
//		Finder finder = new Finder(new File(
//				"/Volumes/APETIT/2004-11-11-TCY3154-inhibitor-effect/"),
//				patterns);

		jTree1 = finder.generateTree();
		jScrollPane1 = new javax.swing.JScrollPane();

		jTree1.setName("jTree1"); // NOI18N
		jScrollPane1.setName("jScrollPane1"); // NOI18N

		jScrollPane1.setViewportView(jTree1);

		add(jScrollPane1);
		setSize(1000, 600);
		show();
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
}