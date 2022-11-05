package GeneticAlgorithm;

import Game.Creature.Creature;
import Game.Environment.Environment;
import Game.Game;
import GeneticAlgorithm.Selection.Selection;
import GeneticAlgorithm.Utility.CrossOverUtil;
import GeneticAlgorithm.Utility.MutationUtil;
import GeneticAlgorithm.Utility.SelectionUtil;

public abstract class GeneticAlgorithm {
    protected double uniformRate;
    protected double mutationFlipRate;
    protected double mutationAddRate;
    protected double mutationSubRate;
    protected double percentageParentsToKeep;
    protected double solution;
    protected Environment environment;
    protected Selection parentSelection;
    protected Selection crossOverSelection;
    protected int nbCreature;

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    protected GeneticAlgorithm() {
        uniformRate = 0.5;
        mutationFlipRate = 0.025;
        mutationAddRate = 0.025;
        mutationSubRate = 0.025;
        this.solution = 0;
        this.environment = null;
        this.percentageParentsToKeep = 0.0;
    }

    protected GeneticAlgorithm(double uniformRate, double mutationFlipRate, double mutationAddRate, double mutationSubRate, double percentageParentsToKeep, double solution, Environment environment) {
        this.uniformRate = uniformRate;
        this.mutationFlipRate = mutationFlipRate;
        this.mutationAddRate = mutationAddRate;
        this.mutationSubRate = mutationSubRate;
        this.percentageParentsToKeep = percentageParentsToKeep;
        this.solution = solution;
        this.environment = environment;
    }

    protected GeneticAlgorithm(int nbCreature, double mutationFlipRate, double mutationAddRate, double mutationSubRate, double percentageParentsToKeep,
                               double solution, Selection parentSelection, Selection crossOverSelection) {
        this.nbCreature = nbCreature;
        this.mutationFlipRate = mutationFlipRate;
        this.mutationAddRate = mutationAddRate;
        this.mutationSubRate = mutationSubRate;
        this.percentageParentsToKeep = percentageParentsToKeep;
        this.solution = solution;
        this.parentSelection = parentSelection;
        this.crossOverSelection = crossOverSelection;
    }

    public abstract Game runAlgorithm(int maxIter) throws InterruptedException, CloneNotSupportedException ;
    public Population evolvePopulation(Population pop) throws CloneNotSupportedException {
        Population newPop = new Population(pop.getGames().size(),(Environment) this.environment.clone(), false);
        int elitismOffset = 0;
        if(this.percentageParentsToKeep!=0.0){
            elitismOffset=(int)(pop.getGames().size()*percentageParentsToKeep);
            newPop.getGames().addAll(SelectionUtil.tournamentSelection(pop, 5, elitismOffset ));

        }

        for(int i = elitismOffset; i<pop.getGames().size();i++)
        {
            Game g1 = SelectionUtil.tournamentSelection(pop, 10, 1).get(0);
            Game g2 = SelectionUtil.tournamentSelection(pop, 10, 1).get(0);
            Creature newCreature = CrossOverUtil.crossOverKeepFromBest(g1,g2, this.uniformRate);
            newPop.getGames().add(i, new Game((Environment) this.environment.clone(), newCreature));
        }
        for (int i = elitismOffset; i < pop.getGames().size(); i++) {
            newPop.getGame(i).setCreature(MutationUtil.mutate(newPop.getGame(i), this.mutationFlipRate, this.mutationAddRate, this.mutationSubRate));

        }

        return newPop;
    }
}