/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CellId;

import java.util.List;

/**
 *
 * @author apetit
 */
public class Position {

    List<Time> times;
    Integer number;

    public Position(int number){
        this.number = number;
    }


    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
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
