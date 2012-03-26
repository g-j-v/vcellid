package utils;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class Output {
	
	JTree tree;
	File directory;
	
	public Output(JTree tree, File directory){
		this.tree = tree;
		this.directory = directory;
	}
	
	//TODO: Tomar de Segmentation.valor los valores para la salida de parameters
	public void generate(){

		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		int positions = tree.getModel().getChildCount(root);

		for(int i = 0; i < positions; ++i){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(i);
			String fileName = node.getUserObject().toString();
			
			File positionDirectory = createDirectory(i + 1);
			if(positionDirectory == null){
				System.out.println("Directory could not be created");
				return;
			}
			createFiles(positionDirectory);
			List<String> allPositionImages = getAllImages(node);
			for(String image: allPositionImages){
				appendToFiles(image,(i+1));
			}
		}
	}

	//Va agregando al archivo correspondiente los nombres de las imagenes
	private void appendToFiles(String image, int position ) {
		if(!image.toLowerCase().contains(".out")){

			if(image.toUpperCase().contains("BF")){

				File bfFile = new File(directory + "\\Position" + position + "\\bf_vcellid.txt");

				try {
					FileWriter writer = new FileWriter(bfFile,true);
					writer.append(directory + "\\Position" + position + "\\" + image + "\r\n");
					writer.append(directory + "\\Position" + position + "\\" + image + "\r\n");
					writer.close();
				} catch (IOException e) {
					System.out.println("Could not add image to bf_vcellid.txt");
					return;
				}

			}else if(image.toUpperCase().contains("YFP") || image.toUpperCase().contains("CFP")){

				File bfFile = new File(directory + "\\Position" + position + "\\fl_vcellid.txt");

				try {
					FileWriter writer = new FileWriter(bfFile,true);
					writer.append(directory + "\\Position" + position + "\\" +image + "\r\n");
					writer.close();
				} catch (IOException e) {
					System.out.println("Could not add image to fl_vcellid.txt");
					return;
				}
			}else{
				System.out.println("No image to add");
			}

		}
	}

	//Obtiene el path de todas las imagenes de la posicion
	private List<String> getAllImages(DefaultMutableTreeNode node) {
		List<String> images = new ArrayList<String>();
		DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(0);
		int i = -1;
		while(childNode != null){
			DefaultMutableTreeNode leaf = (DefaultMutableTreeNode) childNode.getChildAt(++i);
			images.add(leaf.getUserObject().toString());
			System.out.println(childNode.getChildCount() + " =?= " + (i+1) );
			if(childNode.getChildCount() == (i + 1)){
				childNode = childNode.getNextSibling();
				i = -1;
			}
		}
		return images;
	}

	//Crea los arvhivos que contienen las imagenes y el de paramentros
	private void createFiles(File positionDirectory) {
		File bf_file = new File(positionDirectory.getAbsoluteFile() + "\\bf_vcellid.txt");
		File fl_file = new File(positionDirectory.getAbsoluteFile() + "\\fl_vcellid.txt");
		File parameters_file = new File(positionDirectory.getAbsoluteFile() + "\\parameters_vcellid_out.txt");
		if(!bf_file.exists()){
			try {
				bf_file.createNewFile();
			} catch (IOException e) {
				System.out.println("bf_vcellid.txt could not be created");
				return;
			}
		}
		if(!fl_file.exists()){
			try {
				fl_file.createNewFile();
			} catch (IOException e) {
				System.out.println("fl_vcellid.txt could not be created");
				return;
			}
		}
		if(!parameters_file.exists()){
			try {
				parameters_file.createNewFile();
			} catch (IOException e) {
				System.out.println("parameters file could not be created");
				return;
			}
		}
		System.out.println("All files were successfully created");
	}

	//Crea el directorio de la posicion y lo retorna
	private File createDirectory(int i) {
		File positionDirectory = new File(directory + "\\Position" + i);
		if(positionDirectory.mkdir()){
			return positionDirectory;
		}
		return null;
	}

}
