package GeneticAlgorithm;

import Game.Const.DesignConst;
import Game.Creature.Creature;
import Game.Environment.Environment;
import Game.Game;
import Game.Movement.MovementUtil;
import MathUtil.Position2D;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Population {

    private List<Game> games;
    private Environment environment;
    public Population(int size, Environment environment, boolean createNew) {
        games = new ArrayList<>();
        this.environment = environment;
        if (createNew) {
            createNewPopulation(size);
        }
    }

    public Game getGame(int index) {
        return games.get(index);
    }


    public void startGamesDebug(int maxNbTick, int time, TimeUnit unit) throws InterruptedException {
        startGamesDebug(0, games.size(), maxNbTick, time, unit);
    }

    public void startGamesDebug(int start, int maxNbTick, int time, TimeUnit unit) throws InterruptedException{
        startGamesDebug(start, games.size(), maxNbTick, time, unit);
    }
    public void startGamesDebug(int start, int end, int maxNbTick)throws InterruptedException {
        /*for(Game g : games)
            g.startNoDisplay();*/
        startGamesDebug(start, end, maxNbTick, 1, TimeUnit.SECONDS);
    }
    public void startGamesDebug(int start, int end, int maxNbTick, int time, TimeUnit unit) throws InterruptedException {
        for(int i = start;i<end;i++)
        {
            games.get(i).startDebug(maxNbTick, time, unit);
        }
    }
    public void startGames(int maxNbTick) throws InterruptedException {
        startGames(0, games.size(), maxNbTick);
    }

    public void startGames(int start, int maxNbTick) throws InterruptedException{
        startGames(start, games.size(), maxNbTick);
    }
    public void startGames(int start, int end, int maxNbTick)throws InterruptedException {
        /*for(Game g : games)
            g.startNoDisplay();*/
        for(int i = start;i<end;i++)
        {
            games.get(i).startNoDisplayLimited(maxNbTick);
        }
    }



    private void createNewPopulation(int size) {
        for (int i = 0; i < size; i++) {
            Creature newIndividual = new Creature(MovementUtil.generateRandomMovement((int)(Math.random()*200 +1)));
            games.add(i, new Game(new Environment(this.environment), newIndividual));
            if(i==1)
                games.get(0).getEnvironment().setBoardOccupation(new Position2D(0,0), DesignConst.ENDING_FLAG);

        }
    }

    public Game getFittest()
    {
                return this.games.stream().min((Comparator.comparingDouble(o -> o.getScore()))).get();
    }

    public int getFittestRunningIndex()
    {
        return this.games.indexOf(this.games.stream().filter(Game::isRunning).min((Comparator.comparingDouble(o -> o.getScore()))).get());
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
