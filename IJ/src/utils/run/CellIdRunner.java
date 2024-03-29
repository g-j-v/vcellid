/**
 * Singleton class that runs cellid
 * @author Alejandro Petit
 */

package utils.run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utils.ImageLoadingPaths;
import utils.SegmentationValues;
import cellid.Segmentation;

public class CellIdRunner {

	/**
	 * Variables
	 */
	String cellIdPath;
	private static CellIdRunner instance;
	String systemDirSeparator = System.getProperty("file.separator");

	/**
	 * Default constructor
	 */
	public CellIdRunner() {
	}

	/**
	 * Returns the instance of CellIdRunner, of a new one if it does not exist-
	 * 
	 * @return Instance of CellIdRunner
	 */
	public static CellIdRunner getInstance() {
		if (instance == null) {
			instance = new CellIdRunner();
		}
		return instance;
	}

	public String getCellIdPath() {
		return cellIdPath;
	}

	public void setCellIdPath(String cellIdPath) {
		this.cellIdPath = cellIdPath;
	}

	/**
	 * Creates the process to run cellid and logs its output on a file
	 * 
	 * @param directory
	 *            where the images can be found
	 * @param position
	 *            to run
	 * @param keepResults
	 *            if the execution is a test or not. If keepResutls is false,
	 *            execution is done in Test directory inside position
	 * @throws Exception 
	 */
	public void run(File directory, int position, boolean keepResults) throws Exception {
		try {
			// Runtime rt = Runtime.getRuntime();
			// Process p = rt.exec(cellIdPath);

			if (keepResults) {
				System.out
						.println("Running for " + directory.getAbsoluteFile());
			} else {
				System.out.println("Running for " + directory.getAbsoluteFile()
						+ systemDirSeparator + "Position" + position
						+ systemDirSeparator + "Test");
			}

			List<String> command = new ArrayList<String>();
			command.add(cellIdPath);
			command.add("--bright");
			if (keepResults) {
				command.add(directory.getAbsolutePath() + systemDirSeparator
						+ "Position" + position + systemDirSeparator
						+ "bf_vcellid.txt");
			} else {
				command.add(directory.getAbsolutePath() + systemDirSeparator
						+ "Position" + position + systemDirSeparator + "Test"
						+ systemDirSeparator + "bf_vcellid.txt");
			}
			command.add("--fluor");
			if (keepResults) {
				command.add(directory.getAbsolutePath() + systemDirSeparator
						+ "Position" + position + systemDirSeparator
						+ "fl_vcellid.txt");
			} else {
				command.add(directory.getAbsolutePath() + systemDirSeparator
						+ "Position" + position + systemDirSeparator + "Test"
						+ systemDirSeparator + "fl_vcellid.txt");
			}
			if (keepResults
					&& SegmentationValues.getInstance().isNucleusFromChannel()) {
				command.add("--third");
				command.add(directory.getAbsolutePath() + systemDirSeparator
						+ "Position" + position + systemDirSeparator
						+ "nuc_vcellid.txt");
			}
			command.add("--param");
			if (keepResults) {
				command.add(directory.getAbsolutePath() + systemDirSeparator
						+ "Position" + position + systemDirSeparator
						+ "parameters_vcellid_out.txt");
			} else {
				command.add(directory.getAbsolutePath() + systemDirSeparator
						+ "Position" + position + systemDirSeparator + "Test"
						+ systemDirSeparator + "parameters_vcellid_out.txt");
			}
			if (!keepResults) {
				command.add("--output");
				command.add(directory.getAbsolutePath() + systemDirSeparator
						+ "Position" + position + systemDirSeparator + "Test"
						+ systemDirSeparator + "out");
			} else {
				command.add("--output");
				command.add(directory.getAbsolutePath() + systemDirSeparator
						+ "Position" + position + systemDirSeparator + "out");
			}
			if (Segmentation.isParameterCheckBoxSelected()) {
				String params[] = Segmentation.getParameters().split(" ");
				for (String str : params) {
					command.add(str);
				}
			}

			ImageLoadingPaths imageLoadingPaths = ImageLoadingPaths
					.getInstance();

			if (imageLoadingPaths.isUiCheck()) {
				String path;
				if (imageLoadingPaths.isForcePath()) {
					path = imageLoadingPaths.getBfPath();
				} else {
					path = imageLoadingPaths.getUiPath();
				}
				command.add("--flat");
				command.add(path + systemDirSeparator
						+ imageLoadingPaths.getUiBasename());
			}
			if (imageLoadingPaths.isCbCheck()) {
				String path;
				if (imageLoadingPaths.isForcePath()) {
					path = imageLoadingPaths.getBfPath();
				} else {
					path = imageLoadingPaths.getCbPath();
				}
				command.add("--dark");
				command.add(path + systemDirSeparator
						+ imageLoadingPaths.getCbToken());
			}

			ProcessBuilder processBuilder = new ProcessBuilder(command);
			processBuilder.redirectErrorStream(true);
			if (keepResults) {
				processBuilder.directory(directory.getAbsoluteFile()); 
			} else {
				processBuilder.directory(new File(directory.getAbsoluteFile()
						+ systemDirSeparator + "Position" + position
						+ systemDirSeparator + "Test"));
			}
			System.out.println(processBuilder.directory());
			System.out.println(command);
			Process p = processBuilder.start();
			InputStream in = p.getInputStream();

			String line;
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);

			File file;
			try {
				file = new File(directory.getAbsoluteFile()
						+ systemDirSeparator
						+ "LOG_VCellID_"
						+ (keepResults ? "Run_" : "Test_")
						+ (new Date().toString().replace(" ", "_").replace(":",
								"-")) + ".txt");
				if (!file.exists()) {
					System.out.println("Creating log file: " + file);
					file.createNewFile();
				}
				FileWriter writer = new FileWriter(file, true);

				while ((line = br.readLine()) != null) {
					writer.append(line + "\r\n");

				}
				writer.close();
			} catch (IOException e) {
				throw e;
			}
			p.waitFor();
		} catch (Exception exc) {
			throw exc;
		}
	}
}
