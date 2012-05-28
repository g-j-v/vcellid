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
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import panels.PicturePanel;
import utils.CellIdRunner;
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
	private String BAR = System.getProperty("file.separator");

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

		Box buttonsBox = Box.createVerticalBox();
		
		Box rootBox = Box.createHorizontalBox();
		Box channelBox = Box.createHorizontalBox();
		Box timeBox = Box.createHorizontalBox();
		Box positionBox = Box.createHorizontalBox();

		buttonsPanel.add(buttonsBox);
	    JButton previousPositionButton = new JButton("Previous Position");
	    previousPositionButton.setPreferredSize(new Dimension(150,20));
	    previousPositionButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(jTree1.getLastSelectedPathComponent() != null){
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
					DefaultMutableTreeNode previousNode;
					if(node.isLeaf()){
						int leafIndex = node.getParent().getIndex(node);
						int timeIndex = node.getParent().getParent().getIndex(node.getParent()); 
						DefaultMutableTreeNode positionParent = (DefaultMutableTreeNode) node.getParent().getParent();
						previousNode = positionParent.getPreviousSibling();
						if(previousNode == null){
							previousNode = (DefaultMutableTreeNode)((DefaultMutableTreeNode)positionParent.getParent()).getLastChild();
						}
						previousNode = (DefaultMutableTreeNode) previousNode.getChildAt(timeIndex).getChildAt(leafIndex);
					}else if(node.getUserObject().toString().toLowerCase().contains("time")){
						int timeIndex = node.getParent().getIndex(node);
						DefaultMutableTreeNode positionParent = (DefaultMutableTreeNode) node.getParent();
						previousNode = positionParent.getPreviousSibling();
						if(previousNode == null){
							previousNode = (DefaultMutableTreeNode)((DefaultMutableTreeNode)positionParent.getParent()).getLastChild();
						}
						previousNode = (DefaultMutableTreeNode) previousNode.getChildAt(timeIndex);
					}else if(node.getUserObject().toString().toLowerCase().contains("position")){
						previousNode = node.getPreviousSibling();
						if(previousNode == null){
							previousNode = (DefaultMutableTreeNode)((DefaultMutableTreeNode)node.getParent()).getLastChild();
						}
					}else if(node.isRoot()){
						previousNode = (DefaultMutableTreeNode)node.getLastChild();
					}else{
						return;
					}
					if(previousNode != null ){
						TreePath path = new TreePath(((DefaultTreeModel)jTree1.getModel()).getPathToRoot(previousNode));
						jTree1.setSelectionPath(path);
						jTree1.scrollPathToVisible(path);
						System.out.println(file.getAbsolutePath() + BAR +previousNode.getUserObject().toString());
					}
				}
			}
		});
//	    buttonsPanel.add(previousPositionButton);
	    positionBox.add(previousPositionButton);
	    
	    JButton previousTimeButton = new JButton("Previous Time");
	    previousTimeButton.setPreferredSize(new Dimension(150,20));
	    previousTimeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(jTree1.getLastSelectedPathComponent() != null){
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
					DefaultMutableTreeNode previousNode;
					if(node.isLeaf()){
						int leafIndex = node.getParent().getIndex(node);
						DefaultMutableTreeNode parent = (DefaultMutableTreeNode)node.getParent();
						previousNode = parent.getPreviousSibling();
						if(previousNode == null){
							previousNode = (DefaultMutableTreeNode)((DefaultMutableTreeNode)parent.getParent()).getLastChild();
						}
						previousNode=(DefaultMutableTreeNode) previousNode.getChildAt(leafIndex);
					}else if(node.getUserObject().toString().toLowerCase().contains("time")){
						previousNode = node.getPreviousSibling();
						if(previousNode == null){
							previousNode = (DefaultMutableTreeNode)((DefaultMutableTreeNode)node.getParent()).getLastChild();
						}
					}else if(node.getUserObject().toString().toLowerCase().contains("position")){
						previousNode = (DefaultMutableTreeNode) node.getLastChild();
					}else if(node.isRoot()){
						previousNode = (DefaultMutableTreeNode)((DefaultMutableTreeNode)node.getLastChild()).getLastChild();
					}else{
						return;
					}
					if(previousNode != null){
						TreePath path = new TreePath(((DefaultTreeModel)jTree1.getModel()).getPathToRoot(previousNode));
						jTree1.setSelectionPath(path);
						jTree1.scrollPathToVisible(path);
						System.out.println(file.getAbsolutePath() + BAR +previousNode.getUserObject().toString());
					}
				}
			}
		});
