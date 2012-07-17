package utils;


import ij.IJ;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;


import CellId.Segmentation;

public class Output {
	
	JTree tree;
	File directory;
	boolean keepResults;
	CellIdProgressBar progressBar;
	String systemDirSeparator = System.getProperty("file.separator");
	String systemNewLineSeparator = System.getProperty("line.separator");
	
	public Output(JTree tree, File directory){
		this.tree = tree;
		this.directory = directory;
	}
	
	
	public void generateRun(){

		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		int positions = tree.getModel().getChildCount(root);
		this.keepResults = true;

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
			//True is passed because as we are not testing, and results should not go on Test folder
			loadParameters(i+1,keepResults);
			
		}
//		CellIdRunner.getInstance().run(directory);
	}

	public void generateBF(boolean keepResults){
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		int positions = tree.getModel().getChildCount(root);
		this.keepResults = keepResults;
		
		for(int i = 0; i < positions; ++i){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(i);
			String fileName = node.getUserObject().toString();
			
			File positionDirectory;
			if(keepResults){
				positionDirectory = createDirectory(i + 1);
			}else{
				positionDirectory = createTestDirectory(i + 1);
			}
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
				appendToBF(image,(i+1),keepResults);
			}
			loadParameters(i+1, keepResults);
			
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
		
		int positionsToRun;
		if(position == 0){
			positionsToRun = positions;
		}else{
			positionsToRun = 1;
		}
		Task task = new Task(positionsToRun);
		CellIdProgressBar progressBar = new CellIdProgressBar(task);
		progressBar.setVisible(true);
		task.addPropertyChangeListener(progressBar);
		task.execute();
		
		RunAndCleanPosition thread = new RunAndCleanPosition(task,directory,position,positions,keepResults);
		thread.start();

	}
	
	//Va agregando al archivo correspondiente los nombres de las imagenes BF
	private void appendToBF(String image, int position, boolean keepResults ) {
		if(!image.toLowerCase().contains(".out")){

			if(image.toUpperCase().contains("BF")){

				File bfFile;
				if(keepResults){
					bfFile = new File(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "bf_vcellid.txt");
				}else{
					bfFile = new File(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "Test" + systemDirSeparator + "bf_vcellid.txt");
				}

				try {
					FileWriter writer = new FileWriter(bfFile,true);
					if(keepResults){
						writer.append(directory + systemDirSeparator + image + "\r\n");
					}else{
						writer.append(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "Test" + systemDirSeparator + image + "\r\n");
					}
						writer.close();
				} catch (IOException e) {
					System.out.println("Could not add BF image to bf_vcellid.txt");
					return;
				}

				File FlFile;
				if(keepResults){
					FlFile = new File(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "fl_vcellid.txt");
				}else{
					FlFile = new File(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "Test" + systemDirSeparator +  "fl_vcellid.txt");
				}
				try {
					FileWriter writer = new FileWriter(FlFile,true);
					if(keepResults){
						writer.append(directory + systemDirSeparator + image + "\r\n");
					}else{
						writer.append(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "Test" + systemDirSeparator + image + "\r\n");
					}
					writer.close();
				} catch (IOException e) {
					System.out.println("Could not add BF image to fl_vcellid.txt");
					return;
				}
				copyImagesForTest(directory,position,image);
			}else{
				System.out.println("No image to add");
			}

		}
	}
	
	//Va agregando al archivo correspondiente los nombres de las imagenes BF y FL
	private void appendToFiles(String image, int position ) {
		if(!image.toLowerCase().contains(".out")){

			if(image.toUpperCase().contains("BF")){

				File bfFile = new File(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "bf_vcellid.txt");

				try {
					FileWriter writer = new FileWriter(bfFile,true);
					writer.append(directory + systemDirSeparator + image + "\r\n");
					writer.append(directory + systemDirSeparator + image + "\r\n");
					writer.close();
				} catch (IOException e) {
					System.out.println("Could not add BF image to bf_vcellid.txt");
					return;
				}

			}else if(image.toUpperCase().contains("YFP") || image.toUpperCase().contains("CFP")){

				File FlFile = new File(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "fl_vcellid.txt");

				try {
					FileWriter writer = new FileWriter(FlFile,true);
					writer.append(directory + systemDirSeparator +image + "\r\n");
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
		List<String> imagesFromPosition = new ArrayList<String>();
		boolean incompleteTime = false;
		while(childNode != null){
			DefaultMutableTreeNode leaf = (DefaultMutableTreeNode) childNode.getChildAt(++i);
			String fileName = leaf.getUserObject().toString();
			imagesFromPosition.add(fileName);
			if(checkEmpty(fileName)){
				incompleteTime = true;
			}
//			System.out.println(childNode.getChildCount() + " =?= " + (i+1) );
			if(childNode.getChildCount() == (i + 1)){
				if(!incompleteTime){
					images.addAll(imagesFromPosition);
				}
				incompleteTime = false;
				imagesFromPosition.clear();					
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
			String fileName = childNode.getUserObject().toString();
			if(checkEmpty(fileName)){
				images.clear();
				return images;
			}
			images.add(childNode.getUserObject().toString());
			childNode = childNode.getNextSibling();
		}
		return images;
	}

	//Checks if the filename is from a fake image
	private boolean checkEmpty(String fileName) {
		if(fileName.equals("Empty_BF") || fileName.equals("Empty_BF_OUT") || fileName.equals("Empty_YFP") || fileName.equals("Empty_YFP_OUT") || fileName.equals("Empty_CFP") || fileName.equals("Empty_CFP_OUT") ){
			return true;
		}
		return false;
	}


	//Crea los arvhivos que contienen las imagenes y el de paramentros
	private void createFiles(File positionDirectory) {
		File bf_file = new File(positionDirectory.getAbsoluteFile() + systemDirSeparator + "bf_vcellid.txt");
		File fl_file = new File(positionDirectory.getAbsoluteFile() + systemDirSeparator + "fl_vcellid.txt");
		File parameters_file = new File(positionDirectory.getAbsoluteFile() + systemDirSeparator + "parameters_vcellid_out.txt");
		if(!bf_file.exists()){
			try {
				bf_file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
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
		File positionDirectory = new File(directory + systemDirSeparator + "Position" + i);
		if(positionDirectory.exists()){
			for( File f: positionDirectory.listFiles()){
				f.delete();
			}
		}else{
			positionDirectory.mkdirs();
		}
		return positionDirectory;
	}
	
	//Crea el directorio de testeo de la posicion y lo retorna
		private File createTestDirectory(int i) {
			File positionDirectory = new File(directory + systemDirSeparator + "Position" + i + systemDirSeparator + "Test");
			if(positionDirectory.exists()){
				for( File f: positionDirectory.listFiles()){
					f.delete();
				}
			}else{
				positionDirectory.mkdirs();
			}
			return positionDirectory;
		}

	public void loadParameters(int position, boolean keepResults){
		
		File bfFile;
		if(keepResults){
			bfFile = new File(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "parameters_vcellid_out.txt");
		}else{
			bfFile = new File(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "Test" + systemDirSeparator + "parameters_vcellid_out.txt");
		}
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
	
	private void copyImagesForTest(File directory, int position, String imageName) {
		File destination = new File(directory.getAbsoluteFile()+ systemDirSeparator + "Position" + position + systemDirSeparator + "Test" + systemDirSeparator + imageName);
		InputStream inputStream;
		OutputStream outputStream;
		try {
			inputStream = new FileInputStream(directory + systemDirSeparator + imageName );
			outputStream = new FileOutputStream(destination);
		} catch (FileNotFoundException e) {
			System.out.println("Could not copy files for test");
			return;
		}
		int length;
		byte[] buffer = new byte[1024];
		try{
			while ((length = inputStream.read(buffer)) > 0){		 
				outputStream.write(buffer, 0, length);
			}			
			inputStream.close();
			outputStream.close();
		}catch(IOException e){
			System.out.println("Could not copy files for test");
		}	
		
	}

	private class RunAndCleanPosition extends Thread{
		
		Task task;
		File directory;
		int position;
		int maxPositions;
		boolean keepResults;
			
		public RunAndCleanPosition(Task task,File directory,int position, int maxPositions,boolean keepResults){
			this.task = task;
			this.directory = directory;
			this.position = position;
			this.maxPositions = maxPositions;
			this.keepResults = keepResults; 
		}
		
		@Override
		public void run() {
			
			System.out.println("Running.........");
			if(position > 0){
				CellIdRunner.getInstance().run(directory,position,keepResults);
				task.getPosition().getAndIncrement();
			}else{
				for(int i = 1; i <= maxPositions; ++i){
					CellIdRunner.getInstance().run(directory,i,keepResults);
					task.getPosition().getAndIncrement();
				}
			}
			
			if(keepResults){
				//TODO: Recargar el arbol
			}else{
				//TODO: Mostar la imagen de test
				IJ.open(directory.getAbsolutePath() + systemDirSeparator + "Position" + position + systemDirSeparator + "Test" + systemDirSeparator + name );
			}
			
			//To test how progress bar works
//			for(int i = 0; i < 30; ++i){
//				try {
//					Random r = new Random();
//					int lala = r.nextInt(10000);
//					Thread.sleep(lala);
//					System.out.println("waiting " + lala + " sec");
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				task.getPosition().getAndIncrement();
//			}
		}
	}
	
	
}
