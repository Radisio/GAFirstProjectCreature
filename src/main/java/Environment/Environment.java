package Environment;

import MathUtil.MathUtil;

public class Environment {
    private Case board[][];

    public Environment(Case[][] board) {
        this.board = board;
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

}
