package utils.node;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import utils.ImageNamePattern;

/**
 * Class that represents a TimeNode of the tree
 * @author apetit
 */
public class TimeNode {

    List<ImageNode> imageNodes;
    PositionNode position;
    Integer number;

    /**
     * Constructor with empty images
     * @param position to which this time belongs
     * @param number to identify the time
     */
    public TimeNode(PositionNode position, int number){
        this(position,number,new ArrayList<ImageNode>());
    }

    /**
     * Constructor with given images
     * @param position to which this time belongs
     * @param number to identify the time
     * @param files which belong to this time
     */
    public TimeNode(PositionNode position, int number, List<ImageNode> imageNodes){
        setPosition(position);
        setNumber(number);
        setImageNodes(imageNodes);
    }

    public List<ImageNode> getFiles() {
        return imageNodes;
    }

    public void addImageNode(ImageNode imageNode){

        if(imageNode != null){
            this.imageNodes.add(imageNode);
        }
    }
    public void addImageNodes(List<ImageNode> imageNodes){
        this.imageNodes.addAll(imageNodes);
    }

    public void setImageNodes(List<ImageNode> imageNodes) {
        if(imageNodes != null){
            this.imageNodes = imageNodes;
        }
        else{
            throw new IllegalArgumentException("Null File List");
        }
    }
//    public void setImageNodes(ImageNode[] imageNodes){
//        this.imageNodes.clear();
//        for(ImageNode image: imageNodes){
//            this.imageNodes.add(image);
//        }
//    }

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

        return ImageNamePattern.getInstance().getTimePattern() + number;

    }
}
