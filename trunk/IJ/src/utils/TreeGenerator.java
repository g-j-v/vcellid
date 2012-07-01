package utils;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.ImageWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import CellId.Segmentation;

public class TreeGenerator {

	private Finder finder;
	private File directory;
	private List<String> fileNames;
	private Map<Integer, DisplayRangeObject> displayRanges;

	public TreeGenerator(Finder finder, File directory) {
		this.finder = finder;
		this.directory = directory;
		this.fileNames = finder.find(directory);
		this.displayRanges = new HashMap<Integer, DisplayRangeObject>();
	}

	public JTree generateTree() {
		final JTree tree;
		// For PopUp
		final JPopupMenu RootPosPopup = new JPopupMenu();
		final JPopupMenu TimeBfPopup = new JPopupMenu();

		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Images");
		createNodes(top, directory);
		tree = new JTree(top);
		RootPosPopup.add(new JMenuItem("Run")).addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {

						Segmentation seg = new Segmentation(tree, directory,
								false);
						seg.setVisible(true);

					}
				});
		RootPosPopup.add(new JMenuItem("Run BF")).addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Segmentation seg = new Segmentation(tree, directory,
								true);
						seg.setVisible(true);
					}
				});

		RootPosPopup.setInvoker(tree);

		TimeBfPopup.add(new JMenuItem("Run Position")).addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Segmentation seg = new Segmentation(tree, directory,
								false);
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
		TimeBfPopup.add(new JMenuItem("Test Time")).addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Segmentation seg = new Segmentation(tree, directory,
								true);
						seg.setVisible(true);
					}
				});

		TimeBfPopup.setInvoker(tree);

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

				Integer channel = node.getParent().getIndex(node);
				DisplayRangeObject dro = displayRanges.get(channel);

				String filePath = node.getUserObject().toString();
				System.out.println(directory.getAbsolutePath());
				System.out.println(filePath);

				ImagePlus imp = WindowManager.getCurrentImage();
				if (imp == null) {
					IJ.open(directory.getAbsolutePath()
							+ System.getProperty("file.separator") + filePath);
					if (dro != null) {
						imp = WindowManager.getCurrentImage();
						imp.setDisplayRange(dro.getMin(), dro.getMax(),
								dro.getChannels());
						ImageWindow win = imp.getWindow();
						if (win != null) {
							win.repaint();
							win.toFront();
						}
					}
				} else {
					System.out.println("Hay una abierta!");
					ImagePlus imp2 = IJ.openImage(directory.getAbsolutePath()
							+ System.getProperty("file.separator") + filePath);
					String newTitle = imp2.getTitle();

					if (dro != null) {
						imp2.setDisplayRange(dro.getMin(), dro.getMax(),
								dro.getChannels());
					}

					imp.setStack(newTitle, imp2.getStack());
					imp.setCalibration(imp2.getCalibration());
					imp.setFileInfo(imp2.getOriginalFileInfo());
					imp.setProperty("Info", imp2.getProperty("Info"));

					ImageWindow win = imp.getWindow();
					if (win != null) {
						win.repaint();
						win.toFront();
					}
				}
			}

			private void checkDisplayRange(Object lastPathComponent) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) lastPathComponent;
				Integer channel = node.getParent().getIndex(node);
				ImagePlus imp = WindowManager.getCurrentImage();
				if (imp == null) {
					return;
				}

				DisplayRangeObject dro = displayRanges.get(channel);
				if (dro == null) {
					dro = new DisplayRangeObject(imp.getDisplayRangeMin(), imp.getDisplayRangeMax(), imp.getNChannels());
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
									.getLastPathComponent()).getUserObject() instanceof Position) {
						RootPosPopup.show(evt.getComponent(), evt.getX(),
								evt.getY());
					} else if (((DefaultMutableTreeNode) selPath
							.getLastPathComponent()).getUserObject() instanceof Time
							|| ((String) ((DefaultMutableTreeNode) selPath
									.getLastPathComponent()).getUserObject())
									.contains("BF")) {
						TimeBfPopup.show(evt.getComponent(), evt.getX(),
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
									.getLastPathComponent()).getUserObject() instanceof Position) {
						RootPosPopup.show(evt.getComponent(), evt.getX(),
								evt.getY());
					} else if (((DefaultMutableTreeNode) selPath
							.getLastPathComponent()).getUserObject() instanceof Time
							|| ((String) ((DefaultMutableTreeNode) selPath
									.getLastPathComponent()).getUserObject())
									.contains("BF")) {
						TimeBfPopup.show(evt.getComponent(), evt.getX(),
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
		for (int i = 1; i <= maxPositions; ++i) {
			Position position = new Position(i);
			// Adding position to root
			DefaultMutableTreeNode positionNode = new DefaultMutableTreeNode(
					position);
			top.add(positionNode);
			int maxTimesForPosition = getMaxTime(position);
			// TODO: Para generar todo el arbol, reemplazar getMaxTime(position)
			// por getMaxTime()
			// de esta forma siempre se generar los nodos de tiempos
			for (int j = 1; j <= maxTimesForPosition; ++j) {
				Time time = new Time(position, j);
				DefaultMutableTreeNode timeNode = new DefaultMutableTreeNode(
						time);
				// Adding Time node to position
				positionNode.add(timeNode);
				File[] files = directory.listFiles(new NameFilter(position,
						time));
				time.setFiles(files);
				// TODO: Para generar todos los nodos de archivos, usar la
				// funcion getAllFiles()
				// para verificar todos los archivos que existen y en el
				// siguiente for
				// crear los nodos aunque no existan
				for (File f : files) {
					timeNode.add(new DefaultMutableTreeNode(f.getName()));
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

	public int getMaxTime(Position position) {

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
			int currentMax = getMaxTime(new Position(i));
			if (currentMax > max) {
				max = currentMax;
			}
		}
		return max;
	}

	private class NameFilter implements FilenameFilter {

		Position position;
		Time time;

		private NameFilter(Position position, Time time) {

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

	public Map<Integer, DisplayRangeObject> getDisplayRanges() {
		return displayRanges;
	}

}
