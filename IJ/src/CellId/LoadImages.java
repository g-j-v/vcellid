/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CellId;

import ij.io.OpenDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import utils.Finder;
import utils.TreeGenerator;

/**
 * 
 * @author alejandropetit
 */
public class LoadImages extends ij.plugin.frame.PlugInFrame implements ActionListener{

	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTree jTree1;

	public LoadImages() {
		super("Load Images");

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
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(LoadImages.this);
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
		
		Finder finder = new Finder(patterns);
//		Finder finder = new Finder(new File(
//				"/Volumes/APETIT/2004-11-11-TCY3154-inhibitor-effect/"),
//				patterns);

		TreeGenerator treeGenerator = new TreeGenerator(finder, file);
		
		jTree1 = treeGenerator.generateTree();
		jScrollPane1 = new javax.swing.JScrollPane();

		jTree1.setName("jTree1"); // NOI18N
		jScrollPane1.setName("jScrollPane1"); // NOI18N

		jScrollPane1.setViewportView(jTree1);

		add(jScrollPane1);
		setSize(1000, 600);
		show();
	}
}
