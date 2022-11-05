package GeneticAlgorithm;

import Game.Creature.Creature;
import Game.Environment.Environment;
import Game.Game;
import Game.Movement.MovementUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    public void startGames() throws InterruptedException {
        startGames(0, games.size());
    }

    public void startGames(int start) throws InterruptedException{
        startGames(start, games.size());
    }
    public void startGames(int start, int end) throws InterruptedException {
        /*for(Game g : games)
            g.startNoDisplay();*/
        for(int i = start;i<end;i++)
        {
            games.get(i).startNoDisplay();
        }
    }



    private void createNewPopulation(int size) {
        for (int i = 0; i < size; i++) {
            Creature newIndividual = new Creature(MovementUtil.generateRandomMovement((int)(Math.random()*200 +1)));
            games.add(i, new Game(new Environment(this.environment), newIndividual));
        }
    }

    public Game getFittest()
    {
       /* Game game = this.games.get(0);
        for(int i =1;i<this.games.size();i++)
        {
            if(this.games.get(i).getScore()<game.getScore())
                game = this.games.get(i);
        }
        return game;*/
       //System.out.println("GetFittest : " + this.games.stream().min((Comparator.comparingDouble(o -> o.getScore()))).get().getScore());
        return this.games.stream().min((Comparator.comparingDouble(o -> o.getScore()))).get();
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
