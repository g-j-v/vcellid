/**
 * Class which creates the tree and its behaviour
 */

package utils.tree;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.ImageWindow;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import utils.DisplayRangeObject;
import utils.ImageNamePattern;
import utils.node.ImageNode;
import utils.node.PositionNode;
import utils.node.TimeNode;



import cellid.Segmentation;
import cellid.image.LoadImagesPatterns;

import com.sun.media.imageio.plugins.tiff.TIFFImageWriteParam;


public class TreeGenerator {

	/**
	 * Variables
	 */
	private Finder finder;
	private File directory;
	private List<String> fileNames;
	private List<String> fluorChannels;
	private Map<Integer, DisplayRangeObject> displayRanges;
	private Map<ImageWindow, DefaultMutableTreeNode> windows;

	/**
	 * Constructor
	 * @param finder to know which images to get
	 * @param directory where to find the images
	 */
	public TreeGenerator( File directory) {
		ImageNamePattern pattern = ImageNamePattern.getInstance();
		this.finder = new Finder(pattern.getSeparator(), pattern.generatePatternList());
		this.directory = directory;
		this.fileNames = finder.find(directory);
		this.fluorChannels = finder.getFluorChannels(fileNames);
		this.displayRanges = new HashMap<Integer, DisplayRangeObject>();
		this.windows = new HashMap<ImageWindow, DefaultMutableTreeNode>();
		generateEmptyTiff();
	}

