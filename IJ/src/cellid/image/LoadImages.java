package cellid.image;

import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.ImageWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import utils.DisplayRangeObject;
import utils.ImageLoadingPaths;
import utils.tree.TreeGenerator;

/**
 * Class for tree presentation and navigation buttons
 * @author 
 */
public class LoadImages extends ij.plugin.frame.PlugInFrame implements ActionListener {

	/**
	 * Class Variables
	 */
	private JScrollPane jScrollPane1;
	private JTree jTree1;
	private TreeGenerator treeGenerator;
	private JCheckBox synchronize;

	private String BAR = System.getProperty("file.separator");

	/**
	 * Default constructor
	 */
	public LoadImages() {
		super("Load Images");
	}

	/**
	 * Call when called, performs the frame generation.
	 */
	public void run(){

		removeAll();

		//Select directory where images are located
//		JFileChooser fc = new JFileChooser();
//		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//		int returnVal = fc.showOpenDialog(LoadImages.this);
//		if (returnVal != JFileChooser.APPROVE_OPTION) {
//			return;
//		}
//
//		final File file = fc.getSelectedFile();
		final File file = ImageLoadingPaths.getInstance().getBfDir();

//		List<String> patterns = new ArrayList<String>();
//		// Pattern para los fields
//		patterns.add("(BF|CFP|YFP)");
//		// Pattern para la posicion
//		patterns.add("Position\\d*");
//		// Pattern para el tiempo
//		patterns.add("time_\\d*.tif");
//
//		Finder finder = new Finder(patterns);

		//TreeGenerator contains the information to generate the tree. based on the Finder and the directory
		treeGenerator = new TreeGenerator(file);

		jTree1 = treeGenerator.generateTree();
		jTree1.setExpandsSelectedPaths(true);

		jScrollPane1 = new javax.swing.JScrollPane();

		jTree1.setName("jTree1"); // NOI18N
		jScrollPane1.setName("jScrollPane1"); // NOI18N
		jScrollPane1.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
		jScrollPane1.setViewportView(jTree1);
		jScrollPane1.setPreferredSize(new Dimension(300, 300));
		add(jScrollPane1);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setPreferredSize(new Dimension(300, 150));
		buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
		buttonsPanel.setBackground(Color.white);

		Box buttonsBox = Box.createVerticalBox();
		buttonsBox.setPreferredSize(new Dimension(280, 130));

		Box rootBox;
		Box channelBox;
		Box timeBox;
		Box positionBox;
		Box synchroBox;

		Dimension buttonSize = new Dimension(140, 20);

		rootBox = createBox();
		channelBox = createBox();
		timeBox = createBox();
		positionBox = createBox();
		synchroBox = createBox();

		buttonsPanel.add(buttonsBox);

		JButton previousPositionButton = new JButton("Previous Position");
		previousPositionButton.setPreferredSize(buttonSize);
		previousPositionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (synchronize.isSelected()) {
					Iterator<Entry<ImageWindow, DefaultMutableTreeNode>> windowIter = treeGenerator
							.getWindows().entrySet().iterator();
					if (windowIter.hasNext()) {
						while (windowIter.hasNext()) {
							Entry<ImageWindow, DefaultMutableTreeNode> entry = windowIter
									.next();
							if (!entry.getKey().isClosed()) {
								DefaultMutableTreeNode node = entry.getValue();
								DefaultMutableTreeNode previousNode = getPreviousPositionNode(node);
								if (previousNode != null) {
									treeGenerator.openImageInWindow(
											previousNode, entry.getKey());
								}
							} else {
								windowIter.remove();
							}
						}
					}
				} else {
					if (jTree1.getLastSelectedPathComponent() != null) {
						DefaultMutableTreeNode node = treeGenerator
								.getWindows().get(
										WindowManager.getCurrentWindow());
						DefaultMutableTreeNode previousNode = getPreviousPositionNode(node);
						if (previousNode != null) {
							updatePath(node, previousNode);
							System.out.println(file.getAbsolutePath() + BAR
									+ previousNode.getUserObject().toString());
						}
					}
				}
			}
		});
		positionBox.add(previousPositionButton);

		JButton previousTimeButton = new JButton("Previous Time");
		previousTimeButton.setPreferredSize(buttonSize);
		previousTimeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (synchronize.isSelected()) {
					Iterator<Entry<ImageWindow, DefaultMutableTreeNode>> windowIter = treeGenerator
							.getWindows().entrySet().iterator();
					if (windowIter.hasNext()) {
						while (windowIter.hasNext()) {
							Entry<ImageWindow, DefaultMutableTreeNode> entry = windowIter
									.next();
							if (!entry.getKey().isClosed()) {
								DefaultMutableTreeNode node = entry.getValue();
								DefaultMutableTreeNode previousNode = getPreviousTimeNode(node);
								if (previousNode != null) {
									treeGenerator.openImageInWindow(
											previousNode, entry.getKey());
								}
							} else {
								windowIter.remove();
							}
						}
					}
				} else {
					if (jTree1.getLastSelectedPathComponent() != null) {
						DefaultMutableTreeNode node = treeGenerator
								.getWindows().get(
										WindowManager.getCurrentWindow());
						DefaultMutableTreeNode previousNode = getPreviousTimeNode(node);
						if (previousNode != null) {
							updatePath(node, previousNode);
							System.out.println(file.getAbsolutePath() + BAR
									+ previousNode.getUserObject().toString());
						}
					}
				}
			}
		});
		timeBox.add(previousTimeButton);

		final JButton previousButton = new JButton("Previous Channel");
		previousButton.setPreferredSize(buttonSize);
		previousButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (synchronize.isSelected()) {//debería dar excepción, maybe
					Iterator<Entry<ImageWindow, DefaultMutableTreeNode>> windowIter = treeGenerator
							.getWindows().entrySet().iterator();
					if (windowIter.hasNext()) {
						while (windowIter.hasNext()) {
							Entry<ImageWindow, DefaultMutableTreeNode> entry = windowIter
									.next();
							if (!entry.getKey().isClosed()) {
								DefaultMutableTreeNode node = entry.getValue();
								DefaultMutableTreeNode previousNode = getPreviousChannelNode(node);
								if (previousNode != null) {
									treeGenerator.openImageInWindow(
											previousNode, entry.getKey());
								}
							} else {
								windowIter.remove();
							}
						}
					}
				} else {
					if (jTree1.getLastSelectedPathComponent() != null) {
						DefaultMutableTreeNode node = treeGenerator
								.getWindows().get(
										WindowManager.getCurrentWindow());
						DefaultMutableTreeNode previousNode = getPreviousChannelNode(node);
						if (previousNode != null) {
							updatePath(node, previousNode);
							System.out.println(file.getAbsolutePath() + BAR
									+ previousNode.getUserObject().toString());
						}
					}
				}
			}

		});
		channelBox.add(previousButton);

		JButton rootButton = new JButton("Root");
		rootButton.setPreferredSize(buttonSize);
		rootButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int row = jTree1.getRowCount() - 1;
				while (row >= 0) {
					jTree1.collapseRow(row);
					row--;
				}
			}
		});
		rootBox.add(rootButton);

		final JButton nextButton = new JButton("Next Channel");
		nextButton.setPreferredSize(new Dimension(150, 20));
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (synchronize.isSelected()) {//debería dar excepción, maybe
					Iterator<Entry<ImageWindow, DefaultMutableTreeNode>> windowIter = treeGenerator
							.getWindows().entrySet().iterator();
					if (windowIter.hasNext()) {
						while (windowIter.hasNext()) {
							Entry<ImageWindow, DefaultMutableTreeNode> entry = windowIter
									.next();
							if (!entry.getKey().isClosed()) {
								DefaultMutableTreeNode node = entry.getValue();
								DefaultMutableTreeNode nextNode = getNextChannelNode(node);
								if (nextNode != null) {
									treeGenerator.openImageInWindow(
											nextNode, entry.getKey());
								}
							} else {
								windowIter.remove();
							}
						}
					}
				} else {
					if (jTree1.getLastSelectedPathComponent() != null) {
						DefaultMutableTreeNode node = treeGenerator
								.getWindows().get(
										WindowManager.getCurrentWindow());
						DefaultMutableTreeNode nextNode = getNextChannelNode(node);
						if (nextNode != null) {
							updatePath(node, nextNode);
							System.out.println(file.getAbsolutePath() + BAR
									+ nextNode.getUserObject().toString());
						}
					}
				}
			}
		});
		channelBox.add(nextButton);

		JButton nextTimeButton = new JButton("Next Time");
		nextTimeButton.setPreferredSize(buttonSize);
		nextTimeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (synchronize.isSelected()) {
					Iterator<Entry<ImageWindow, DefaultMutableTreeNode>> windowIter = treeGenerator
							.getWindows().entrySet().iterator();
					if (windowIter.hasNext()) {
						while (windowIter.hasNext()) {
							Entry<ImageWindow, DefaultMutableTreeNode> entry = windowIter
									.next();
							if (!entry.getKey().isClosed()) {
								DefaultMutableTreeNode node = entry.getValue();
								DefaultMutableTreeNode nextNode = getNextTimeNode(node);
								if (nextNode != null) {
									treeGenerator.openImageInWindow(
											nextNode, entry.getKey());
								}
							} else {
								windowIter.remove();
							}
						}
					}
				} else {
					if (jTree1.getLastSelectedPathComponent() != null) {
						DefaultMutableTreeNode node = treeGenerator
								.getWindows().get(
										WindowManager.getCurrentWindow());
						DefaultMutableTreeNode nextNode = getNextTimeNode(node);
						if (nextNode != null) {
							updatePath(node, nextNode);
							System.out.println(file.getAbsolutePath() + BAR
									+ nextNode.getUserObject().toString());
						}
					}
				}
			}
		});
		timeBox.add(nextTimeButton);

		JButton nextPositionButton = new JButton("Next Position");
		nextPositionButton.setPreferredSize(buttonSize);
		nextPositionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (synchronize.isSelected()) {
					Iterator<Entry<ImageWindow, DefaultMutableTreeNode>> windowIter = treeGenerator
							.getWindows().entrySet().iterator();
					if (windowIter.hasNext()) {
						while (windowIter.hasNext()) {
							Entry<ImageWindow, DefaultMutableTreeNode> entry = windowIter
									.next();
							if (!entry.getKey().isClosed()) {
								DefaultMutableTreeNode node = entry.getValue();
								DefaultMutableTreeNode nextNode = getNextPositionNode(node);
								if (nextNode != null) {
									treeGenerator.openImageInWindow(
											nextNode, entry.getKey());
								}
							} else {
								windowIter.remove();
							}
						}
					}
				} else {
					if (jTree1.getLastSelectedPathComponent() != null) {
						DefaultMutableTreeNode node = treeGenerator
								.getWindows().get(
										WindowManager.getCurrentWindow());
						DefaultMutableTreeNode nextNode = getNextPositionNode(node);
						if (nextNode != null) {
							updatePath(node, nextNode);
							System.out.println(file.getAbsolutePath() + BAR
									+ nextNode.getUserObject().toString());
						}
					}
				}

			}

		});
		positionBox.add(nextPositionButton);

		synchronize = new JCheckBox("Synchronize");
		synchronize.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				AbstractButton abstractButton = (AbstractButton) arg0
						.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				previousButton.setEnabled(!selected);
				nextButton.setEnabled(!selected);

			}
		});
		synchroBox.add(synchronize);

		buttonsBox.add(synchroBox);
		buttonsBox.add(rootBox);
		buttonsBox.add(channelBox);
		buttonsBox.add(timeBox);
		buttonsBox.add(positionBox);

		buttonsPanel.add(buttonsBox);

		add(buttonsPanel, BorderLayout.SOUTH);

		setSize(300, 700);
		setResizable(true);
		setVisible(true);
	}

	/**
	 * 
	 * @return
	 */
	private Box createBox() {
		Dimension hboxSize = new Dimension(280, 20);
		Box rootBox;
		rootBox = Box.createHorizontalBox();
		rootBox.setPreferredSize(hboxSize);
		return rootBox;
	}

	/**
	 * 
	 * @param node
	 */
	private void updateDisplayRanges(DefaultMutableTreeNode node) {
		ImagePlus imp = WindowManager.getCurrentImage();
		DisplayRangeObject dro = new DisplayRangeObject(
				imp.getDisplayRangeMin(), imp.getDisplayRangeMax(),
				imp.getNChannels());
		Integer position = node.getParent().getIndex(node);
		treeGenerator.getDisplayRanges().put(position, dro);
	}

	/**
	 * 
	 * @param node
	 * @param previousNode
	 */
	private void updatePath(DefaultMutableTreeNode node, DefaultMutableTreeNode previousNode) {
		TreePath path = new TreePath(
				((DefaultTreeModel) jTree1.getModel())
						.getPathToRoot(previousNode));
		updateDisplayRanges(node);
		jTree1.setSelectionPath(path);
		jTree1.scrollPathToVisible(path);
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	private DefaultMutableTreeNode getNextTimeNode(DefaultMutableTreeNode node) {
		DefaultMutableTreeNode nextNode = null;
		if (node == null) {
			nextNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) jTree1
					.getModel().getRoot()).getFirstChild().getChildAt(0);
		} else if (node.isLeaf()) {
			int position = node.getParent().getIndex(node);
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node
					.getParent();
			nextNode = (DefaultMutableTreeNode) parent.getNextSibling();
			if (nextNode == null) {
				nextNode = (DefaultMutableTreeNode) parent.getParent()
						.getChildAt(0);
			}
			nextNode = (DefaultMutableTreeNode) nextNode.getChildAt(position);
		} else if (node.getUserObject().toString().toLowerCase()
				.contains("time")) {
			nextNode = node.getNextSibling();
			if (nextNode == null) {
				nextNode = (DefaultMutableTreeNode) node.getParent()
						.getChildAt(0);
			}
		} else if (node.getUserObject().toString().toLowerCase()
				.contains("position")) {
			nextNode = (DefaultMutableTreeNode) node.getFirstChild();
		} else if (node.isRoot()) {
			nextNode = (DefaultMutableTreeNode) node.getFirstChild()
					.getChildAt(0);
		}
		return nextNode;
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	private DefaultMutableTreeNode getPreviousPositionNode(DefaultMutableTreeNode node) {
		DefaultMutableTreeNode previousNode = null;

		if (node == null) {
			previousNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) jTree1
					.getModel().getRoot()).getLastChild();
		} else if (node.isLeaf()) {
			int leafIndex = node.getParent().getIndex(node);
			int timeIndex = node.getParent().getParent()
					.getIndex(node.getParent());
			DefaultMutableTreeNode positionParent = (DefaultMutableTreeNode) node
					.getParent().getParent();
			previousNode = positionParent.getPreviousSibling();
			if (previousNode == null) {
				previousNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) positionParent
						.getParent()).getLastChild();
			}
			previousNode = (DefaultMutableTreeNode) previousNode.getChildAt(
					timeIndex).getChildAt(leafIndex);
		} else if (node.getUserObject().toString().toLowerCase()
				.contains("time")) {
			int timeIndex = node.getParent().getIndex(node);
			DefaultMutableTreeNode positionParent = (DefaultMutableTreeNode) node
					.getParent();
			previousNode = positionParent.getPreviousSibling();
			if (previousNode == null) {
				previousNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) positionParent
						.getParent()).getLastChild();
			}
			previousNode = (DefaultMutableTreeNode) previousNode
					.getChildAt(timeIndex);
		} else if (node.getUserObject().toString().toLowerCase()
				.contains("position")) {
			previousNode = node.getPreviousSibling();
			if (previousNode == null) {
				previousNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) node
						.getParent()).getLastChild();
			}
		} else if (node.isRoot()) {
			previousNode = (DefaultMutableTreeNode) node.getLastChild();
		}
		return previousNode;
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	private DefaultMutableTreeNode getPreviousTimeNode(	DefaultMutableTreeNode node) {
		DefaultMutableTreeNode previousNode = null;
		if (node == null) {
			previousNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) ((DefaultMutableTreeNode) jTree1
					.getModel().getRoot()).getLastChild()).getLastChild();
		} else if (node.isLeaf()) {
			int leafIndex = node.getParent().getIndex(node);
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node
					.getParent();
			previousNode = parent.getPreviousSibling();
			if (previousNode == null) {
				previousNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) parent
						.getParent()).getLastChild();
			}
			previousNode = (DefaultMutableTreeNode) previousNode
					.getChildAt(leafIndex);
		} else if (node.getUserObject().toString().toLowerCase()
				.contains("time")) {
			previousNode = node.getPreviousSibling();
			if (previousNode == null) {
				previousNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) node
						.getParent()).getLastChild();
			}
		} else if (node.getUserObject().toString().toLowerCase()
				.contains("position")) {
			previousNode = (DefaultMutableTreeNode) node.getLastChild();
		} else if (node.isRoot()) {
			previousNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) node
					.getLastChild()).getLastChild();
		}
		return previousNode;
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	private DefaultMutableTreeNode getPreviousChannelNode(DefaultMutableTreeNode node) {
		DefaultMutableTreeNode previousNode = null;
		if (node == null) {
			previousNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) ((DefaultMutableTreeNode) ((DefaultMutableTreeNode) jTree1
					.getModel().getRoot()).getLastChild()).getLastChild())
					.getLastChild();
		} else if (node.isLeaf()) {
			previousNode = node.getPreviousSibling();
			if (previousNode == null) {
				previousNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) node
						.getParent()).getLastChild();
			}
		} else if (node.getUserObject().toString().toLowerCase()
				.contains("time")) {
			previousNode = (DefaultMutableTreeNode) node.getPreviousSibling()
					.getLastChild();
		} else if (node.getUserObject().toString().toLowerCase()
				.contains("position")) {
			previousNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) node
					.getLastChild()).getLastChild();
		} else if (node.isRoot()) {
			previousNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) ((DefaultMutableTreeNode) node
					.getLastChild()).getLastChild()).getLastChild();
		}
		return previousNode;
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	private DefaultMutableTreeNode getNextChannelNode(DefaultMutableTreeNode node) {
		DefaultMutableTreeNode nextNode = null;
		if (node == null) {
			nextNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) jTree1
					.getModel().getRoot()).getFirstChild().getChildAt(0)
					.getChildAt(0);
		} else if (node.isLeaf()) {
			nextNode = node.getNextSibling();
			if (nextNode == null) {
				nextNode = (DefaultMutableTreeNode) node.getParent()
						.getChildAt(0);
			}
		} else if (node.getUserObject().toString().toLowerCase()
				.contains("time")) {
			nextNode = (DefaultMutableTreeNode) node.getFirstChild();
		} else if (node.getUserObject().toString().toLowerCase()
				.contains("position")) {
			nextNode = (DefaultMutableTreeNode) node.getFirstChild()
					.getChildAt(0);
		} else if (node.isRoot()) {
			nextNode = (DefaultMutableTreeNode) node.getFirstChild()
					.getChildAt(0).getChildAt(0);
		}
		return nextNode;
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	private DefaultMutableTreeNode getNextPositionNode( DefaultMutableTreeNode node) {
		DefaultMutableTreeNode nextNode = null;
		if (node == null) {
			nextNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) jTree1
					.getModel().getRoot()).getFirstChild();
		} else if (node.isLeaf()) {
			int leafIndex = node.getParent().getIndex(node);
			int timeIndex = node.getParent().getParent()
					.getIndex(node.getParent());
			DefaultMutableTreeNode positionParent = (DefaultMutableTreeNode) node
					.getParent().getParent();
			nextNode = positionParent.getNextSibling();
			if (nextNode == null) {
				nextNode = (DefaultMutableTreeNode) positionParent.getParent()
						.getChildAt(0);
			}
			nextNode = (DefaultMutableTreeNode) nextNode.getChildAt(timeIndex)
					.getChildAt(leafIndex);
		} else if (node.getUserObject().toString().toLowerCase()
				.contains("time")) {
			int timeIndex = node.getParent().getIndex(node);
			DefaultMutableTreeNode positionParent = (DefaultMutableTreeNode) node
					.getParent();
			nextNode = positionParent.getNextSibling();
			if (nextNode == null) {
				nextNode = (DefaultMutableTreeNode) positionParent.getParent()
						.getChildAt(0);
			}
			nextNode = (DefaultMutableTreeNode) nextNode.getChildAt(timeIndex);
		} else if (node.getUserObject().toString().toLowerCase()
				.contains("position")) {
			nextNode = node.getNextSibling();
			if (nextNode == null) {
				nextNode = (DefaultMutableTreeNode) node.getParent()
						.getChildAt(0);
			}
		} else if (node.isRoot()) {
			nextNode = (DefaultMutableTreeNode) node.getFirstChild();
		}
		return nextNode;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
