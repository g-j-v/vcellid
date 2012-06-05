package utils;

import ij.IJ;
import ij.gui.ImageCanvas;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import panels.PicturePanel;

public class TreeGenerator {

	private Finder finder;
	private File directory;
	private List<String> fileNames;

	public TreeGenerator(Finder finder, File directory) {
		this.finder = finder;
		this.directory = directory;
		this.fileNames = finder.find(directory);
	}

	public JTree generateTree(final PicturePanel picturePanel) {
		final JTree tree;
		//For PopUp
		final JPopupMenu popup = new JPopupMenu();
		popup.add(new JMenuItem("Test Cell Id")).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// LLamar a Segmentation en test
				
			}
		});
		popup.add(new JMenuItem("Run Cell Id")).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// LLamar a Segmentation en run
				
			}
		});
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Images");
		createNodes(top, directory);
		tree = new JTree(top);
		popup.setInvoker(tree);
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
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
				if (picturePanel != null) {
					picturePanel.setImage(new ImageCanvas(IJ
							.openImage(directory.getAbsolutePath() + System.getProperty("file.separator")
									+ filePath)));
					picturePanel.getImage().paint(picturePanel.getGraphics());
				}
				// IJ.open(directory.getAbsolutePath() + "/" +filePath);

			}
		});
		tree.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent evt) {
				if(evt.getButton() != MouseEvent.BUTTON3){
					return;
				}
				System.out.println("Largo el boton derecho");	
				TreePath selPath = tree.getPathForLocation(evt.getX(), evt.getY());
		        if (selPath == null){
		            return;
		        }else{
		            tree.setSelectionPath(selPath);
		        }
		        if (evt.isPopupTrigger()) {
		            popup.show(evt.getComponent(), evt.getX(), evt.getY());
		        }
				
			}
			
			@Override
			public void mousePressed(MouseEvent evt) {
				if(evt.getButton() != MouseEvent.BUTTON3){
					return;
				}
				System.out.println("Aprieto el boton derecho");	
				TreePath selPath = tree.getPathForLocation(evt.getX(), evt.getY());
		        if (selPath == null){
		            return;
		        }else{
		            tree.setSelectionPath(selPath);
		        }
		        if (evt.isPopupTrigger()) {
		            popup.show(evt.getComponent(), evt.getX(), evt.getY());
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
			//TODO: Para generar todo el arbol, reemplazar getMaxTime(position) por getMaxTime()
			//		de esta forma siempre se generar los nodos de tiempos
			for (int j = 1; j <= maxTimesForPosition; ++j) {
				Time time = new Time(position, j);
				DefaultMutableTreeNode timeNode = new DefaultMutableTreeNode(
						time);
				// Adding Time node to position
				positionNode.add(timeNode);
				File[] files = directory.listFiles(new NameFilter(position,
						time));
				time.setFiles(files);
				//TODO:	Para generar todos los nodos de archivos, usar la funcion getAllFiles()
				//		para verificar todos los archivos que existen y en el siguiente for
				//		crear los nodos aunque no existan
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
			for (i = endPos - 1; position.charAt(i) >= '0' && position.charAt(i) <= '9'; i--) {
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
	
	public int getMaxTime(){
		int max = 0;
		for(int i = 0; i < getMaxPosition(); ++i){
			int currentMax = getMaxTime(new Position(i));
			if(currentMax  > max){
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

}