	/**
	 * Generates the tree
	 * @return a tree with the images
	 */
	public JTree generateTree() {
		final JTree tree;
		// For PopUp
		final JPopupMenu RootPosPopup = new JPopupMenu();
		final JPopupMenu TimePopup = new JPopupMenu();
		final JPopupMenu BfPopup = new JPopupMenu();
		final JPopupMenu FlImagePopup = new JPopupMenu();

		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Images");
		createNodes(top, directory);
		tree = new JTree(top);
		tree.setCellRenderer(new MyRenderer());
		RootPosPopup.add(new JMenuItem("Run")).addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {

						Segmentation seg = new Segmentation(tree, directory, fluorChannels,
								false, true);
						seg.setVisible(true);

					}
				});
		RootPosPopup.add(new JMenuItem("Run BF")).addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Segmentation seg = new Segmentation(tree, directory, fluorChannels,
								true, true);
						seg.setVisible(true);
					}
				});

		RootPosPopup.setInvoker(tree);

		TimePopup.add(new JMenuItem("Run Position")).addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Segmentation seg = new Segmentation(tree, directory, fluorChannels,
								false, true);
						seg.setVisible(true);
					}
				});
		
		TimePopup.add(new JMenuItem("Test Time")).addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Segmentation seg = new Segmentation(tree, directory, fluorChannels,
								true, false);
						seg.setVisible(true);
					}
				});

		TimePopup.setInvoker(tree);

		BfPopup.add(new JMenuItem("Run Position")).addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Segmentation seg = new Segmentation(tree, directory, fluorChannels,
								false, true);
						seg.setVisible(true);
					}
				});

		BfPopup.add(new JMenuItem("Test Time")).addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Segmentation seg = new Segmentation(tree, directory, fluorChannels,
								true, false);
						seg.setVisible(true);
					}
				});

		BfPopup.add(new JMenuItem("Open in new window")).addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
								.getLastSelectedPathComponent();
						System.out.println("Evento Right");
						if (node == null || node.getChildCount() > 0) {
							System.out.println("hola");
							return;
						}

						openImageInNewWindow(node);
					}
				});

		BfPopup.setInvoker(tree);

		FlImagePopup.add(new JMenuItem("Open in new window"))
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
								.getLastSelectedPathComponent();
						System.out.println("Evento Right");
						if (node == null || node.getChildCount() > 0) {
							System.out.println("hola");
							return;
						}

						openImageInNewWindow(node);
					}
				});
		FlImagePopup.setInvoker(tree);

		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {

				if (e.getOldLeadSelectionPath() != null) {
					checkDisplayRange(windows.get(WindowManager.getCurrentWindow()));
//					checkDisplayRange(e.getOldLeadSelectionPath()
//							.getLastPathComponent());
				}

				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
						.getLastSelectedPathComponent();
				System.out.println("Evento");
				if (node == null || node.getChildCount() > 0) {
					System.out.println("hola");
					return;
				}

				String filePath = node.getUserObject().toString();
				System.out.println(directory.getAbsolutePath());
				System.out.println(filePath);
				
				ImagePlus imp = WindowManager.getCurrentImage();
				if (imp == null) {
					openImageInNewWindow(node);
				} else {
					System.out.println("Hay una abierta!");
					openImageInCurrentWindow(imp, node);
				}
			}

		});

		tree.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent evt) {
				if (evt.getButton() != MouseEvent.BUTTON3) {
					return;
				}
				System.out.println("Largo el boton derecho");
				TreePath selPath = tree.getPathForLocation(evt.getX(),
						evt.getY());
				if (selPath == null) {
					return;
				} else {
					tree.setSelectionPath(selPath);
				}
				if (evt.isPopupTrigger()) {
					System.out.println(selPath);
					if (selPath.getParentPath() == null
							|| ((DefaultMutableTreeNode) selPath
									.getLastPathComponent()).getUserObject() instanceof PositionNode) {
						RootPosPopup.show(evt.getComponent(), evt.getX(),
								evt.getY());
					} else if (((DefaultMutableTreeNode) selPath
							.getLastPathComponent()).getUserObject() instanceof TimeNode) {
						TimePopup.show(evt.getComponent(), evt.getX(),
								evt.getY());
					} else if (((ImageNode) ((DefaultMutableTreeNode) selPath
							.getLastPathComponent()).getUserObject())
							.getImage().getChannel().equals("BF")) {
						BfPopup.show(evt.getComponent(), evt.getX(), evt.getY());
					} else if (((ImageNode) ((DefaultMutableTreeNode) selPath
							.getLastPathComponent()).getUserObject())
							.getImage().getChannel().substring(1,3).equals("FP")) {
						FlImagePopup.show(evt.getComponent(), evt.getX(),
								evt.getY());
					} else {
						System.out.println("No Popup to show");
					}
					// popup.show(evt.getComponent(), evt.getX(), evt.getY());
				}

			}

			@Override
			public void mousePressed(MouseEvent evt) {
				if (evt.getButton() != MouseEvent.BUTTON3) {
					return;
				}
				System.out.println("Aprieto el boton derecho");
				TreePath selPath = tree.getPathForLocation(evt.getX(),
						evt.getY());
				if (selPath == null) {
					return;
				} else {
					tree.setSelectionPath(selPath);
				}
				if (evt.isPopupTrigger()) {
					System.out.println(selPath);
					System.out.println(selPath);
					if (selPath.getParentPath() == null
							|| ((DefaultMutableTreeNode) selPath
									.getLastPathComponent()).getUserObject() instanceof PositionNode) {
						RootPosPopup.show(evt.getComponent(), evt.getX(),
								evt.getY());
					} else if (((DefaultMutableTreeNode) selPath
							.getLastPathComponent()).getUserObject() instanceof TimeNode) {
						TimePopup.show(evt.getComponent(), evt.getX(),
								evt.getY());
					} else if (((ImageNode) ((DefaultMutableTreeNode) selPath
							.getLastPathComponent()).getUserObject())
							.getImage().getChannel().equals("BF")) {
						BfPopup.show(evt.getComponent(), evt.getX(), evt.getY());
					} else if (((ImageNode) ((DefaultMutableTreeNode) selPath
							.getLastPathComponent()).getUserObject())
							.getImage().getChannel().substring(1,3).equals("FP")) {
						FlImagePopup.show(evt.getComponent(), evt.getX(),
								evt.getY());
					} else {
						System.out.println("No Popup to show");
					}
					// popup.show(evt.getComponent(), evt.getX(), evt.getY());
				}

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		return tree;
	}

	/**
	 * Adds nodes to the tree.
	 * @param top the node where to add new nodes, usually the root
	 * @param directory where to get the images
	 */
	public void createNodes(DefaultMutableTreeNode top, File directory) {

		List<PositionImage> images = filesToImages(finder.find(directory));

		System.out.println("TAM: " + fileNames.size());
		int maxPositions = getMaxPosition(images);
		int maxTimeAllPosition = getMaxTime(images);
		for (int i = 1; i <= maxPositions; ++i) {
			PositionNode positionNode = new PositionNode(i);
			// Adding position to root
			DefaultMutableTreeNode treePositionNode = new DefaultMutableTreeNode(
					positionNode);
			top.add(treePositionNode);
			for (int j = 1; j <= maxTimeAllPosition; ++j) {
				TimeNode timeNode = new TimeNode(positionNode, j);
				DefaultMutableTreeNode treeTimeNode = new DefaultMutableTreeNode(
						timeNode);
				// Adding Time node to position
				treePositionNode.add(treeTimeNode);
				if(j == 20101)
					System.out.println(j);
				List<PositionImage> imageNames = getImages(images,positionNode,timeNode);
				
				//agrego todas las imagenes que encontre en el directorio. Las que no existen ya fueron creadas como "empty" en getImages();
				for(PositionImage positionImage: imageNames){
					timeNode.addImageNode(new ImageNode(positionNode, timeNode, positionImage, positionImage.isEmpty()));
					treeTimeNode.add(new DefaultMutableTreeNode(new ImageNode(positionNode, timeNode, positionImage, positionImage.isEmpty())));
				}

			}

		}
	}

	/**
	 * Looks for the biggest position.
	 * @param images list of @PositionImage to look for the biggest time 
	 * @return the maximum position
	 */
	public int getMaxPosition(List<PositionImage> images) {

		// TODO: Para generalizar todavia mas podemos recibir un parametro
		// que identifique el nombre con el que empieza la posicion, o guardarlo
		// como variable de instancia de Position
		int max = 0;
		for (PositionImage image : images) {
			int aux = Integer.valueOf(image.getPositionId());
			if (aux > max) {
				max = aux;
			}
		}
		System.out.println("Max Pos: " + max);
		return max;
	}

	/**
	 * Looks for the biggest time in a given position
	 * @param position
	 * @param images list of @PositionImage to look for the biggest time 
	 * @return the maximum time
	 */
	public int getMaxTime(PositionNode position, List<PositionImage> images) {

		// TODO: Para generalizar todavia mas podemos recibir un parametro
		// que identifique el nombre con el que empieza el tiempo, o guardarlo
		// como variable de instancia de Time
		int max = 0;
		for (PositionImage image : images) {
			int aux;

			if (Integer.valueOf(image.getPositionId()) == position.getNumber()) {
				aux = Integer.valueOf(((TimeImage)image).getTimeId());
				if (aux > max) {
					max = aux;
				}

			}
		}
		// System.out.println("Max time:" + max);
		return max;
	}

	/**
	 * Looks for the biggest time for all the positions
	 * @param images list of @PositionImage to look for the biggest time 
	 * @return the maximum time or 1 if the images do not have time
	 */
	public int getMaxTime(List<PositionImage> images) {

		if(!ImageNamePattern.getInstance().isTimeFlag()){
			return 1;
		}
		int max = 0;
		if(!(images.get(0) instanceof TimeImage)){
			return 1;
		}
		for (int i = 0; i < getMaxPosition(images); ++i) {
			int currentMax = getMaxTime(new PositionNode(i), images);
			if (currentMax > max) {
				max = currentMax;
			}
		}
		return max;
	}
	
	/**
	 * 
	 * @param fileNames name of the images to convert to PositionImage
	 * @return the PositionImages corresponding to each file name
	 */
	private List<PositionImage> filesToImages(List<String> fileNames){
		
		List<PositionImage> images = new ArrayList<PositionImage>();
		ImageNamePattern namePattern = ImageNamePattern.getInstance();
		
		for(String name: fileNames){
			
			String channel = "";
			boolean out = false;
			
			//Channel
			if(name.toLowerCase().contains(namePattern.getBrightfieldChannelPattern().toLowerCase())){
				channel = namePattern.getBrightfieldChannelPattern();
			}else if(name.toLowerCase().contains(namePattern.getFluorChannelPattern().toLowerCase())){
				channel = name.charAt(0) + namePattern.getFluorChannelPattern();
			}else{
				//Image does not contain any of the channel patterns specified
				return null;
			}
			
			//Position Id in string
			String position = name.split(namePattern.getPositionPattern())[1];
			
			int i;
			for (i = 0 ; position.charAt(i) >= '0'
					&& position.charAt(i) <= '9'; i++) {
				;
			}
			
			//Output image
			if(name.toLowerCase().contains(".out")){
				out = true;
			}
			
			//Time Id if exists
			if(namePattern.isTimeFlag()){
				
				String time = name.split(namePattern.getTimePattern())[1];
				
				int j;
				for (j = 0 ; time.charAt(j) >= '0'
						&& time.charAt(j) <= '9'; j++) {
					;
				}
				images.add(new TimeImage(channel, position.substring(0,i), out, time.substring(0,j),false));
			}else{
				images.add(new PositionImage(channel, position.substring(0,i), out, false));
								
			}
			
		}
		return images;
	}

	/**
	 * Gets the images for a given time and position and completes the missing ones
	 * @param images the images found in the directory
	 * @param positionNode the position to get the images from
	 * @param timeNode	the time to get the images from
	 * @return the complete list of images (empty or not) corresponding to that position and time
	 */
	private List<PositionImage> getImages(List<PositionImage> images, PositionNode positionNode, TimeNode timeNode){
		
		if(images == null || images.size() == 0){
			System.out.println("No images to process");
			return null;
		}
		if(positionNode == null || timeNode == null){
			System.out.println("Invalid Position or Time Node");
			return null;
		}
		boolean time = images.get(0) instanceof TimeImage;
		List<PositionImage> selectedImages = new ArrayList<PositionImage>();
		for(PositionImage img: images){
			if(Integer.valueOf(img.getPositionId()).equals(positionNode.getNumber())){
				if(time){
					if(Integer.valueOf(((TimeImage)img).getTimeId()).equals(timeNode.getNumber())) {
						selectedImages.add(img);
					}
				}else{
					selectedImages.add(img);
				}
			}
		}
		
		boolean[] existentImages = new boolean[2 + fluorChannels.size()*2];
		for(PositionImage img: selectedImages){
			if(img.isBF() && !img.isOut()){
				existentImages[0] = true;
			}else if(img.isBF() && img.isOut()){
				existentImages[1] = true;
			}else if(fluorChannels.contains(img.getChannel()) && !img.isOut()){
				existentImages[2 + 2 * (fluorChannels.indexOf(img.getChannel()))] = true;
			}else if(fluorChannels.contains(img.getChannel()) && img.isOut()){
				existentImages[3 + (2 * fluorChannels.indexOf(img.getChannel()))] = true;
			}else{
				System.out.println("There is a strange image");
				return null;
			}
		}
		for(int i = 0; i < existentImages.length ; ++i){
			ImageNamePattern pattern = ImageNamePattern.getInstance();
			if(!existentImages[i]){
				if( i == 0){
					if(time){
						selectedImages.add(i,new TimeImage(pattern.getBrightfieldChannelPattern(), String.valueOf(positionNode.getNumber()), false, String.valueOf(timeNode.getNumber()), true));
					}else{
						selectedImages.add(i,new PositionImage(pattern.getBrightfieldChannelPattern(), String.valueOf(positionNode.getNumber()), false,true));
					}
				}else if (i == 1){
					if(time){
						selectedImages.add(i,new TimeImage(pattern.getBrightfieldChannelPattern(), String.valueOf(positionNode.getNumber()), true, String.valueOf(timeNode.getNumber()), true));
					}else{
						selectedImages.add(i,new PositionImage(pattern.getBrightfieldChannelPattern(), String.valueOf(positionNode.getNumber()), true,true));
					}
				}else if(i % 2 == 0){
					if(time){
						selectedImages.add(i,new TimeImage(fluorChannels.get((i-2)/2), String.valueOf(positionNode.getNumber()), false, String.valueOf(timeNode.getNumber()), true));
					}else{
						selectedImages.add(i,new PositionImage(fluorChannels.get((i-2)/2), String.valueOf(positionNode.getNumber()), false,true));
					}
				}else if(i % 2 == 1){
					if(time){
						selectedImages.add(i,new TimeImage(fluorChannels.get((i-3)/2), String.valueOf(positionNode.getNumber()), true, String.valueOf(timeNode.getNumber()), true));
					}else{
						selectedImages.add(i,new PositionImage(fluorChannels.get((i-3)/2), String.valueOf(positionNode.getNumber()), true,true));
					}
				}else{
					System.out.println("Should have never reached this location");
					return null;
				}
			}
		}
		return selectedImages;
	}
	
