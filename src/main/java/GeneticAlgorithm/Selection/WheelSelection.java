package GeneticAlgorithm.Selection;

import Game.Game;
import GeneticAlgorithm.Population;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WheelSelection extends Selection{

    public WheelSelection() {
    }

    @Override
    public List<Game> select(Population pop, int returnedSize) {
        double totalScore = pop.getGames().stream().map(x->x.getScore()).collect(Collectors.summingDouble(Double::doubleValue));
        List<Pair<Game, Double>> gameProba = new ArrayList<>();
        pop.getGames().forEach(x-> gameProba.add(new Pair(x, x.getScore()/totalScore)));
        return (List<Game>)(Object) Arrays.asList(new EnumeratedDistribution<Game>(gameProba).sample(returnedSize));
    }
}
