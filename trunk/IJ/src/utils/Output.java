package utils;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import CellId.Segmentation;

public class Output {
	
	JTree tree;
	File directory;
	
	public Output(JTree tree, File directory){
		this.tree = tree;
		this.directory = directory;
	}
	
	
	public void generateRun(){

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
			loadParameters(i+1);
			
		}
//		CellIdRunner.getInstance().run(directory);
	}

	public void generateTest(){
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
			List<String> allPositionImages;
			if(tree.getSelectionPath().getParentPath() == null ||
					((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent()).getUserObject() instanceof PositionNode ){
				allPositionImages = getAllImages(node);				
			}else{
				allPositionImages = getTimeImages((DefaultMutableTreeNode)tree.getSelectionPath().getPathComponent(2));
			}
			for(String image: allPositionImages){
				appendToBF(image,(i+1));
			}
			loadParameters(i+1);
			
		}
	}
	
	public void run(){
		TreePath selected = tree.getSelectionPath();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		int positions = tree.getModel().getChildCount(root);
		int position;
		
		if(selected.getParentPath() == null){
			position = 0;
		}else{
			position = ((PositionNode)((DefaultMutableTreeNode)selected.getPathComponent(1)).getUserObject()).getNumber();
		}
		if(position > 0){
			CellIdRunner.getInstance().run(directory,position);			
		}else{
			for(int i = 1; i <= positions; ++i){
				CellIdRunner.getInstance().run(directory,i);
			}
		}

	}
	
	//Va agregando al archivo correspondiente los nombres de las imagenes BF
	private void appendToBF(String image, int position ) {
		if(!image.toLowerCase().contains(".out")){

			if(image.toUpperCase().contains("BF")){

				File bfFile = new File(directory + "\\Position" + position + "\\bf_vcellid.txt");

				try {
					FileWriter writer = new FileWriter(bfFile,true);
					writer.append(directory + "\\" + image + "\r\n");
					writer.close();
				} catch (IOException e) {
					System.out.println("Could not add BF image to bf_vcellid.txt");
					return;
				}

				File FlFile = new File(directory + "\\Position" + position + "\\fl_vcellid.txt");

				try {
					FileWriter writer = new FileWriter(FlFile,true);
					writer.append(directory + "\\" +image + "\r\n");
					writer.close();
				} catch (IOException e) {
					System.out.println("Could not add BF image to fl_vcellid.txt");
					return;
				}
			}else{
				System.out.println("No image to add");
			}

		}
	}
	
	//Va agregando al archivo correspondiente los nombres de las imagenes BF y FL
	private void appendToFiles(String image, int position ) {
		if(!image.toLowerCase().contains(".out")){

			if(image.toUpperCase().contains("BF")){

				File bfFile = new File(directory + "\\Position" + position + "\\bf_vcellid.txt");

				try {
					FileWriter writer = new FileWriter(bfFile,true);
					writer.append(directory + "\\" + image + "\r\n");
					writer.append(directory + "\\" + image + "\r\n");
					writer.close();
				} catch (IOException e) {
					System.out.println("Could not add BF image to bf_vcellid.txt");
					return;
				}

			}else if(image.toUpperCase().contains("YFP") || image.toUpperCase().contains("CFP")){

				File FlFile = new File(directory + "\\Position" + position + "\\fl_vcellid.txt");

				try {
					FileWriter writer = new FileWriter(FlFile,true);
					writer.append(directory + "\\" +image + "\r\n");
					writer.close();
				} catch (IOException e) {
					System.out.println("Could not add FL image to fl_vcellid.txt");
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
	
	private List<String> getTimeImages(DefaultMutableTreeNode node){
		List<String> images = new ArrayList<String>();
		DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(0);
		while(childNode != null){
			images.add(childNode.getUserObject().toString());
			childNode = childNode.getNextSibling();
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
		if(positionDirectory.exists()){
			for( File f: positionDirectory.listFiles()){
				f.delete();
			}
		}
		return positionDirectory;
	}

	public void loadParameters(int position){
		File bfFile = new File(directory + "\\Position" + position + "\\parameters_vcellid_out.txt");
		try {
			FileWriter writer = new FileWriter(bfFile,true);
			writer.append(" max_split_over_minor " + Segmentation.getMaxSplitOverMinor() + "\r\n");
			writer.append(" max_dist_over_waist " + Segmentation.maxDistOverWaist() + "\r\n");
			writer.append(" max_pixels_per_cell " + Segmentation.maxPixelsPerCell() + "\r\n");
			writer.append(" min_pixels_per_cell " + Segmentation.minPixelsPerCell() + "\r\n");
			writer.append(" background_reject_factor " + Segmentation.backgroundRejectFactor() + "\r\n");
			writer.append(" tracking_comparison " + Segmentation.trackingComparison() + "\r\n");
			writer.append(" " + Segmentation.cellAlignment() + "\r\n");
			writer.append(" " + Segmentation.frameAlignment() + "\r\n");
			writer.append(" image_type brightfield\r\n");
			writer.append(" bf_fl_mapping list\r\n");
			writer.append(" fret bf_bottom_and_top\r\n");
			writer.append(" fret nuclear_top\r\n");
			//TODO: aca van parametros sin valores
			writer.close();
		} catch (IOException e) {
			System.out.println("Could not add image to fl_vcellid.txt");
			return;
		}
	}
}
