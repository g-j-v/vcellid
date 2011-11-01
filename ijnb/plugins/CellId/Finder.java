/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CellId;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author alejandropetit
 */
public class Finder {

    //storing the Directory to look for files and the patterns separator

    File directory;
    String separator;
    List<String> fileNames;

    public Finder(File directory, String separator , List<String> patterns){
        //Indicar el separador de tiempo y posicion como variable.
        setDirectory(directory);
        this.separator = separator;
        this.fileNames = new ArrayList<String>();
        find(patterns);
    }

    public Finder(File directory,List<String> patterns){

        // The default separator is the lower dash "_"
        this(directory,"_",patterns);
    }

    //recieves the patterns in the order they should appear
    private void find(List<String> patterns){

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
    }

    public JTree generateTree(){
        JTree tree;
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Images");
    createNodes(top);
    tree = new JTree(top);
        return tree;
    }

    public void createNodes(DefaultMutableTreeNode top){
        System.out.println("TAM: " + fileNames.size());
        int maxPositions = getMaxPosition();
        for(int i = 1; i <= maxPositions; ++i ){
            Position position = new Position(i);
            //Adding position to root
            DefaultMutableTreeNode positionNode = new DefaultMutableTreeNode(position);
            top.add(positionNode);
            int maxTimesForPosition = getMaxTime(position);
            for(int j = 1; j <= maxTimesForPosition; ++j){
                Time time = new Time(position,j);
                DefaultMutableTreeNode timeNode = new DefaultMutableTreeNode(time);
                //Adding Time node to position
                positionNode.add(timeNode);
                File[] files = directory.listFiles(new NameFilter(position,time));
                time.setFiles(files);
                for(File f : files){
                    timeNode.add(new DefaultMutableTreeNode(f.getName()));
                }
                
            }
            
        }
    }

    public int getMaxPosition(){

       //TODO: Para generalizar todavia mas podemos recibir un parametro
       // que identifique el nombre con el que empieza la posicion, o guardarlo
       // como variable de instancia de Position
        int max = 0;
        for(String name: fileNames){
            int aux;
            int startPos = name.toLowerCase().indexOf("position");
            int endPos = name.toLowerCase().indexOf("_time");
            aux = Integer.valueOf(name.substring(startPos+8,endPos));
            if(aux > max){
                max = aux;
            }
        }
        System.out.println("Max Pos: " + max);
        return max;
    }

    public int getMaxTime(Position position){

       //TODO: Para generalizar todavia mas podemos recibir un parametro
       // que identifique el nombre con el que empieza el tiempo, o guardarlo
       // como variable de instancia de Time
       int max = 0;
       for(String name: fileNames){
            int aux;
            
            if(name.toLowerCase().contains(position.toString())){
                int startPos = name.toLowerCase().indexOf("time_");
                int endPos = name.toLowerCase().indexOf(".tif");
                aux = Integer.valueOf(name.substring(startPos+5,endPos));
//                System.err.println("Num de time" + name.substring(startPos+5,endPos));
                if(aux > max){
                    max = aux;
                }

            }
        }
//        System.out.println("Max time:" + max);
        return max;
    }


    public void setDirectory(File file){
        if(!file.isDirectory()){
            throw new IllegalArgumentException("Path is not a directory");
        }else{
            this.directory = file;
        }
    }
    private class NameFilter implements FilenameFilter{

        Position position;
        Time time;

        private NameFilter(Position position, Time time){

            this.position = position;
            this.time = time;
        }

        public boolean accept(File dir, String name) {
           if(name.toLowerCase().contains(position.toString()) &&
                   name.toLowerCase().contains(time.toString())){
               return true;
           }else{
               return false;
           }
        }

    }
}
