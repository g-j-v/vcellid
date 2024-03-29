package utils.node;

import java.util.List;

import utils.ImageNamePattern;

/**
 * Class to represent a Position node of the tree
 * @author Alejandro Petit
 */
public class PositionNode {

    List<TimeNode> times;
    Integer number;

    /**
     * Constructor
     * @param number to identify the position
     */
    public PositionNode(int number){
        this.number = number;
    }

    public List<TimeNode> getTimes() {
        return times;
    }

    public void setTimes(List<TimeNode> times) {
        this.times = times;
    }

    public void setNumber(Integer number) {
        if(number > 0){
            this.number = number;
        }else{
            throw new IllegalArgumentException("Invalid number");
        }

    }

    public Integer getNumber() {
        return number;
    }

    @Override
    public String toString(){
        return ImageNamePattern.getInstance().getPositionPattern() + number ;
    }
}
