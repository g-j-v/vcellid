/**
 * Class to generate output files to run cellid
 * @author Alejandro Petit
 */

package utils.run;

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

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import utils.ImageLoadingPaths;
import utils.SegmentationValues;
import utils.node.ImageNode;
import utils.node.PositionNode;
import utils.tree.PositionImage;
import cellid.Segmentation;





public class Output {
	
	JTree tree;
	File directory;
	List<String> flourChannels;
	boolean keepResults;
	CellIdProgressBar progressBar;
	String systemDirSeparator = System.getProperty("file.separator");
	String systemNewLineSeparator = System.getProperty("line.separator");
	
	/**
	 * Constructor
	 * @param tree with images
	 * @param directory where to find the images
	 */
	public Output(JTree tree, File directory, List<String> fluorChannels){
		this.tree = tree;
		this.directory = directory;
		this.flourChannels = fluorChannels;
	}
	
	/**
	 * Generates the files considering a run with BF and FL images
	 */
	public void generateRun(){
		TreePath selected = tree.getSelectionPath();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		int positions;
		this.keepResults = true;
		
		boolean singlePosition;
		if(selected.getParentPath() == null){
			positions = tree.getModel().getChildCount(root);;
			singlePosition = false;
		}else{
			positions = ((PositionNode)((DefaultMutableTreeNode)selected.getPathComponent(1)).getUserObject()).getNumber();
			singlePosition = true;
		}

		
		for(int i = 0; i < positions; ++i){
			if(singlePosition){
				i = positions -1; 
			}
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(i);
			String fileName = node.getUserObject().toString();
			
			File positionDirectory = createDirectory(i + 1);
			if(positionDirectory == null){
				System.out.println("Directory could not be created");
				return;
			}
			createFiles(positionDirectory);
			if(SegmentationValues.getInstance().isNucleusFromChannel()){
				createNucFile(positionDirectory);
			}
			List<PositionImage> allPositionImages = getAllImages(node);
			for(PositionImage image: allPositionImages){
				appendToFiles(image,(i+1));
			}
			//True is passed because as we are not testing, and results should not go on Test folder
			loadParameters(i+1,keepResults);
			
		}
	}

