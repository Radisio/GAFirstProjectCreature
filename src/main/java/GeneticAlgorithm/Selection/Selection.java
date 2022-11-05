package GeneticAlgorithm.Selection;

import Game.Game;
import GeneticAlgorithm.Population;

import java.util.List;

public abstract class Selection {
    protected double rate;

    public Selection(double rate) {
        this.rate = rate;
    }

    public abstract List<Game> select(Population pop, int returnedSize);
}
