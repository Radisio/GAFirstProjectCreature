package Game;

import Game.Const.DesignConst;
import Game.Environment.Environment;
import Game.Creature.Creature;
import Game.Environment.EnvironmentUtil;
import MathUtil.*;

import java.util.List;
public class Game {
    private Environment environment;
    private Creature creature;
    private double score;
    private Position2D creaturePosition;
    public Game(Environment environment, Creature creature) {
        this.environment = environment;
        this.creature = creature;
        this.creaturePosition=null;
        this.score = Double.MAX_VALUE;
    }

    public double getScore()
    {
        return this.score;
    }

    public void computeScore()
    {
        this.score= MathUtil.euclidianDistance(this.creaturePosition, this.environment.getEndingPos());
    }

    private void display()
    {
        System.out.println("\rScore : " + this.score +"\r");
        System.out.println("\r"+this.environment + "\r");
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
    }

    private void updateCreaturePosition(Position2D pos){
        if(this.environment.setCreaturePosition(pos, this.creaturePosition))
        {
            this.creaturePosition = pos;
        }
    }

    public void startNoDisplay() throws InterruptedException{
        updateCreaturePosition(this.environment.getStartingPos());
        int i = 0;
        computeScore();
        while(this.creaturePosition!=this.environment.getEndingPos() && i<this.creature.getMovements().size())
        {
            List<Position2D> positions = EnvironmentUtil.doMovement(this.environment.getBoard(), this.creaturePosition, this.creature.getMovements().get(i));
            for(Position2D pos : positions)
            {
                updateCreaturePosition(pos);
                computeScore();
            }
            i++;
        }
        if(this.creaturePosition == this.environment.getEndingPos())
            System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHH ON Y EST");
        //computeScore();
        //System.out.println("I : " + i);
        //System.out.println("Crea movement size : " + this.creature.getMovements().size());
        reinitializeEnvironment();

    }

    public void start() throws InterruptedException {
        updateCreaturePosition(this.environment.getStartingPos());
        display();
        int i = 0;
        computeScore();
        while(this.creaturePosition!=this.environment.getEndingPos() && i<this.creature.getMovements().size())
        {
            List<Position2D> positions = EnvironmentUtil.doMovement(this.environment.getBoard(), this.creaturePosition, this.creature.getMovements().get(i));
            for(Position2D pos : positions)
            {
                updateCreaturePosition(pos);
                Thread.sleep(1000);
                computeScore();
                display();
            }
            i++;
        }
        reinitializeEnvironment();

    }

    public void reinitializeEnvironment()
    {
        /*
        if(this.creaturePosition!=null)
        {
            if(creaturePosition==this.environment.getStartingPos())
                this.environment.setBoardOccupation(this.environment.getStartingPos(), DesignConst.STARTING_FLAG);
            else if (creaturePosition==this.environment.getEndingPos())
                this.environment.setBoardOccupation(this.environment.getEndingPos(), DesignConst.ENDING_FLAG);
            else
                this.environment.setBoardOccupation(creaturePosition, DesignConst.EMPTY);

            creaturePosition=null;
        }*/
        if(this.creaturePosition!=null) {
            this.environment.setBoardOccupation(this.creaturePosition, DesignConst.EMPTY);
            creaturePosition=null;
        }
        this.environment.setBoardOccupation(this.environment.getStartingPos(), DesignConst.STARTING_FLAG);
        this.environment.setBoardOccupation(this.environment.getEndingPos(), DesignConst.ENDING_FLAG);


    }

    public Environment getEnvironment() {
        return environment;
    }

    public Creature getCreature() {
        return creature;
    }

    public Position2D getCreaturePosition() {
        return creaturePosition;
    }
}
