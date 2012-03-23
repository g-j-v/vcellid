/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author alejandropetit
 */
public class Finder {

    //storing the Directory to look for files and the patterns separator

    String separator;
    List<String> patterns;


    public Finder(String separator , List<String> patterns){
        //Indicar el separador de tiempo y posicion como variable.
        this.separator = separator;
        this.patterns = patterns;
    }

    public Finder(List<String> patterns){

        // The default separator is the lower dash "_"
        this("_",patterns);
    }

    //recieves the directory where to look files, and returns a list of files that match.
    public List<String> find(File directory){

    	List<String> fileNames = new ArrayList<String>();
    	
        String pattern = "";
        for(String str : patterns){
            pattern += str;
            pattern += separator;
        }
        
        
        pattern = pattern.substring(0, pattern.length()-1);
        System.out.println("pattern: " + pattern);
        Pattern pat = Pattern.compile(pattern);
        for(String fileName : directory.list()){
        	System.out.println("-" + pat.pattern() + "-");
        	System.out.println("-" + fileName + "-");
            Matcher matcher = pat.matcher(fileName);
            if(matcher.matches()){
            	
                fileNames.add(matcher.group());
            }

        }
        
        return fileNames;
    }

}
