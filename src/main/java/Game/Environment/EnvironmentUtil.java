package Game.Environment;

import Game.Environment.Configuration.EnvironmentConfig;
import Game.Movement.MovementConst;
import MathUtil.Position2D;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

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
        List<Position2D> positions = new ArrayList<>();
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
    public static Case[][] deepCopyBoard(Case[][] board)
    {
        Case[][] newBoard = new Case[board.length][board[0].length];
        for(int i = 0;i<board.length;i++)
        {
            for(int j =0;j<board[0].length;j++)
                newBoard[i][j] = new Case(board[i][j].getOccupation());
        }
        return newBoard;
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
    public static Environment readEnvironmentFromFile(String path)
    {
        Environment b2 = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            b2 = (Environment) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return b2;
    }

    public static void writeEnvironmentToFile(Environment env, String path) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(env);
        oos.flush();
        oos.close();
    }
    public static Environment newEnvironmentFromFilePos(String path)
    {
        Environment env = null;
        try {
            File f = new File(path);
            Scanner s = new Scanner(f);
            if(s.hasNextLine()) {
                String dataLine = s.nextLine();
                //Start End [Obstacles]
                String[] coordinates = dataLine.split(" ");
                List<Position2D> positions = new ArrayList<>();
                for (int i = 0; i < coordinates.length; i++)
                {
                    String[] pos = coordinates[i].split(",");
                    positions.add(new Position2D(Integer.parseInt(pos[0]), Integer.parseInt(pos[1])));
                }
                int maxX = positions.stream().max(Comparator.comparing(v->v.x)).get().x+2;
                int maxY = positions.stream().max(Comparator.comparing(v->v.y)).get().y+2;
                EnvironmentBuilder eb = new EnvironmentBuilder();
                eb.setNbLine(maxY);
                eb.setNbCol(maxX);
                eb.setStartingPos(positions.get(0));
                eb.setEndingPos(positions.get(1));
                List<Position2D> obstacles = positions.subList(2, positions.size());
                env= eb.buildWObstacles(obstacles);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return env;
    }
    public static Environment newEnvironmentFromFilePosOtherView(String path)
    {
        Environment env = null;
        try {
            File f = new File(path);
            Scanner s = new Scanner(f);
            if(s.hasNextLine()) {
                String dataLine = s.nextLine();
                //Start End [Obstacles]
                String[] coordinates = dataLine.split(" ");
                List<Position2D> positions = new ArrayList<>();
                for (int i = 0; i < coordinates.length; i++) {
                    String[] pos = coordinates[i].split(",");
                    positions.add(new Position2D(Integer.parseInt(pos[0]), Integer.parseInt(pos[1])));
                }
                int maxX = positions.stream().max(Comparator.comparing(v->v.x)).get().x+3;
                int maxY = positions.stream().max(Comparator.comparing(v->v.y)).get().y+3;
                EnvironmentBuilder eb = new EnvironmentBuilder();
                eb.setNbLine(maxY);
                eb.setNbCol(maxX);
                ///Reword des positions
                for(Position2D pos : positions)
                {
                    pos.x +=1;
                    pos.y = maxY-2-pos.y;
                }
                eb.setStartingPos(positions.get(0));
                eb.setEndingPos(positions.get(1));
                List<Position2D> obstacles = positions.subList(2, positions.size());
                env= eb.buildWObstacles(obstacles);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return env;
    }
}
