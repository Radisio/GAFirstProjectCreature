package GeneticAlgorithm.Selection;

import Game.Game;
import GeneticAlgorithm.Population;

import java.util.List;

public abstract class Selection {



    public abstract List<Game> select(Population pop, int returnedSize);

}