//	/**
//	 * 
//	 * @author alejandropetit
//	 *
//	 */
//	private class NameFilter implements FilenameFilter {
//
//		PositionNode position;
//		TimeNode time;
//
//		private NameFilter(PositionNode position, TimeNode time) {
//
//			this.position = position;
//			this.time = time;
//		}
//
//		public boolean accept(File dir, String name) {
//			if (name.toLowerCase().contains(position.toString())
//					&& name.toLowerCase().contains(time.toString())) {
//				return true;
//			} else {
//				return false;
//			}
//		}
//
//	}

//	private PositionImage getBf(List<PositionImage> images) {
//		for (PositionImage image : images) {
//			if (image.getChannel().toUpperCase().equals("BF")
//					&& !image.isOut()) {
//				return image;
//			}
//		}
//		return null;
//	}
//
//	private String getBfOut(File[] files) {
//		for (File file : files) {
//			if (file.getName().toLowerCase().contains("bf")
//					&& file.getName().toLowerCase().contains(".out.tif")) {
//				return file.getName();
//			}
//		}
//		return null;
//	}
//
//	private String getFp(File[] files, String channel) {
//		for (File file : files) {
//			if (file.getName().toLowerCase().contains(channel.toLowerCase())
//					&& !file.getName().toLowerCase().contains(".out.tif")) {
//				return file.getName();
//			}
//		}
//		return null;
//	}
//
//	private String getFpOut(File[] files, String channel) {
//		for (File file : files) {
//			if (file.getName().toLowerCase().contains(channel.toLowerCase())
//					&& file.getName().toLowerCase().contains(".out.tif")) {
//				return file.getName();
//			}
//		}
//		return null;
//	}


	
	public Map<Integer, DisplayRangeObject> getDisplayRanges() {
		return displayRanges;
	}
	
	/**
	 * Used to show the fake images in red
	 * @author alejandropetit
	 *
	 */
	private class MyRenderer extends DefaultTreeCellRenderer {

		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, sel, expanded,
					leaf, row, hasFocus);

			if (leaf && isFakeImageNode(value)) {
				setForeground(Color.RED);
			}

			return this;
		}

		protected boolean isFakeImageNode(Object value) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			ImageNode nodeInfo;
			try{
				nodeInfo = (ImageNode) (node.getUserObject());
				if (nodeInfo.isFake()) {
					return true;
				}
			}catch(Exception e){
				return false;
			}
			return false;

		}
	}

	/**
	 * 
	 * @param imp
	 * @param node
	 */
	public void openImageInCurrentWindow(ImagePlus imp,
			DefaultMutableTreeNode node) {

		ImageWindow win = imp.getWindow();

		Integer channel = node.getParent().getIndex(node);
		DisplayRangeObject dro = displayRanges.get(channel);
		ImagePlus imp2;
		String newTitle;
		if (((ImageNode) node.getUserObject()).isFake()) {
			imp2 = IJ.openImage(directory + System.getProperty("file.separator")+ "EmptyImage.tiff");
			newTitle = "Empty Image";
		} else {
			String filePath = node.getUserObject().toString();
			System.out.println(directory.getAbsolutePath());
			System.out.println(filePath);

			imp2 = IJ.openImage(directory.getAbsolutePath()
					+ System.getProperty("file.separator") + filePath);
			newTitle = imp2.getTitle();
		}

		if (dro != null) {
			imp2.setDisplayRange(dro.getMin(), dro.getMax(), dro.getChannels());
		}

		imp.setStack(newTitle, imp2.getStack());
		imp.setCalibration(imp2.getCalibration());
		imp.setFileInfo(imp2.getOriginalFileInfo());
		imp.setProperty("Info", imp2.getProperty("Info"));

		if (win != null) {
			win.repaint();
			win.toFront();
		}
		
		windows.put(win, node);
	}

	/**
	 * This method keeps the DisplayRangeObject from the ImagePlus object
	 * currently displayed in win.
	 * 
	 * @param node
	 * @param win
	 */
	public void openImageInWindow(DefaultMutableTreeNode node, ImageWindow win) {

		ImagePlus imp = win.getImagePlus();
		DisplayRangeObject dro = new DisplayRangeObject(
				imp.getDisplayRangeMin(), imp.getDisplayRangeMax(),
				imp.getNChannels());
		ImagePlus imp2;
		String newTitle;
		if (((ImageNode) node.getUserObject()).isFake()) {
			imp2 = IJ.openImage(directory + System.getProperty("file.separator")+ "EmptyImage.tiff");
			newTitle = "Empty Image";
		} else {
			String filePath = node.getUserObject().toString();
			System.out.println(directory.getAbsolutePath());
			System.out.println(filePath);

			imp2 = IJ.openImage(directory.getAbsolutePath()
					+ System.getProperty("file.separator") + filePath);
			newTitle = imp2.getTitle();
		}

		if (dro != null) {
			imp2.setDisplayRange(dro.getMin(), dro.getMax(), dro.getChannels());
		}

		imp.setStack(newTitle, imp2.getStack());
		imp.setCalibration(imp2.getCalibration());
		imp.setFileInfo(imp2.getOriginalFileInfo());
		imp.setProperty("Info", imp2.getProperty("Info"));

		if (win != null) {
			win.repaint();
		}

		windows.put(win, node);
	}

	/**
	 * 
	 * @param node
	 */
	public void openImageInNewWindow(DefaultMutableTreeNode node) {

		Integer channel = node.getParent().getIndex(node);
		DisplayRangeObject dro = displayRanges.get(channel);

		String filePath;

		if (((ImageNode) node.getUserObject()).isFake()) {
			filePath = directory + System.getProperty("file.separator")+ "EmptyImage.tiff";
		} else {
			filePath = directory.getAbsolutePath()
					+ System.getProperty("file.separator")
					+ node.getUserObject().toString();
		}

		System.out.println(directory.getAbsolutePath());
		System.out.println(filePath);

		IJ.open(filePath);
		if (dro != null) {
			ImagePlus imp = WindowManager.getCurrentImage();
			imp.setDisplayRange(dro.getMin(), dro.getMax(), dro.getChannels());
			ImageWindow win = imp.getWindow();
			if (win != null) {
				win.repaint();
				win.toFront();
			}
		}
		windows.put(WindowManager.getCurrentWindow(), node);

	}

	/**
	 * 
	 * @param lastPathComponent
	 */
	private void checkDisplayRange(Object lastPathComponent) {

		if (lastPathComponent == null) {
			return;
		}

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) lastPathComponent;
		Integer channel = node.getParent().getIndex(node);
		ImagePlus imp = WindowManager.getCurrentImage();
		if (imp == null) {
			return;
		}

		DisplayRangeObject dro = displayRanges.get(channel);
		if (dro == null) {
			dro = new DisplayRangeObject(imp.getDisplayRangeMin(),
					imp.getDisplayRangeMax(), imp.getNChannels());
			displayRanges.put(channel, dro);
		} else {
			if (imp.getDisplayRangeMax() != dro.getMax()) {
				dro.setMax(imp.getDisplayRangeMax());
			}
			if (imp.getDisplayRangeMin() != dro.getMin()) {
				dro.setMin(imp.getDisplayRangeMin());
			}
			if (imp.getNChannels() != dro.getChannels()) {
				dro.setChannels(imp.getNChannels());
			}
		}
		windows.put(WindowManager.getCurrentWindow(), node);
	}

	public Map<ImageWindow, DefaultMutableTreeNode> getWindows() {
		return windows;
	}
	
	/**
	 * Generates a Black .tiff image to use as Empty Image.
	 * @return
	 */
	private boolean generateEmptyTiff(){

		BufferedImage image = new BufferedImage(512,512,BufferedImage.TYPE_BYTE_GRAY);
		String filename = directory + System.getProperty("file.separator") + "EmptyImage.tiff";
		File tiffFile = new File(filename);
		ImageOutputStream ios = null;
		ImageWriter writer = null;

		try {

			// find an appropriate writer
			Iterator it = ImageIO.getImageWritersByFormatName("TIF");
			if (it.hasNext()) {
				writer = (ImageWriter)it.next();
			} else {
				return false;
			}

			// setup writer
			ios = ImageIO.createImageOutputStream(tiffFile);
			writer.setOutput(ios);
			TIFFImageWriteParam writeParam = new TIFFImageWriteParam(Locale.ENGLISH);
			writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			// see writeParam.getCompressionTypes() for available compression type strings
			writeParam.setCompressionType("PackBits");

			// convert to an IIOImage
			IIOImage iioImage = new IIOImage(image, null, null);

			// write it!
			writer.write(null, iioImage, writeParam);

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<String> getFluorChannels() {
		return fluorChannels;
	}

	public void setFluorChannels(List<String> fluorChannels) {
		this.fluorChannels = fluorChannels;
	}

}
