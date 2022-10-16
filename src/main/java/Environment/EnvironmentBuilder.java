package Environment;

import Const.DesignConst;
import jdk.jshell.spi.ExecutionControl;

import java.util.Random;

public class EnvironmentBuilder {
    public static int DEFAULT_MIN_NB_LINE = 5;
    public static int DEFAULT_MAX_NB_LINE = 15;
    public static int DEFAULT_MIN_NB_COL = 30;
    public static int DEFAULT_MAX_NB_COL = 40;


    private  int nbLine;
    private  int nbCol;
    private int startingPosX;
    private int startingPosY;
    private int endingPosX;
    private int endingPosY;
    private boolean filling;

    public EnvironmentBuilder()
    {
        this.nbLine = -1;
        this.nbCol = -1;
        this.startingPosX = -1;
        this.startingPosY = -1;
        this.endingPosX = -1;
        this.endingPosY = -1;
        this.filling=true;
    }

    public EnvironmentBuilder startingPosX(int spx)
    {
        this.startingPosX = spx;
        return this;
    }
    public EnvironmentBuilder startingPosY(int spy)
    {
        this.startingPosY = spy;
        return this;
    }
    public EnvironmentBuilder endingPosX(int epx)
    {
        this.endingPosX = epx;
        return this;
    }
    public EnvironmentBuilder endingPosY(int epy)
    {
        this.endingPosY = epy;
        return this;
    }
    public  EnvironmentBuilder nbLine(int nbLine)
    {
        this.nbLine = nbLine;
        return this;
    }
    public EnvironmentBuilder nbCol(int nbCol)
    {
        this.nbCol = nbCol;
        return this;
    }

    public EnvironmentBuilder filling(boolean f)
    {
        this.filling = f;
        return this;
    }

    private void setVariable()
    {
        Random random = new Random();
        int startingFirstQuart = random.ints(0,2).findAny().getAsInt();
        if(this.nbCol == -1)
        {
            this.nbCol = random.ints(DEFAULT_MIN_NB_COL,DEFAULT_MAX_NB_COL).findAny().getAsInt();
        }
        if(this.nbLine == -1)
        {
            this.nbLine = random.ints(DEFAULT_MIN_NB_LINE,DEFAULT_MAX_NB_LINE).findAny().getAsInt();

        }
        if(this.startingPosX == -1)
        {
            int minBound, maxBound;
            minBound = 1;
            maxBound = (this.nbCol-2)/4;
            if(startingFirstQuart==1)
            {
                maxBound = this.nbCol-2;
                minBound = maxBound - maxBound/4;
            }
            this.startingPosX = random.ints(minBound, maxBound).findAny().getAsInt();

        }
        if(this.startingPosY == -1)
        {
            this.startingPosY = random.ints(1, this.nbLine-2).findAny().getAsInt();
        }
        if(this.endingPosX == -1 ||this.endingPosY == -1)
        {
            int minBound, maxBound;
            minBound = 1;
            maxBound = (this.nbCol-2)/4;
            if(startingFirstQuart==0)
            {
                maxBound = this.nbCol-2;
                minBound = maxBound - maxBound/4;
            }

            this.endingPosX = random.ints(minBound, maxBound).findFirst().getAsInt();
            int maxYEnd = 0;
            if(startingFirstQuart== 0) {
                maxYEnd = endingPosX - startingPosX + startingPosY;
            }
             else {
                maxYEnd = -endingPosX + startingPosX + startingPosY;
            }
            if(maxYEnd>this.nbLine-2)
            {
                this.endingPosY = random.ints(1, this.nbLine-2).findAny().getAsInt();
                /*
                if(maxYEnd>this.nbLine-2)
                    this.endingPosY = this.nbLine-2;
                else
                    this.endingPosY = 1;*/
            }
            else{
                this.endingPosY=maxYEnd;
            }

            System.out.println("X dep : " + startingPosX);
            System.out.println("Y dep : " + startingPosY);
            System.out.println("X fin : " + endingPosX);
            System.out.println("Y fin: " + endingPosY);
            System.out.println("NbCol : " + nbCol);
            System.out.println("NbLine : " + nbLine);


        }
        System.out.println("Starting : (x,y) : ("+startingPosX+","+startingPosY+")");
        System.out.println("Ending : (x,y) : ("+endingPosX+","+endingPosY+")");
        /*
        if(this.endingPosY == -1)
        {
            //this.endingPosY = random.ints(1, this.nbLine-2).findFirst().getAsInt();
            this.endingPosY = endingPosX-startingPosX+startingPosY;
        }*/

    }

    private void putBlockUnderFlags(Case board[][]) throws ExecutionControl.NotImplementedException {
        if(!board[startingPosY+1][startingPosX].isOccupied())
        {
            board[startingPosY+1][startingPosX].setOccupation(DesignConst.WHITE_SQUARE);
            if(filling)
            {
                int fillingYPos = startingPosY+2;
                while (fillingYPos < nbLine && !board[fillingYPos][startingPosX].isOccupied()) {
                    board[fillingYPos++][startingPosX].setOccupation(DesignConst.WHITE_SQUARE);
                }
            }
            else{
                throw new ExecutionControl.NotImplementedException("Not implemented yet");
            }
        }
        if(!board[endingPosY+1][endingPosX].isOccupied())
        {
            board[endingPosY+1][endingPosX].setOccupation(DesignConst.WHITE_SQUARE);
            if(filling)
            {
                int fillingYPos = endingPosY+2;
                while (fillingYPos<nbLine&&!board[fillingYPos][endingPosX].isOccupied()) {
                    board[fillingYPos++][endingPosX].setOccupation(DesignConst.WHITE_SQUARE);
                }
            }
            else{
                throw new ExecutionControl.NotImplementedException("Not implemented yet");
            }
        }
    }

    public Environment build() throws ExecutionControl.NotImplementedException {
        setVariable();

        Case board[][] = new Case[nbLine][nbCol];
        for(int i = 0;i<nbLine;i++)
        {
            for(int j = 0;j<nbCol;j++)
            {
                board[i][j] = new Case(DesignConst.EMPTY);
            }
        }
        int tmp = 2;
        for(int i = 0;i<nbLine;i++)
        {
            for(int j = 0;j<nbCol;j++)
            {
                board[i][j] = new Case(DesignConst.WHITE_SQUARE);
            }
            i = nbLine-tmp--;
        }
        tmp = 2;
        for(int i=0;i<nbCol;i++)
        {
            for(int j =0;j<nbLine;j++)
            {
                board[j][i].setOccupation(DesignConst.WHITE_SQUARE);
            }
            i = nbCol-tmp--;
        }
        board[this.startingPosY][this.startingPosX].setOccupation(DesignConst.STARTING_FLAG);
        board[this.endingPosY][this.endingPosX].setOccupation(DesignConst.ENDING_FLAG);
        putBlockUnderFlags(board);
        return new Environment(board);
    }
}
