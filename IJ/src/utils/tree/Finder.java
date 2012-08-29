package utils.tree;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class looks for images in the given directory according to the patterns specified
 * @author alejandropetit
 */
public class Finder {

    String separator;
    List<String> patterns;


    /**
     * Constructor
     * @param separator used between channel, position and time indicator
     * @param patterns to match
     */
    public Finder(String separator , List<String> patterns){
        //Indicar el separador de tiempo y posicion como variable.
        this.separator = separator;
        this.patterns = patterns;
    }

    /**
     * Constructor. Considers "_" as the default separator
     * @param patterns to match
     */
    public Finder(List<String> patterns){

        // The default separator is the lower dash "_"
        this("_",patterns);
    }

    /**
     * Looks for images
     * @param directory where to look for the files.
     * @return list of files found on the directory that matched 
     */
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
