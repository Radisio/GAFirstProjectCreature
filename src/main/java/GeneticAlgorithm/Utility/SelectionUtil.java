package GeneticAlgorithm.Utility;

import Game.Game;
import GeneticAlgorithm.Population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SelectionUtil {
    public static List<Game> tournamentSelection(Population pop, int tournamentSize, int nb)
    {
        List<Game> returnedList = new ArrayList<>();
        for(int i = 0;i<nb;i++)
        {
            Population tournament = new Population(tournamentSize, pop.getEnvironment(),false);
            for(int j = 0;j<tournamentSize;j++)
            {
                int randomId = (int)(Math.random() * pop.getGames().size());
                tournament.getGames().add(j, pop.getGame(randomId));
            }
            returnedList.add(tournament.getFittest());
        }
        return returnedList;
    }
    public static List<Game> bestFitSelection(Population pop, int nb)
    {
        List<Game> fittests = pop.getGames();
        Collections.sort(fittests, Comparator.comparingDouble(Game::getScore));
        fittests.subList(0, nb);
        return fittests;
    }

}
