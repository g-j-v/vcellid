/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CellId;

import ij.io.OpenDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import panels.PicturePanel;

import utils.Finder;
import utils.TreeGenerator;

/**
 * 
 * @author alejandropetit
 */
public class LoadImages extends ij.plugin.frame.PlugInFrame implements ActionListener{

	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTree jTree1;
	PicturePanel picturePanel;
	

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
		patterns.add("(BF|CFP|YFP)");
		// Pattern para la posicion
		patterns.add("Position\\d*");
		// Pattern para el tiempo
		patterns.add("time_\\d*.tif");
		
		Finder finder = new Finder(patterns);
//		Finder finder = new Finder(new File(
//				"/Volumes/APETIT/2004-11-11-TCY3154-inhibitor-effect/"),
//				patterns);
		
		picturePanel = new PicturePanel(null);
	    // Preferred height is irrelevant, since using WEST region
		picturePanel.setPreferredSize(new Dimension(400, 0));
		picturePanel.setBorder(BorderFactory.createLineBorder (Color.blue, 2));
		picturePanel.setBackground(Color.white);
	    add(picturePanel, BorderLayout.EAST);

		TreeGenerator treeGenerator = new TreeGenerator(finder, file);
		
		jTree1 = treeGenerator.generateTree(picturePanel);
		jScrollPane1 = new javax.swing.JScrollPane();

		jTree1.setName("jTree1"); // NOI18N
		jScrollPane1.setName("jScrollPane1"); // NOI18N
		jScrollPane1.setBorder(BorderFactory.createLineBorder (Color.blue, 2));
		jScrollPane1.setViewportView(jTree1);
		jScrollPane1.setPreferredSize(new Dimension(300,300));
		add(jScrollPane1);

		
	    
	    JPanel buttonsPanel = new JPanel();
	    buttonsPanel.setPreferredSize(new Dimension(800, 100));
	    buttonsPanel.setBorder(BorderFactory.createLineBorder (Color.blue, 2));
	    buttonsPanel.setBackground(Color.white);
	    buttonsPanel.add(new JButton("Previous"));
	    buttonsPanel.add(new JButton("Next"));
	    add(buttonsPanel, BorderLayout.SOUTH);
	    
	    
		setSize(800, 500);
		show();
	}
}
