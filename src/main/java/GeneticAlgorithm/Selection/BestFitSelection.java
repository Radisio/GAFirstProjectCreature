package GeneticAlgorithm.Selection;

import Game.Game;
import GeneticAlgorithm.MonoThread.Population;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BestFitSelection extends Selection{

    public BestFitSelection(double rate) {
        super(rate);
    }

    @Override
    public List<Game> select(Population pop, int returnedSize) {
        List<Game> fittests = pop.getGames();
        Collections.sort(fittests, Comparator.comparingDouble(Game::getScore));
        //Collections.reverse(fittests);
        fittests.subList(0, returnedSize);
        return fittests;
    }
}
