package Game.Environment;

import Game.Const.DesignConst;
import Game.Movement.MovementConst;
import MathUtil.*;
import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EnvironmentBuilder {
    public static int DEFAULT_MIN_NB_LINE = 5;
    public static int DEFAULT_MAX_NB_LINE = 15;
    public static int DEFAULT_MIN_NB_COL = 30;
    public static int DEFAULT_MAX_NB_COL = 40;


    private  int nbLine;
    private  int nbCol;
    private Position2D startingPos;
    private Position2D endingPos;
    private boolean filling;

    public EnvironmentBuilder()
    {
        this.nbLine = -1;
        this.nbCol = -1;
        this.filling=true;
        this.startingPos = new Position2D(-1,-1);
        this.endingPos = new Position2D(-1,-1);
    }

    public EnvironmentBuilder startingPos(Position2D pos)
    {
        this.startingPos = pos;
        return this;
    }

    public EnvironmentBuilder endingPos(Position2D pos)
    {
        this.endingPos = pos;
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

    private int valeurMaxLimit(Position2D start, Position2D end)
    {
        /**
        if(start.x>end.x)
        {
            return end.x+-start.x+start.y;
        }
        else
            return -end.x+start.x+start.y;**/
        try {
           //System.out.println("Start (" +start.x + ";" + start.y+")");
         //   System.out.println("End (" +end.x + ";" + end.y+")");
         //   System.out.println("VML : " + ((end.x-start.x) / (end.y - start.y)));
            //return (start.x - end.x) / (start.y - end.y);
            return (end.x-start.x) / (end.y - start.y);
        }
        catch(ArithmeticException ae)
        {
            if(end.y-start.y == 0)
                return Integer.MAX_VALUE;
            return 0;
        }
    }

    private boolean isSolutionPossible(Position2D start, Position2D end)
    {
        /*
        if(start.x>end.x)
            return valeurMaxLimit(start, end)>=1;
        return valeurMaxLimit(start,end)<=-1;*/
        int vml = valeurMaxLimit(start,end);
        return vml>=1 || vml <=-1;
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
        if(this.startingPos.x == -1)
        {
            int minBound, maxBound;
            minBound = 1;
            maxBound = (this.nbCol-2)/4;
            if(startingFirstQuart==1)
            {
                maxBound = this.nbCol-2;
                minBound = maxBound - maxBound/4;
            }
            this.startingPos.x = random.ints(minBound, maxBound).findAny().getAsInt();

        }
        if(this.startingPos.y == -1)
        {
            this.startingPos.y= random.ints(1, this.nbLine-2).findAny().getAsInt();
        }
        if(this.endingPos.x == -1 || this.endingPos.y == -1)
        {
            int minBound, maxBound;
            minBound = 1;
            maxBound = (this.nbCol-2)/4;
            if(startingFirstQuart==0)
            {
                maxBound = this.nbCol-2;
                minBound = maxBound - maxBound/4;
            }

            this.endingPos.x = random.ints(minBound, maxBound).findFirst().getAsInt();
            int maxYEnd = 0;
            if(startingFirstQuart== 0) {
                maxYEnd = this.endingPos.x  - this.startingPos.x + this.startingPos.y;
            }
             else {
                maxYEnd = -this.endingPos.x + this.startingPos.x + this.startingPos.y;
             }
            if(maxYEnd>this.nbLine-2)
            {
                this.endingPos.y = random.ints(1, this.nbLine-2).findAny().getAsInt();
            }
            else{
                this.endingPos.y=maxYEnd;
            }
        }
    }

    private Case[][] fillVoid(Case board[][], Position2D pos)
    {
        //System.out.println("FillVoid");
        if(!board[pos.y+1][pos.x].isOccupied())
        {
           // System.out.println("ON RENTRE");
            board[pos.y+1][pos.x].setOccupation(DesignConst.WHITE_SQUARE);
            if(filling)
            {
             //   System.out.println("BAGUETTE");
                int fillingYPos = pos.y+2;
                while (fillingYPos < nbLine && !board[fillingYPos][pos.x].isOccupied()) {
               //     System.out.println("Oui");
                    board[fillingYPos++][pos.x].setOccupation(DesignConst.WHITE_SQUARE);
                }
            }
        }
        return board;
    }

    private void addElement(Case board[][], Position2D pos, char element)
    {
        board[pos.y][pos.x].setOccupation(element);
        fillVoid(board,pos);
    }


    private void initiateEnvironment(Case board[][])
    {
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
        addElement(board, this.startingPos, DesignConst.STARTING_FLAG);
        addElement(board, this.endingPos, DesignConst.ENDING_FLAG);
    }

    private void buildingSML(Case[][] board, Position2D start)
    {
        Position2D pos=  start;
        if(start.x < this.endingPos.x)
        {
            pos.x++;
            pos.y--;
            while(pos!=this.endingPos)
            {
                addElement(board, new Position2D(pos.x,pos.y+1), DesignConst.WHITE_SQUARE);
                pos.x++;
                pos.y--;
            }
        }
        else{
            pos.x--;
            pos.y--;
            while(pos!=this.endingPos)
            {
                //System.out.println("POS : " + pos);
                //System.out.println("Ending : " + this.endingPos);
                addElement(board, new Position2D(pos.x,pos.y+1), DesignConst.WHITE_SQUARE);
                pos.x--;
                pos.y--;
            }
        }
    }

    private List<Case[][]> findAllSolution(Case[][] initBoard, Position2D actualPos){
        ArrayList<Case[][]> returnedArrayList = new ArrayList<>();
        if (isSolutionPossible(actualPos, this.endingPos)) {
            //System.out.println("Actual pos : " + actualPos);
            //System.out.println("Ending pos : " + this.endingPos);
            //System.out.println("UNE SOLUTION EST POSSIBLE");
            ///sI OUI
            ///Si solution max limite uniquement disponible (=1 ou =-1)
            int VML = valeurMaxLimit(actualPos, this.endingPos);
            if (VML == 1 || VML == -1) {
                /// On fait la technique max limite et on return
                Case[][] boardSML = initBoard;
                buildingSML(boardSML, actualPos);
                returnedArrayList.add(boardSML);
                //System.out.println("SML Solution");
            } else {
                /// Sinon, on continue

                MovementConst[] allowedMovement = new MovementConst[]{MovementConst.UP_RIGHT, MovementConst.RIGHT, MovementConst.DOWN_RIGHT};
                if (endingPos.x < startingPos.x) {
                    allowedMovement = new MovementConst[]{MovementConst.UP_LEFT, MovementConst.LEFT, MovementConst.DOWN_LEFT};
                }
                for (int i = 0; i < 3; i++) {
                    Case[][] board = initBoard;
                    List<Case[][]> result = createSolution(board, this.startingPos, allowedMovement[i]);
                    //System.out.println("RESULT SIZE 1 : " + result.size());
                    if (result.size()>0) {
                        returnedArrayList.addAll(result);
                    }
                }
            }

        }
        else
            System.out.println("AUCUNE SOLUTION");
        return returnedArrayList;
    }

    private List<Case[][]> createSolution(Case[][] board, Position2D start, MovementConst move)
    {
        ArrayList<Case[][]> returnedArrayList = new ArrayList<>();
        ///Réaliser le mouvement
        List<Position2D> lPos = EnvironmentUtil.doMovement(board, start, move);
       // System.out.println("LPOS SIZE : " + lPos.size());
        if(lPos.size()!=0) {
            //Position2D actualPos = lPos.get(lPos.size()-1);
            ///Sommes-nous sur le point d'arrivée?
            for (Position2D actualPos : lPos) {
                Case[][] initBoard = board;
                if (actualPos == this.endingPos) {
                 //   System.out.println("ENDING POS REACH");
                    returnedArrayList.add(initBoard);
                } else {
                    ///Est-on en l'air ?
                    ///Si oui, block ou pas block ?
                    if (!board[actualPos.y + 1][actualPos.x].isOccupied()) {
                        ///Block
                        //fillVoid(initBoard, actualPos);
                        Case[][] boardBlock = board;
                        addElement(boardBlock, new Position2D(actualPos.x, actualPos.y+1), DesignConst.WHITE_SQUARE);
                        List<Case[][]> result = findAllSolution(boardBlock, actualPos);
                       // System.out.println("RESULT SIZE 2 : " + result.size());
                        if (result.size() > 0)
                            returnedArrayList.addAll(result);
                        /// Pas block : On itère dans la boucle, on arrivera la case d'après
                    } else {
                        List<Case[][]> result = findAllSolution(initBoard, actualPos);
                       // System.out.println("RESULT SIZE  3: " + result.size());
                        if (result.size() > 0)
                            returnedArrayList.addAll(result);
                    }
                }
            }
        }
        return returnedArrayList;
    }

    private void settingUpPath(Case[][] board)
    {
        MovementConst[] allowedMovement = new MovementConst[]{MovementConst.UP_RIGHT, MovementConst.RIGHT, MovementConst.DOWN_RIGHT};
        if(endingPos.x<startingPos.x)
        {
            allowedMovement = new MovementConst[]{MovementConst.UP_LEFT, MovementConst.LEFT, MovementConst.DOWN_LEFT};
        }
        List<Case[][]> solutions =new ArrayList<>();

        for(int i =0;i<3;i++)
        {
            List<Case[][]> result = createSolution(board, this.startingPos, allowedMovement[i]);
            if(result.size()>0)
            {
                solutions.addAll(result);
            }
        }

    }

    public Case[][] getRandomMountain(Case[][] board) {
        Case[][] returnedBoard = board;
        Position2D actualPos = this.startingPos;
        //MovementConst[] allowedMovement = new MovementConst[]{MovementConst.UP_RIGHT, MovementConst.RIGHT, MovementConst.DOWN_RIGHT};
        ArrayList<MovementConst> allowedMovement = new ArrayList<MovementConst>()
        {
            {
            add(MovementConst.UP_RIGHT);

            add(MovementConst.RIGHT);

            add(MovementConst.DOWN_RIGHT);
            }
        };
        if(endingPos.x<startingPos.x)
        {
            allowedMovement = new ArrayList<MovementConst>()
            {
                {
                    add(MovementConst.UP_LEFT);

                    add(MovementConst.LEFT);

                    add(MovementConst.DOWN_LEFT);
                }
            };

        }
        Random random = new Random();

        int min, max;
        boolean move = false;
        while(actualPos!=this.endingPos) {
            move = false;
            ArrayList<MovementConst> allowMovementTmp = allowedMovement;
           // System.out.println("ActualPos : " + actualPos);
            //System.out.println("EndingPos : " + this.endingPos);
            while (!move) {
                if(allowMovementTmp.size() == 0)
                {
                    //System.out.println("MERDE");
                    return returnedBoard;
                }
                int indexRandom = random.ints(0, allowMovementTmp.size()).findAny().getAsInt();
                List<Position2D> pos = EnvironmentUtil.doMovement(returnedBoard, actualPos, allowMovementTmp.get(indexRandom));
                if (pos.size() == 0) {
                    //System.out.println("MERDE 2");
                    move = false;
                    allowMovementTmp.remove(indexRandom);
                } else {
                    ArrayList<Integer> posAvailable = MathUtil.range(0, pos.size()-1,1);
                    while(posAvailable.size()>0 && !move) {
                        int indexPos = 0;
                        if (posAvailable.size() > 1) {
                            indexPos = posAvailable.get(random.ints(0, posAvailable.size()).findAny().getAsInt());
                        }
                        Position2D p = pos.get(indexPos);
                        //System.out.println("POSITION  : " + p);
                        //System.out.println("END POSITION  : " + endingPos);
                        if (isSolutionPossible(p, this.endingPos)) {
                            //System.out.println("AVANT = ");
                            //new Game.Environment(returnedBoard, this.startingPos, this.endingPos).displayOnce();
                            returnedBoard = fillVoid(returnedBoard, p);
                            //System.out.println("APRES");
                            //new Game.Environment(returnedBoard, this.startingPos, this.endingPos).displayOnce();
                            actualPos = p;
                            move = true;
                        } else {
                            move = false;
                            //allowMovementTmp.remove(indexRandom);
                            if(posAvailable.size()==1)
                                posAvailable.clear();
                            else
                                posAvailable.removeAll(Arrays.asList(indexPos));
                        }
                    }
                    if(!move)
                    {
                        allowMovementTmp.remove(indexRandom);
                    }
                    else
                        move=true;
                }

            }
        }
        return returnedBoard;
    }

    public List<Environment> buildAllSolution() throws ExecutionControl.NotImplementedException {
        setVariable();
       // System.out.println("NbLine : " + this.nbLine);
       // System.out.println("nbCol : " + this.nbCol);

        Case board[][] = new Case[nbLine][nbCol];
        initiateEnvironment(board);
        //PAS ENCORE TESTER
        List<Case[][]> solutions = findAllSolution(board, this.startingPos);
        //System.out.println("SOLUTIONS : " + solutions.size());
        //return new Game.Environment(board, this.startingPos, this.endingPos);
        List<Environment> returnedList = new ArrayList<>();
        for (Case[][] s : solutions)
        {
            returnedList.add(new Environment(s, this.startingPos, this.endingPos));
        }
        return returnedList;
    }

    public Environment build()
    {
        setVariable();
        Case board[][] = new Case[nbLine][nbCol];
        initiateEnvironment(board);
        new Environment(board, this.startingPos, this.endingPos);
        board = getRandomMountain(board);
        if(board==null)
            return null;
        else
            return new Environment(board, this.startingPos, this.endingPos);
    }
}
