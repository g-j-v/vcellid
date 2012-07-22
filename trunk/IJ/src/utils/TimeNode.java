package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a TimeNode of the tree
 * @author apetit
 */
public class TimeNode {

    List<File> files;
    PositionNode position;
    Integer number;

    /**
     * Constructor with empty images
     * @param position to which this time belongs
     * @param number to identify the time
     */
    public TimeNode(PositionNode position, int number){
        this(position,number,new ArrayList<File>());
    }

    /**
     * Constructor with given images
     * @param position to which this time belongs
     * @param number to identify the time
     * @param files which belong to this time
     */
    public TimeNode(PositionNode position, int number, List<File> files){
        setPosition(position);
        setNumber(number);
        setFiles(files);
    }

    public List<File> getFiles() {
        return files;
    }

    public void addFile(File file){

        if(file != null){
            this.files.add(file);
        }
    }
    public void addFiles(List<File> files){
        this.files.addAll(files);
    }

    public void setFiles(List<File> files) {
        if(files != null){
            this.files = files;
        }
        else{
            throw new IllegalArgumentException("Null File List");
        }
    }
    public void setFiles(File[] files){
        this.files.clear();
        for(File f: files){
            this.files.add(f);
        }
    }

    public void setNumber(Integer number) {
        if (number > 0){
            this.number = number;
        }else{
            throw new IllegalArgumentException("Invalid Position id");
        }

    }

    public void setPosition(PositionNode position) {
        if(position != null){
            this.position = position;
        }else{
            throw new IllegalArgumentException("Null Position");
        }

    }

    public Integer getNumber() {
        return number;
    }

    public PositionNode getPosition() {
        return position;
    }

    @Override
    public String toString(){

        if(number > 99){
            return "time_" + number;
        }else if(number > 9){
            return "time_0"+ number;
        }else{
            return "time_00" + number;
        }

    }
}
