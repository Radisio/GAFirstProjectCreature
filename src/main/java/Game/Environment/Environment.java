package Game.Environment;

import Game.Const.DesignConst;
import MathUtil.Position2D;

import java.io.Serializable;
import java.util.Arrays;

public class Environment implements Serializable, Cloneable {
    private Case[][] board;
    private Position2D startingPos;
    private Position2D endingPos;

    public Environment(Case[][] board, Position2D starting, Position2D ending) {

        this.board = EnvironmentUtil.deepCopyBoard(board);
        this.startingPos = starting;
        this.endingPos = ending;
    }

    public Environment(Environment environment) {
        this.board = EnvironmentUtil.deepCopyBoard(environment.getBoard());
        this.startingPos = environment.getStartingPos();
        this.endingPos = environment.getEndingPos();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Environment e = (Environment) super.clone();
        e.setBoard(this.getBoard().clone());
        e.setStartingPos(this.getStartingPos());
        e.setEndingPos(this.getEndingPos());
        return e;
    }

    public Case[][] getBoard() {
        return board;
    }

    public void setBoard(Case[][] board) {
        this.board = board;
    }


    public void displayOnce()
    {
        for(int i = 0;i<board.length;i++)
        {
            for(int j = 0; j<board[i].length;j++)
            {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public Case getCaseByPosition(Position2D pos)
    {
        int x = pos.x;
        int y = pos.y;
        if(x<this.board.length && y<this.board[0].length)
            return this.board[y][x];
        return null;
    }

    @Override
    public String toString() {
        StringBuilder returnedString= new StringBuilder();
        System.out.println("EndingPos : " + getEndingPos());
        for(int i = 0;i<board.length;i++)
        {
            for(int j = 0; j<board[i].length;j++)
            {
                returnedString.append(board[i][j]);
            }
            returnedString.append("\n");
        }
        return returnedString.toString();
    }

    public Position2D getStartingPos() {
        return startingPos;
    }

    public void setStartingPos(Position2D startingPos) {
        this.startingPos = startingPos;
    }

    public Position2D getEndingPos() {
        return endingPos;
    }

    public void setEndingPos(Position2D endingPos) {
        this.endingPos = endingPos;
    }


    public boolean setCreaturePosition(Position2D pos, Position2D oldPos){

        if(board[pos.y][pos.x].getOccupation()!=DesignConst.WHITE_SQUARE) {
            board[pos.y][pos.x].setOccupation(DesignConst.CREATURE);
            if(oldPos!=null)
            {
                if(oldPos!=this.startingPos)
                    board[oldPos.y][oldPos.x].setOccupation(DesignConst.EMPTY);
                else
                    board[oldPos.y][oldPos.x].setOccupation(DesignConst.STARTING_FLAG);
            }
            return true;
        }
        return false;
    }

    public void setBoardOccupation(Position2D pos, char occupation) {
        this.board[pos.y][pos.x].setOccupation(occupation);
    }
}
