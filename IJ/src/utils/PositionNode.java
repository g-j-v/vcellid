/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.List;

/**
 *
 * @author apetit
 */
public class PositionNode {

    List<TimeNode> times;
    Integer number;

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
        return "position" + number + "_" ;
    }
}
