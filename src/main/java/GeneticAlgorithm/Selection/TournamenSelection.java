package GeneticAlgorithm.Selection;

import Game.Game;
import GeneticAlgorithm.Population;

import java.util.ArrayList;
import java.util.List;

public class TournamenSelection extends Selection {
    private final int tournamentSize;

    public TournamenSelection(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    @Override
    public List<Game> select(Population pop,int returnedSize) {
        List<Game> returnedList = new ArrayList<>();
        for(int i = 0;i<returnedSize;i++)
        {
            Population tournament = new Population(tournamentSize, pop.getEnvironment(),false);
            for(int j = 0;j<tournamentSize;j++)
            {
                int randomId = (int)(Math.random() * pop.getGames().size());
                tournament.getGames().add(j, pop.getGame(randomId));
            }
            //System.out.println("Tournament size : " + tournament.getGames().size());
            returnedList.add(tournament.getFittest());
            //System.out.println("returnedList size : " + returnedList.size());
            //  System.out.println("NB : " + nb);
        }
        return returnedList;
    }
}
