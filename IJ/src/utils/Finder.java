/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import ij.IJ;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

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
        Pattern pat = Pattern.compile(pattern);
        for(String childs : directory.list()){
            Matcher matcher = pat.matcher(childs);
            if(matcher.matches()){
                fileNames.add(matcher.group());
            }

        }
        
        return fileNames;
    }

}
