package utils;

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

import com.sun.media.imageio.plugins.tiff.TIFFImageWriteParam;

import CellId.Segmentation;

public class TreeGenerator {

	private Finder finder;
	private File directory;
	private List<String> fileNames;
	private Map<Integer, DisplayRangeObject> displayRanges;
	private Map<ImageWindow, DefaultMutableTreeNode> windows;

	public TreeGenerator(Finder finder, File directory) {
		this.finder = finder;
		this.directory = directory;
		this.fileNames = finder.find(directory);
		this.displayRanges = new HashMap<Integer, DisplayRangeObject>();
		this.windows = new HashMap<ImageWindow, DefaultMutableTreeNode>();
		generateEmptyTiff();
	}

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

						Segmentation seg = new Segmentation(tree, directory,
								false, true);
						seg.setVisible(true);

					}
				});
		RootPosPopup.add(new JMenuItem("Run BF")).addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Segmentation seg = new Segmentation(tree, directory,
								true, true);
						seg.setVisible(true);
					}
				});

		RootPosPopup.setInvoker(tree);

		TimePopup.add(new JMenuItem("Run Position")).addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Segmentation seg = new Segmentation(tree, directory,
								false, true);
						seg.setVisible(true);
					}
				});
		// TimeBfPopup.add(new JMenuItem("Run Time")).addActionListener(new
		// ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// Segmentation seg = new Segmentation(tree, directory,false);
		// seg.setVisible(true);
		// }
		// });
		TimePopup.add(new JMenuItem("Test Time")).addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Segmentation seg = new Segmentation(tree, directory,
								true, false);
						seg.setVisible(true);
					}
				});

		TimePopup.setInvoker(tree);

		BfPopup.add(new JMenuItem("Run Position")).addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Segmentation seg = new Segmentation(tree, directory,
								false, true);
						seg.setVisible(true);
					}
				});

		BfPopup.add(new JMenuItem("Test Time")).addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Segmentation seg = new Segmentation(tree, directory,
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
					checkDisplayRange(e.getOldLeadSelectionPath()
							.getLastPathComponent());
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
				
				ImageNode imageNode = (ImageNode) node.getUserObject();

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
							.getImageName().contains("BF")) {
						BfPopup.show(evt.getComponent(), evt.getX(), evt.getY());
					} else if (((ImageNode) ((DefaultMutableTreeNode) selPath
							.getLastPathComponent()).getUserObject())
							.getImageName().contains("CFP")
							|| ((ImageNode) ((DefaultMutableTreeNode) selPath
									.getLastPathComponent()).getUserObject())
									.getImageName().contains("YFP")) {
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
							.getImageName().contains("BF")) {
						BfPopup.show(evt.getComponent(), evt.getX(), evt.getY());
					} else if (((ImageNode) ((DefaultMutableTreeNode) selPath
							.getLastPathComponent()).getUserObject())
							.getImageName().contains("CFP")
							|| ((ImageNode) ((DefaultMutableTreeNode) selPath
									.getLastPathComponent()).getUserObject())
									.getImageName().contains("YFP")) {
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

	public void createNodes(DefaultMutableTreeNode top, File directory) {

		List<String> fileNames = finder.find(directory);

		System.out.println("TAM: " + fileNames.size());
		int maxPositions = getMaxPosition();
		int maxTimeAllPosition = getMaxTime();
		for (int i = 1; i <= maxPositions; ++i) {
			PositionNode position = new PositionNode(i);
			// Adding position to root
			DefaultMutableTreeNode positionNode = new DefaultMutableTreeNode(
					position);
			top.add(positionNode);
			for (int j = 1; j <= maxTimeAllPosition; ++j) {
				TimeNode time = new TimeNode(position, j);
				DefaultMutableTreeNode timeNode = new DefaultMutableTreeNode(
						time);
				// Adding Time node to position
				positionNode.add(timeNode);
				File[] files = directory.listFiles(new NameFilter(position,
						time));
				time.setFiles(files);

				String name;
				name = getBf(files);
				if (name == null) {
					// nodo que no existe, indicarlo con imagen vacia y nombre
					// en color
					timeNode.add(new DefaultMutableTreeNode(new ImageNode(
							position, time, "Empty_BF", true)));
				} else {
					timeNode.add(new DefaultMutableTreeNode(new ImageNode(
							position, time, name, false)));
				}
				name = getBfOut(files);
				if (name == null) {
					// nodo que no existe, indicarlo con imagen vacia y nombre
					// en color
					timeNode.add(new DefaultMutableTreeNode(new ImageNode(
							position, time, "Empty_BF_OUT", true)));
				} else {
					timeNode.add(new DefaultMutableTreeNode(new ImageNode(
							position, time, name, false)));
				}
				name = getYfp(files);
				if (name == null) {
					// nodo que no existe, indicarlo con imagen vacia y nombre
					// en color
					timeNode.add(new DefaultMutableTreeNode(new ImageNode(
							position, time, "Empty_YFP", true)));
				} else {
					timeNode.add(new DefaultMutableTreeNode(new ImageNode(
							position, time, name, false)));
				}
				name = getYfpOut(files);
				if (name == null) {
					// nodo que no existe, indicarlo con imagen vacia y nombre
					// en color
					timeNode.add(new DefaultMutableTreeNode(new ImageNode(
							position, time, "Empty_YFP_OUT", true)));
				} else {
					timeNode.add(new DefaultMutableTreeNode(new ImageNode(
							position, time, name, false)));
				}
				name = getCfp(files);
				if (name == null) {
					// nodo que no existe, indicarlo con imagen vacia y nombre
					// en color
					timeNode.add(new DefaultMutableTreeNode(new ImageNode(
							position, time, "Empty_CFP", true)));
				} else {
					timeNode.add(new DefaultMutableTreeNode(new ImageNode(
							position, time, name, false)));
				}
				name = getCfpOut(files);
				if (name == null) {
					// nodo que no existe, indicarlo con imagen vacia y nombre
					// en color
					timeNode.add(new DefaultMutableTreeNode(new ImageNode(
							position, time, "Empty_CFP_OUT", true)));
				} else {
					timeNode.add(new DefaultMutableTreeNode(new ImageNode(
							position, time, name, false)));
				}
			}

		}
	}

	public int getMaxPosition() {

		// TODO: Para generalizar todavia mas podemos recibir un parametro
		// que identifique el nombre con el que empieza la posicion, o guardarlo
		// como variable de instancia de Position
		int max = 0;
		for (String name : fileNames) {
			int aux;
			// Busco el segundo separador para ver el identificador de la
			// posicion
			String[] info = name.split(finder.separator);
			String position = info[1];
			int endPos = position.length();
			int i;
			for (i = endPos - 1; position.charAt(i) >= '0'
					&& position.charAt(i) <= '9'; i--) {
				;
			}
			i++;
			System.out.println(i + " - " + endPos + " - "
					+ position.substring(endPos - i, endPos));
			aux = Integer.valueOf(position.substring(i, endPos));
			if (aux > max) {
				max = aux;
			}
		}
		System.out.println("Max Pos: " + max);
		return max;
	}

	public int getMaxTime(PositionNode position) {

		// TODO: Para generalizar todavia mas podemos recibir un parametro
		// que identifique el nombre con el que empieza el tiempo, o guardarlo
		// como variable de instancia de Time
		int max = 0;
		for (String name : fileNames) {
			int aux;

			if (name.toLowerCase().contains(position.toString())) {
				int endPos = name.toLowerCase().indexOf(".");
				int i;
				for (i = endPos - 1; name.charAt(i) >= '0'
						&& name.charAt(i) <= '9'; i--) {
					;
				}
				System.out.println("time: " + (i + 1) + " - " + endPos + " - "
						+ name.substring(i, endPos));
				aux = Integer.valueOf(name.substring(i + 1, endPos));
				if (aux > max) {
					max = aux;
				}

			}
		}
		// System.out.println("Max time:" + max);
		return max;
	}

	public int getMaxTime() {
		int max = 0;
		for (int i = 0; i < getMaxPosition(); ++i) {
			int currentMax = getMaxTime(new PositionNode(i));
			if (currentMax > max) {
				max = currentMax;
			}
		}
		return max;
	}

	private class NameFilter implements FilenameFilter {

		PositionNode position;
		TimeNode time;

		private NameFilter(PositionNode position, TimeNode time) {

			this.position = position;
			this.time = time;
		}

		public boolean accept(File dir, String name) {
			if (name.toLowerCase().contains(position.toString())
					&& name.toLowerCase().contains(time.toString())) {
				return true;
			} else {
				return false;
			}
		}

	}

	private String getBf(File[] files) {
		for (File file : files) {
			if (file.getName().toLowerCase().contains("bf")
					&& !file.getName().toLowerCase().contains(".out.tif")) {
				return file.getName();
			}
		}
		return null;
	}

	private String getBfOut(File[] files) {
		for (File file : files) {
			if (file.getName().toLowerCase().contains("bf")
					&& file.getName().toLowerCase().contains(".out.tif")) {
				return file.getName();
			}
		}
		return null;
	}

	private String getYfp(File[] files) {
		for (File file : files) {
			if (file.getName().toLowerCase().contains("yfp")
					&& !file.getName().toLowerCase().contains(".out.tif")) {
				return file.getName();
			}
		}
		return null;
	}

	private String getYfpOut(File[] files) {
		for (File file : files) {
			if (file.getName().toLowerCase().contains("yfp")
					&& file.getName().toLowerCase().contains(".out.tif")) {
				return file.getName();
			}
		}
		return null;
	}

	private String getCfp(File[] files) {
		for (File file : files) {
			if (file.getName().toLowerCase().contains("cfp")
					&& !file.getName().toLowerCase().contains(".out.tif")) {
				return file.getName();
			}
		}
		return null;
	}

	private String getCfpOut(File[] files) {
		for (File file : files) {
			if (file.getName().toLowerCase().contains("cfp")
					&& file.getName().toLowerCase().contains(".out.tif")) {
				return file.getName();
			}
		}
		return null;
	}

	public Map<Integer, DisplayRangeObject> getDisplayRanges() {
		return displayRanges;
	}

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
			ImageNode nodeInfo = (ImageNode) (node.getUserObject());
			if (nodeInfo.isFake()) {
				return true;
			}
			return false;

		}
	}

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

}
