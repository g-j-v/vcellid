package CellId;

import ij.IJ;
import ij.gui.ImageCanvas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

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

		final File file = fc.getSelectedFile();

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
		picturePanel.setPreferredSize(new Dimension(600, 600));
		picturePanel.setBorder(BorderFactory.createLineBorder (Color.blue, 2));
		picturePanel.setBackground(Color.white);
	    add(picturePanel, BorderLayout.EAST);

		TreeGenerator treeGenerator = new TreeGenerator(finder, file);
		
		jTree1 = treeGenerator.generateTree(picturePanel);
		jTree1.setExpandsSelectedPaths(true);
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

	    JButton previousPositionButton = new JButton("Previous Position");
	    previousPositionButton.setPreferredSize(new Dimension(150,20));
	    previousPositionButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(jTree1.getLastSelectedPathComponent() != null){
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
					DefaultMutableTreeNode previousNode;
					if(node.isLeaf()){
						DefaultMutableTreeNode positionParent = (DefaultMutableTreeNode) node.getParent().getParent();
						previousNode = positionParent.getPreviousSibling();
					}else if(node.getUserObject().toString().toLowerCase().contains("time")){
						DefaultMutableTreeNode positionParent = (DefaultMutableTreeNode) node.getParent();
						previousNode = positionParent.getPreviousSibling();
					}else if(node.getUserObject().toString().toLowerCase().contains("position")){
						previousNode = node.getPreviousSibling();
					}else{
						return;
					}
					if(previousNode != null ){
						jTree1.setSelectionPath(new TreePath(previousNode));
						System.out.println(file.getAbsolutePath() + "/" +previousNode.getUserObject().toString());
					}
				}
			}
		});
	    buttonsPanel.add(previousPositionButton);
	    
	    JButton previousTimeButton = new JButton("Previous Time");
	    previousTimeButton.setPreferredSize(new Dimension(150,20));
	    previousTimeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(jTree1.getLastSelectedPathComponent() != null){
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
					DefaultMutableTreeNode previousNode;
					if(node.isLeaf()){
						DefaultMutableTreeNode parent = (DefaultMutableTreeNode)node.getParent();
						previousNode = parent.getPreviousSibling();
					}else if(node.getUserObject().toString().toLowerCase().contains("time")){
						previousNode = node.getPreviousSibling();
					}else if(node.getUserObject().toString().toLowerCase().contains("position")){
						previousNode = (DefaultMutableTreeNode) node.getLastChild();
					}else{
						return;
					}
					if(previousNode != null){
						jTree1.setSelectionPath(new TreePath(previousNode));
						System.out.println(file.getAbsolutePath() + "/" +previousNode.getUserObject().toString());
					}
				}
			}
		});
	    buttonsPanel.add(previousTimeButton);
	    
	    JButton previousButton = new JButton("Previous Image");
	    previousButton.setPreferredSize(new Dimension(150,20));
	    previousButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(jTree1.getLastSelectedPathComponent() != null){
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
					DefaultMutableTreeNode previousNode = node.getPreviousSibling();
					if(previousNode != null && previousNode.isLeaf()){
						TreePath path = new TreePath(previousNode);
						jTree1.setSelectionPath(path);
						jTree1.scrollPathToVisible(path);
						System.out.println(file.getAbsolutePath() + "/" +previousNode.getUserObject().toString());
						picturePanel.setImage(new ImageCanvas(IJ.openImage(file.getAbsolutePath() + "/" +previousNode.getUserObject().toString())));
						picturePanel.getImage().paint(picturePanel.getGraphics());
					}
				}
			}
		});
	    buttonsPanel.add(previousButton);
	    
	    JButton nextButton = new JButton("Next Image");
	    nextButton.setPreferredSize(new Dimension(150,20));
	    nextButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(jTree1.getLastSelectedPathComponent() != null){
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
					DefaultMutableTreeNode nextNode = node.getNextSibling();
					if(nextNode != null && nextNode.isLeaf()){
						TreePath path = new TreePath(nextNode);
						jTree1.setSelectionPath(path);
						jTree1.scrollPathToVisible(path);
						System.out.println(file.getAbsolutePath() + "/" +nextNode.getUserObject().toString());
						picturePanel.setImage(new ImageCanvas(IJ.openImage(file.getAbsolutePath() + "/" + nextNode.getUserObject().toString())));
						picturePanel.getImage().paint(picturePanel.getGraphics());
					}
				}
			}
		});
	    buttonsPanel.add(nextButton);

	    JButton nextTimeButton = new JButton("Next Time");
	    nextTimeButton.setPreferredSize(new Dimension(150,20));
	    nextTimeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(jTree1.getLastSelectedPathComponent() != null){
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
					DefaultMutableTreeNode nextNode;
					if(node.isLeaf()){
						DefaultMutableTreeNode parent = (DefaultMutableTreeNode)node.getParent();
						nextNode = parent.getNextSibling();
					}else if(node.getUserObject().toString().toLowerCase().contains("time")){
						nextNode = node.getNextSibling();
					}else if(node.getUserObject().toString().toLowerCase().contains("position")){
						nextNode = (DefaultMutableTreeNode) node.getFirstChild();
					}else{
						return;
					}
					if(nextNode != null){
						jTree1.setSelectionPath(new TreePath(nextNode));
						System.out.println(file.getAbsolutePath() + "/" +nextNode.getUserObject().toString());
					}
				}
			}
		});
	    buttonsPanel.add(nextTimeButton);
	    
	    JButton nextPositionButton = new JButton("Next Position");
	    nextPositionButton.setPreferredSize(new Dimension(150,20));
	    nextPositionButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(jTree1.getLastSelectedPathComponent() != null){
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
					DefaultMutableTreeNode nextNode;
					if(node.isLeaf()){
						DefaultMutableTreeNode positionParent = (DefaultMutableTreeNode) node.getParent().getParent();
						nextNode = positionParent.getNextSibling();
					}else if(node.getUserObject().toString().toLowerCase().contains("time")){
						DefaultMutableTreeNode positionParent = (DefaultMutableTreeNode) node.getParent();
						nextNode = positionParent.getNextSibling();
					}else if(node.getUserObject().toString().toLowerCase().contains("position")){
						nextNode = node.getNextSibling();
					}else{
						return;
					}
					if(nextNode != null){
						jTree1.setSelectionPath(new TreePath(nextNode));
						System.out.println(file.getAbsolutePath() + "/" +nextNode.getUserObject().toString());
					}
				}
			}
		});
	    buttonsPanel.add(nextPositionButton);
	    
	    add(buttonsPanel, BorderLayout.SOUTH);
	    
	    
		setSize(1000, 700);
		setResizable(false);
		show();
	}
}