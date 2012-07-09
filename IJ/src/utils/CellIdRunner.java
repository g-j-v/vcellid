package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CellId.Segmentation;

public class CellIdRunner {
	
	String cellIdPath;
	private static CellIdRunner instance;
	
	
	public CellIdRunner(){
	}
	
	public static CellIdRunner getInstance(){
		if(instance == null){
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

	

	public void run(File directory, int position, boolean keepResults){
		try
		{
//			Runtime rt = Runtime.getRuntime();
//			Process p = rt.exec(cellIdPath);
			
			System.out.println("Running for " + directory.getAbsoluteFile());
			
			List<String> command = new ArrayList<String>();
			command.add(cellIdPath);
			command.add("--bright");
			if(keepResults){
				command.add(directory.getAbsolutePath() + "\\Position"+ position + "\\bf_vcellid.txt");
			}else{
				command.add(directory.getAbsolutePath() + "\\Position"+ position + "\\Test\\bf_vcellid.txt");
			}
			command.add("--fluor");
			if(keepResults){
				command.add(directory.getAbsolutePath() + "\\Position" + position + "\\fl_vcellid.txt");				
			}else{
				command.add(directory.getAbsolutePath() + "\\Position" + position + "\\Test\\fl_vcellid.txt");	
			}
			if(keepResults){
				command.add("--param=" + directory.getAbsolutePath() + "\\Position" + position + "\\parameters_vcellid_out.txt");
			}else{
				command.add("--param=" + directory.getAbsolutePath() + "\\Position" + position + "\\Test\\parameters_vcellid_out.txt");
			}
			if(keepResults){
				command.add("--output");
				command.add(directory.getAbsolutePath() + "\\Position" + position + "\\Test");
			}
			if(Segmentation.isParameterCheckBoxSelected()){
				String params[] = Segmentation.getParameters().split(" ");
				for(String str: params){
					command.add(str);
				}
			}
//			System.out.println(System.getProperty("user.dir"));
//			System.setProperty("user.dir", directory.getAbsolutePath());
//			System.out.println(System.getProperty("user.dir"));
			ProcessBuilder processBuilder = new ProcessBuilder(command);
			processBuilder.directory(directory.getAbsoluteFile());
			System.out.println(processBuilder.directory());
			Process p = processBuilder.start();
			InputStream in = p.getInputStream();
			OutputStream out = p.getOutputStream ();
			InputStream err = p.getErrorStream();
	 
			//do whatever you want
			//some more code
			String line;
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);

			while ((line = br.readLine()) != null) {
			  System.out.println(line);
			}
	 
			p.destroy() ;
		}catch(Exception exc){/*handle exception*/}
	}
}