	/**
	 * Generates files considering only BF files. bf_file.txt = fl_file.txt both with BF images 
	 * @param keepResults to diferenciate if its a run or a test. If keepResults is false, files are created in Test directory
	 */
	public void generateBF(boolean keepResults){
		
		TreePath selected = tree.getSelectionPath();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		int positions = tree.getModel().getChildCount(root);
		this.keepResults = keepResults;
		
		boolean singlePosition;
		if(selected.getParentPath() == null){
			positions = tree.getModel().getChildCount(root);;
			singlePosition = false;
		}else{
			positions = ((PositionNode)((DefaultMutableTreeNode)selected.getPathComponent(1)).getUserObject()).getNumber();
			singlePosition = true;
		}

		
		for(int i = 0; i < positions; ++i){
			
			if(singlePosition){
				i = positions -1; 
			}
			
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
			List<PositionImage> allPositionImages;
			if(tree.getSelectionPath().getParentPath() == null ||
					((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent()).getUserObject() instanceof PositionNode ){
				allPositionImages = getAllImages(node);				
			}else{
				allPositionImages = getTimeImages((DefaultMutableTreeNode)tree.getSelectionPath().getPathComponent(2));
			}
			for(PositionImage image: allPositionImages){
				appendToBF(image,(i+1),keepResults);
			}
			loadParameters(i+1, keepResults);
			
		}
	}
	
	/**
	 * Creates the progress bar, and the thread to run cellid
	 */
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
	
	/**
	 * Adds to the corresponding file the name of the image
	 * @param image to add to the file
	 * @param position where to add the image
	 * @param keepResults indicated if its a test. If keepResults is true, image is added to files in Test directory
	 */
	private void appendToBF(PositionImage image, int position, boolean keepResults ) {
		if(!image.isOut()){

			if(image.isBF()){

				File bfFile;
				if(keepResults){
					bfFile = new File(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "bf_vcellid.txt");
				}else{
					bfFile = new File(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "Test" + systemDirSeparator + "bf_vcellid.txt");
				}

				try {
					FileWriter writer = new FileWriter(bfFile,true);
					if(keepResults){
						if(image.isEmpty()){
							writer.append(directory + systemDirSeparator + "EmptyImage.tiff\r\n");
						}else{
							writer.append(directory + systemDirSeparator + image + "\r\n");
						}
					}else{
						if(image.isEmpty()){
							writer.append(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "Test" + systemDirSeparator + "EmptyImage.tiff\r\n");		
						}else{
							writer.append(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "Test" + systemDirSeparator + image + "\r\n");
						}
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
						if(image.isEmpty()){
							writer.append(directory + systemDirSeparator + "EmptyImage.tiff\r\n");
						}else{
							writer.append(directory + systemDirSeparator + image + "\r\n");
						}
					}else{
						if(image.isEmpty()){
							writer.append(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "Test" + systemDirSeparator + "EmptyImage.tiff\r\n");
						}else{
							writer.append(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "Test" + systemDirSeparator + image + "\r\n");
						}
					}
					writer.close();
				} catch (IOException e) {
					System.out.println("Could not add BF image to fl_vcellid.txt");
					return;
				}
				if(image.isEmpty()){
					copyImagesForTest(directory, position, "EmptyImage.tiff", null,true);
				}else{						
					copyImagesForTest(directory,position,image.toString(), null,true);
				}
			}else{
				System.out.println("No image to add");
			}

		}
	}
	
	//Va agregando al archivo correspondiente los nombres de las imagenes BF y FL
	/**
	 * Adds to the corresponding file the name of the image
	 * @param image to add to the files
	 * @param position where to add the image
	 */
	private void appendToFiles(PositionImage image, int position ) {

		if(!image.isOut()){
			if(image.isBF()){

				File bfFile = new File(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "bf_vcellid.txt");

				try {
					
					FileWriter writer = new FileWriter(bfFile,true);
					for(String fluorChannel : flourChannels){
						if(image.isEmpty()){
							writer.append(directory + systemDirSeparator + "EmptyImage.tiff\r\n");
						}else{
							writer.append(directory + systemDirSeparator + image + "\r\n");						
						}	
					}
					
					writer.close();
				} catch (IOException e) {
					System.out.println("Could not add BF image to bf_vcellid.txt");
					return;
				}

			}else if(image.isFL()){

				File FlFile = new File(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "fl_vcellid.txt");
				File NucFile = null;
				if(SegmentationValues.getInstance().isNucleusFromChannel()){
					NucFile = new File(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "nuc_vcellid.txt");
				}
				try {
					FileWriter writer = new FileWriter(FlFile,true);
					FileWriter nucWriter = null;
					if(NucFile != null){
						nucWriter = new FileWriter(NucFile,true);
					}
					if(image.isEmpty()){
						writer.append(directory + systemDirSeparator + "EmptyImage.tiff\r\n");
						if(nucWriter != null){
							nucWriter.append(directory + systemDirSeparator + "EmptyImage.tiff\r\n");
						}
					}else{
						writer.append(directory + systemDirSeparator + image + "\r\n");
						if(nucWriter != null){
							nucWriter.append(directory + systemDirSeparator + "3" + image.toString().substring(1) + "\r\n");
							if(image.getChannel().equals(SegmentationValues.getInstance().getNucleusChannel())){
								copyImagesForTest(directory, position, image.toString() , "3" + image.toString().substring(1), false);
							}
						}
					}
					writer.close();
					if(nucWriter != null){
						nucWriter.close();
					}
				} catch (IOException e) {
					System.out.println("Could not add FL image to fl_vcellid.txt");
					return;
				}
			}else{
				System.out.println("No image to add");
			}

		}
	}

	/**
	 * Gets the name of all the images under that node
	 * @param node where to look for the images.
	 * @return list of all the images under the node
	 */
	private List<PositionImage> getAllImages(DefaultMutableTreeNode node) {
		List<PositionImage> images = new ArrayList<PositionImage>();
		DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(0);
		int i = -1;
//		List<String> imagesFromPosition = new ArrayList<String>();
//		boolean incompleteTime = false;
		while(childNode != null){
			DefaultMutableTreeNode leaf = (DefaultMutableTreeNode) childNode.getChildAt(++i);
			images.add(((ImageNode)leaf.getUserObject()).getImage());
//			imagesFromPosition.add(fileName);
//			if(checkEmpty(fileName)){
//				incompleteTime = true;
//			}
//			System.out.println(childNode.getChildCount() + " =?= " + (i+1) );
			if(childNode.getChildCount() == (i + 1)){
//				if(!incompleteTime){
//					images.addAll(imagesFromPosition);
//				}
//				incompleteTime = false;
//				imagesFromPosition.clear();					
				childNode = childNode.getNextSibling();
				i = -1;
			}
		}
		return images;
	}
	
	/**
	 * Obtains the time images corresponding to a node
	 * @param node where to look for the images
	 * @return the images found
	 */
	private List<PositionImage> getTimeImages(DefaultMutableTreeNode node){
		List<PositionImage> images = new ArrayList<PositionImage>();
		DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(0);
		while(childNode != null){
//			String fileName = childNode.getUserObject().toString();
//			if(checkEmpty(fileName)){
//				images.clear();
//				return images;
//			}
			images.add(((ImageNode)childNode.getUserObject()).getImage());
			childNode = childNode.getNextSibling();
		}
		return images;
	}
	
	/**
	 * Reads output image in test directory
	 * @param position where to look for the image
	 * @return the file name of the image.
	 */
	private String readOutputfromTest(int position){
		File file = new File(directory + systemDirSeparator + "Position" + position + systemDirSeparator + "Test");
		String[] filenames = file.list();
		for(String files: filenames){
			if(files.toLowerCase().contains(".out") && files.toLowerCase().contains(".tif") ){
				return files;
			}
		}
		return null;
	}


	/**
	 * Creates the files for BF images, FL images and parameters
	 * @param positionDirectory where to create the file
	 */
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

	/**
	 * 
	 * @param positionDirectory directory where to create the nuc_vcellid.txt file.
	 */
	private void createNucFile(File positionDirectory) {
		File nuc_file = new File(positionDirectory.getAbsoluteFile() + systemDirSeparator + "nuc_vcellid.txt");
			if(!nuc_file.exists()){
			try {
				nuc_file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("nuc_vcellid.txt could not be created");
				return;
			}
		}
		System.out.println("All files were successfully created");
	}

	
	/**
	 * Creates the position for a given directory 
	 * @param i is the number of the position
	 * @return
	 */
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

	/**
	 * Adds the parameters values to the corresponding file
	 * @param position where to find the file to be filled
	 * @param keepResults indicated which file to use. If its false, Test directory is used.
	 */
	public void loadParameters(int position, boolean keepResults){
		
		SegmentationValues segmentationValues = SegmentationValues.getInstance();
		
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
			if(segmentationValues.isBFasFLflag()){
				writer.append("treat_brightfield_as_fluorescence_also\r\n");
			}
			if(segmentationValues.isNucleusFromChannel()){
				//Not sure yet what to do.
				;
			}
			if(segmentationValues.isFretImage()){
				writer.append("fret bf_bottom_and_top\r\n");
				writer.append("fret nuclear_"+segmentationValues.getFretImageValue()+"\r\n");
			}
			if( keepResults && segmentationValues.isNucleusFromChannel()){
				writer.append("third_image nuclear_label\r\n");
			}

//			writer.append(" fret bf_bottom_and_top\r\n");
//			writer.append(" fret nuclear_top\r\n");
			//TODO: aca van parametros sin valores
			writer.close();
		} catch (IOException e) {
			System.out.println("Could not add image to fl_vcellid.txt");
			return;
		}
	}
	
