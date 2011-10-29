/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CellId;

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

    File directory;
    String separator;

    public Finder(File directory, String separator){
        setDirectory(directory);
        this.separator = separator;
    }

    public Finder(File directory){

        // The default separator is the lower dash "_"
        this(directory,"_");
    }

    //recieves the patterns in the order they should appear
    public List<String> find(List<String> patterns){

        List<String> founded = new ArrayList<String>();

        String pattern = "";
        for(String str : patterns){
            pattern += str;
            pattern += separator;
        }
        pattern = pattern.substring(0, pattern.length()-1);
        Pattern pat = Pattern.compile(pattern);
        for(String childs : directory.list()){
            Matcher matcher = pat.matcher(childs);
            if(matcher.matches()){
                founded.add(matcher.group());
            }

        }
        return founded;
    }

    public void setDirectory(File file){
        if(!file.isDirectory()){
            throw new IllegalArgumentException("Path is not a directory");
        }else{
            this.directory = file;
        }
    }
}
