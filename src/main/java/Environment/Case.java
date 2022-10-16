package Environment;

import Const.DesignConst;

public class Case {
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
        if(this.occupation == DesignConst.EMPTY)
            this.occupied = false;
        else
            this.occupied = true;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    @Override
    public String toString() {
        return String.valueOf(occupation);
    }
}