	/**
	 * Makes a copy of the BF to the Test directory to run cellid in test mode
	 * @param directory where to find the images
	 * @param position in which image should be copied. This value is ignored if inTime is false
	 * @param name of the new image. If this value is null or empty same name as default image will be used.
	 * @param imageName to copy
	 * @param inTime if true image will be copied in Test folder if not in default directory
	 */
	private void copyImagesForTest(File directory, int position, String imageName, String targetName, boolean inTest) {
		File destination;
		if(targetName == null || targetName.trim().isEmpty()){
			targetName = imageName;
		}
		if(inTest){
			destination = new File(directory.getAbsoluteFile()+ systemDirSeparator + "Position" + position + systemDirSeparator + "Test" + systemDirSeparator + targetName);			
		}else{
			destination = new File(directory.getAbsoluteFile()+ systemDirSeparator  + targetName);
		}
		InputStream inputStream;
		OutputStream outputStream;
		try {
			inputStream = new FileInputStream(directory + systemDirSeparator + imageName );
			outputStream = new FileOutputStream(destination);
		} catch (FileNotFoundException e) {
			System.out.println("Could not copy files images");
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
			System.out.println("Could not copy files images");
		}	
		
	}

	/**
	 * Thread to run cellid.
	 * @author alejandropetit
	 *
	 */
	private class RunAndCleanPosition extends Thread{
		
		Task task;
		File directory;
		int position;
		int maxPositions;
		boolean keepResults;
			
		/**
		 * Constructor
		 * @param task which controls changes.
		 * @param directory where images are found
		 * @param position to run or, 0 for all positions
		 * @param maxPositions in the tree
		 * @param keepResults to indentify a test
		 */
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
				String imageName = readOutputfromTest(position);
				if(imageName != null){
					IJ.open(directory.getAbsolutePath() + systemDirSeparator + "Position" + position + systemDirSeparator + "Test" + systemDirSeparator + imageName );
				}
			}
		}
	}
	
	
}
