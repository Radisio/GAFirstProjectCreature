package Game.Environment;

import Game.Const.DesignConst;

import java.io.Serializable;

public class Case implements Serializable {
    private char occupation;
    private boolean occupied;
    public Case(char occupation) {
        this.occupation = occupation;
    }

    public char getOccupation() {
        return occupation;
    }

    public void setOccupation(char occupation) {
        this.occupation = occupation;
        /*if(this.occupation == DesignConst.EMPTY)
            this.occupied = false;
        else
            this.occupied = true;*/
    }

    public boolean isOccupied() {

        return this.occupation==DesignConst.WHITE_SQUARE;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    @Override
    public String toString() {
        return String.valueOf(occupation);
    }
}