//	    buttonsPanel.add(previousTimeButton);
	    timeBox.add(previousTimeButton);
	    
	    JButton previousButton = new JButton("Previous Channel");
	    previousButton.setPreferredSize(new Dimension(150,20));
	    previousButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
				DefaultMutableTreeNode previousNode = null;
				if(node == null){
					previousNode = (DefaultMutableTreeNode)((DefaultMutableTreeNode)((DefaultMutableTreeNode)((DefaultMutableTreeNode)jTree1.getModel().getRoot()).getLastChild()).getLastChild()).getLastChild();
				}else if(node.isLeaf()){
					previousNode = node.getPreviousSibling();
					if(previousNode==null){
						previousNode = (DefaultMutableTreeNode)((DefaultMutableTreeNode) node.getParent()).getLastChild();
					}
				}else if(node.getUserObject().toString().toLowerCase().contains("time")){
					previousNode = (DefaultMutableTreeNode)node.getPreviousSibling().getLastChild();
				}else if(node.getUserObject().toString().toLowerCase().contains("position")){
					previousNode = (DefaultMutableTreeNode)((DefaultMutableTreeNode)node.getLastChild()).getLastChild();
				}else if(node.isRoot()){
					previousNode = (DefaultMutableTreeNode)((DefaultMutableTreeNode)((DefaultMutableTreeNode)node.getLastChild()).getLastChild()).getLastChild();
				}else{
					return;
				}
				TreePath path = new TreePath(((DefaultTreeModel)jTree1.getModel()).getPathToRoot(previousNode));
				jTree1.setSelectionPath(path);
				jTree1.scrollPathToVisible(path);
				System.out.println(file.getAbsolutePath() + BAR +previousNode.getUserObject().toString());
				picturePanel.setImage(new ImageCanvas(IJ.openImage(file.getAbsolutePath() + BAR + previousNode.getUserObject().toString())));
				picturePanel.getImage().paint(picturePanel.getGraphics());
			}
		});
//	    buttonsPanel.add(previousButton);
	    channelBox.add(previousButton);
	    
	    JButton rootButton = new JButton("Root");
	    rootButton.setPreferredSize(new Dimension(150,20));
	    rootButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TreePath path = new TreePath(((DefaultTreeModel)jTree1.getModel()).getPathToRoot((TreeNode)jTree1.getModel().getRoot()));
				jTree1.setSelectionPath(path);
				jTree1.scrollPathToVisible(path);
			}
		});
//	    buttonsPanel.add(rootButton);
	    rootBox.add(rootButton);
	    
	    JButton nextButton = new JButton("Next Channel");
	    nextButton.setPreferredSize(new Dimension(150,20));
	    nextButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
				DefaultMutableTreeNode nextNode = null;
				if(node == null){
					nextNode = (DefaultMutableTreeNode)((DefaultMutableTreeNode)jTree1.getModel().getRoot()).getFirstChild().getChildAt(0).getChildAt(0);
				}else if(node.isLeaf()){
					nextNode = node.getNextSibling();
					if(nextNode==null){
						nextNode = (DefaultMutableTreeNode) node.getParent().getChildAt(0);
					}
				}else if(node.getUserObject().toString().toLowerCase().contains("time")){
					nextNode = (DefaultMutableTreeNode)node.getFirstChild();
				}else if(node.getUserObject().toString().toLowerCase().contains("position")){
					nextNode = (DefaultMutableTreeNode)node.getFirstChild().getChildAt(0);
				}else if(node.isRoot()){
					nextNode = (DefaultMutableTreeNode)node.getFirstChild().getChildAt(0).getChildAt(0);
				}else{
					return;
				}
				TreePath path = new TreePath(((DefaultTreeModel)jTree1.getModel()).getPathToRoot(nextNode));
				jTree1.setSelectionPath(path);
				jTree1.scrollPathToVisible(path);
				System.out.println(file.getAbsolutePath() + BAR +nextNode.getUserObject().toString());
				picturePanel.setImage(new ImageCanvas(IJ.openImage(file.getAbsolutePath() + BAR + nextNode.getUserObject().toString())));
				picturePanel.getImage().paint(picturePanel.getGraphics());

			}
		});
