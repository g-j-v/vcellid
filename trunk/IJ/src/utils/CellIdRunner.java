package utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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

	

	public void run(){
		try
		{
//			Runtime rt = Runtime.getRuntime();
//			Process p = rt.exec(cellIdPath);
			List<String> command = new ArrayList<String>();
			command.add(cellIdPath);
			command.add("--bright");
			command.add("bf_vcellid.txt");
			Process p = new ProcessBuilder(cellIdPath).start();
			System.out.println(p);
			InputStream in = p.getInputStream();
			OutputStream out = p.getOutputStream ();
			InputStream err = p.getErrorStream();
	 
			//do whatever you want
			//some more code
	 
			p.destroy() ;
		}catch(Exception exc){/*handle exception*/}
	}
}
