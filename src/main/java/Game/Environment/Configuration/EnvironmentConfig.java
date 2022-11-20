package Game.Environment.Configuration;

import MathUtil.Position2D;

public class EnvironmentConfig {

    private int nbLine;
    private int nbCol;
    private int maxNbLine;
    private int minNbLine;
    private int maxNbCol;
    private int minNbCol;
    private Position2D start;
    private Position2D end;

    public EnvironmentConfig(){
        nbLine = -1;
        nbCol = -1;
        maxNbLine = -1;
        minNbLine = -1;
        maxNbCol = -1;
        minNbCol = -1;
        start = new Position2D(-1,-1);
        end = new Position2D(-1,-1);
    }

    public EnvironmentConfig(int nbLine, int nbCol, int maxNbLine, int minNbLine, int maxNbCol, int minNbCol) {
        this.nbLine = nbLine;
        this.nbCol = nbCol;
        this.maxNbLine = maxNbLine;
        this.minNbLine = minNbLine;
        this.maxNbCol = maxNbCol;
        this.minNbCol = minNbCol;
    }

    public int getNbLine() {
        return nbLine;
    }

    public void setNbLine(int nbLine) {
        this.nbLine = nbLine;
    }

    public int getNbCol() {
        return nbCol;
    }

    public void setNbCol(int nbCol) {
        this.nbCol = nbCol;
    }

    public int getMaxNbLine() {
        return maxNbLine;
    }

    public void setMaxNbLine(int maxNbLine) {
        this.maxNbLine = maxNbLine;
    }

    public int getMinNbLine() {
        return minNbLine;
    }

    public void setMinNbLine(int minNbLine) {
        this.minNbLine = minNbLine;
    }

    public int getMaxNbCol() {
        return maxNbCol;
    }

    public void setMaxNbCol(int maxNbCol) {
        this.maxNbCol = maxNbCol;
    }

    public int getMinNbCol() {
        return minNbCol;
    }

    public void setMinNbCol(int minNbCol) {
        this.minNbCol = minNbCol;
    }

    public Position2D getStart() {
        return start;
    }

    public void setStart(Position2D start) {
        this.start = start;
    }

    public Position2D getEnd() {
        return end;
    }

    public void setEnd(Position2D end) {
        this.end = end;
    }
}