//	    buttonsPanel.add(nextButton);
	    channelBox.add(nextButton);

	    JButton nextTimeButton = new JButton("Next Time");
	    nextTimeButton.setPreferredSize(new Dimension(150,20));
	    nextTimeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
				DefaultMutableTreeNode nextNode = null;
				if(node == null){
					nextNode = (DefaultMutableTreeNode)((DefaultMutableTreeNode)jTree1.getModel().getRoot()).getFirstChild().getChildAt(0);
				}else if(node.isLeaf()){
					int position = node.getParent().getIndex(node);
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode)node.getParent();
					nextNode = (DefaultMutableTreeNode)parent.getNextSibling();
					if(nextNode==null){
						nextNode = (DefaultMutableTreeNode) parent.getParent().getChildAt(0);
					}
					nextNode = (DefaultMutableTreeNode)nextNode.getChildAt(position);
				}else if(node.getUserObject().toString().toLowerCase().contains("time")){
					nextNode = node.getNextSibling();
					if(nextNode==null){
						nextNode = (DefaultMutableTreeNode) node.getParent().getChildAt(0);
					}
				}else if(node.getUserObject().toString().toLowerCase().contains("position")){
					nextNode = (DefaultMutableTreeNode) node.getFirstChild();
				}else if (node.isRoot()){
					nextNode = (DefaultMutableTreeNode) node.getFirstChild().getChildAt(0);
				}else{
					return;
				}
				if(nextNode != null){
					TreePath path = new TreePath(((DefaultTreeModel)jTree1.getModel()).getPathToRoot(nextNode));
					jTree1.setSelectionPath(path);
					jTree1.scrollPathToVisible(path);
					System.out.println(file.getAbsolutePath() + BAR +nextNode.getUserObject().toString());
				}
			}
		});
//	    buttonsPanel.add(nextTimeButton);
	    timeBox.add(nextTimeButton);
	    
	    JButton nextPositionButton = new JButton("Next Position");
	    nextPositionButton.setPreferredSize(new Dimension(150,20));
	    nextPositionButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
					DefaultMutableTreeNode nextNode = null;
					if(node == null){
						nextNode = (DefaultMutableTreeNode)((DefaultMutableTreeNode)jTree1.getModel().getRoot()).getFirstChild();
					}else if(node.isLeaf()){
						int leafIndex = node.getParent().getIndex(node);
						int timeIndex = node.getParent().getParent().getIndex(node.getParent()); 
						DefaultMutableTreeNode positionParent = (DefaultMutableTreeNode) node.getParent().getParent();
						nextNode = positionParent.getNextSibling();
						if(nextNode==null){
							nextNode = (DefaultMutableTreeNode) positionParent.getParent().getChildAt(0);
						}
						nextNode = (DefaultMutableTreeNode) nextNode.getChildAt(timeIndex).getChildAt(leafIndex);
					}else if(node.getUserObject().toString().toLowerCase().contains("time")){
						int timeIndex = node.getParent().getIndex(node);
						DefaultMutableTreeNode positionParent = (DefaultMutableTreeNode) node.getParent();
						nextNode = positionParent.getNextSibling();
						if(nextNode==null){
							nextNode = (DefaultMutableTreeNode) positionParent.getParent().getChildAt(0);
						}
						nextNode = (DefaultMutableTreeNode) nextNode.getChildAt(timeIndex);
					}else if(node.getUserObject().toString().toLowerCase().contains("position")){
						nextNode = node.getNextSibling();
						if(nextNode==null){
							nextNode = (DefaultMutableTreeNode) node.getParent().getChildAt(0);
						}
					}else if(node.isRoot()){
						nextNode = (DefaultMutableTreeNode) node.getFirstChild();
					}else{
						return;
					}
					if(nextNode != null){
						TreePath path = new TreePath(((DefaultTreeModel)jTree1.getModel()).getPathToRoot(nextNode));
						jTree1.setSelectionPath(path);
						jTree1.scrollPathToVisible(path);
						System.out.println(file.getAbsolutePath() + BAR +nextNode.getUserObject().toString());
					}
				
			}
		});
//	    buttonsPanel.add(nextPositionButton);
	    positionBox.add(nextPositionButton);
	    
	    buttonsBox.add(rootBox);
	    buttonsBox.add(channelBox);
	    buttonsBox.add(timeBox);
	    buttonsBox.add(positionBox);
	    
	    buttonsPanel.add(buttonsBox);
	    
	    //////////////////////////////////////////////////////////////////////
	    /////////////////////FOR TEST/////////////////////////////////////////
	    JButton testOutputButton = new JButton("Test Output Generation");
	    testOutputButton.setPreferredSize(new Dimension(180,20));
	    testOutputButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {				
				Segmentation seg = new Segmentation(jTree1, file);
				seg.setVisible(true);
			}
		});
	    buttonsPanel.add(testOutputButton);
	    
	    /////////////////////////////////////////////////////////////////////
	    /////////////////////////////////////////////////////////////////////
	    add(buttonsPanel, BorderLayout.SOUTH);
	    
	    
		setSize(1200, 700);
		setResizable(false);
		show();
	}
}
