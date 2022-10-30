package Game.Environment;

import Game.Movement.MovementConst;
import MathUtil.Position2D;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentUtil {
    private static List<Position2D> fallingHandler(Case[][] board, Position2D start, MovementConst move)
    {
        List<Position2D> positions = new ArrayList<>();
        int x = start.x;
        int y = start.y;
        if(board[y+1][x].isOccupied())
        {
            return positions;
        }
        ///Falling
        positions.addAll(gravity(board, new Position2D(x,y), move));
        return positions;
    }
    public static List<Position2D> gravity(Case[][] board, Position2D start, MovementConst move)
    {
        int x = start.x;
        int y = start.y;
        List<Position2D> positions = new ArrayList<Position2D>();
        switch(move)
        {
            case UP_RIGHT:
            case RIGHT:
            case DOWN_RIGHT:
            {
                /*while(!board[++y][++x].isOccupied() && !board[y+1][x].isOccupied())
                {
                    positions.add(new Position2D(x,y));
                }
                break;*/
                while(!board[++y][++x].isOccupied())
                {
                    positions.add(new Position2D(x,y));
                }
                //positions.add(new Position2D(x,y));
                if(!board[y][--x].isOccupied())
                {
                    while(!board[y][x].isOccupied())
                    {
                        positions.add(new Position2D(x,y++));
                    }
                }
                break;
            }
            case UP_LEFT:
            case LEFT:
            case DOWN_LEFT:{
                /*while(!board[++y][--x].isOccupied() && !board[y+1][x].isOccupied())
                {
                    positions.add(new Position2D(x,y));
                }
                break;*/
                while(!board[++y][--x].isOccupied())
                {
                    positions.add(new Position2D(x,y));
                }
                //positions.add(new Position2D(x,y));
                if(!board[y][++x].isOccupied())
                {
                    while(!board[y][x].isOccupied())
                    {
                        positions.add(new Position2D(x,y++));
                    }
                }
                break;
            }
        }
/*
        System.out.println("Y: = " + y);
        while(!board[++y][x].isOccupied())
        {
            System.out.println("y = " + y);
            positions.add(new Position2D(x,y));
        }
        */
        return positions;
    }

    public static List<Position2D> doMovement(Case[][] board, Position2D start, MovementConst move)
    {
        List<Position2D> positions = new ArrayList<>();
        int x = start.x;
        int y = start.y;
       // System.out.println("MOVE : " + move);
      //  System.out.println("x : " + x);
        switch(move)
        {
            case UP_RIGHT:
            {
                if(board[--y][++x].isOccupied())
                    return positions;
                positions.add(new Position2D(x,y));
                positions.addAll(fallingHandler(board, new Position2D(x,y), move));
                break;
            }
            case RIGHT:{
                if(board[y][++x].isOccupied())
                {
                    return positions;
                }
                positions.add(new Position2D(x,y));
                positions.addAll(fallingHandler(board, new Position2D(x,y), move));
                break;
            }
            case DOWN_RIGHT:
            {
                if(board[++y][++x].isOccupied())
                {
                    return positions;
                }
                positions.add(new Position2D(x,y));
                positions.addAll(fallingHandler(board, new Position2D(x,y), move));
                break;
            }
            case UP_LEFT:
            {
                if(board[--y][--x].isOccupied())
                    return positions;
                positions.add(new Position2D(x,y));
                positions.addAll(fallingHandler(board, new Position2D(x,y), move));
                break;
            }
            case LEFT:
            {
                if(board[y][--x].isOccupied())
                {
                    return positions;
                }
                positions.add(new Position2D(x,y));
                positions.addAll(fallingHandler(board, new Position2D(x,y), move));
                break;
            }
            case DOWN_LEFT:
            {
                if(board[++y][--x].isOccupied())
                    return positions;
                positions.add(new Position2D(x,y));
                positions.addAll(fallingHandler(board, new Position2D(x,y), move));
                break;
            }
            case UP:
            {
                if(board[--y][x].isOccupied())
                    return positions;
                positions.add(new Position2D(x,y));
                positions.add(start);
                break;
            }
            case DOWN:{
                if(board[++y][x].isOccupied())
                    return positions;
                positions.add(new Position2D(x,y));
                break;
            }
        }
       // System.out.println("x 2: " + x);
        return positions;
    }
}
