package GeneticAlgorithm;

import Game.Debug.DebugGame;
import Game.Environment.Environment;
import Game.Game;
import GeneticAlgorithm.CrossOver.CrossOver;
import GeneticAlgorithm.Selection.Selection;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GeneticAlgorithmMonoThread extends GeneticAlgorithm {

    public GeneticAlgorithmMonoThread() {
        super();
    }

    public GeneticAlgorithmMonoThread(double mutationFlipRate, double mutationAddRate, double mutationSubRate,
                                      double percentageParentsToKeep, double solution, Environment environment, int maxNbTick) {
        super(mutationFlipRate, mutationAddRate, mutationSubRate, percentageParentsToKeep, solution, environment, maxNbTick);
    }

    public GeneticAlgorithmMonoThread(int nbCreature, double mutationFlipRate, double mutationAddRate, double mutationSubRate,
                                      double percentageParentsToKeep, double solution, Selection parentSelection, Selection crossOverSelection,
                                      CrossOver crossOver, int maxNbTick) {
        super(nbCreature, mutationFlipRate, mutationAddRate, mutationSubRate, percentageParentsToKeep, solution, parentSelection, crossOverSelection, crossOver, maxNbTick);
    }

    @Override
    public Game runAlgorithm( int maxIter) throws InterruptedException, CloneNotSupportedException {
        Population myPop = new Population(nbCreature,environment, true);
        generationCount = 1;
        myPop.startGames(this.maxNbTick);
        while(myPop.getFittest().getScore()>solution && generationCount<maxIter)
        {
            System.out.println("Generation : " + generationCount);
            System.out.println("Best score : " + myPop.getFittest().getScore());
            System.out.println("Movement length : " + myPop.getFittest().getCreature().getMovements().size());
            myPop=evolvePopulation(myPop);
            myPop.startGames(this.maxNbTick);

            generationCount++;
        }
        if(generationCount==maxIter)
            System.out.println("Limit of iterations reached !");
        else
            System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        System.out.println("Genes: ");
        System.out.println(myPop.getFittest());
        return myPop.getFittest();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /// Pas encore testé
    @Override
    public Game runAlgorithmDebug(int timeTick, TimeUnit timeUnit) throws InterruptedException, CloneNotSupportedException, IOException {
        pop = new Population(nbCreature,environment, true);
        generationCount = 0;
        int nbGeneration = displayDebugMenu();
        int i = 0;
        while(nbGeneration!=0)
        {
            i=0;

            while(i<nbGeneration)
            {
                watcher.start();
                DebugGame.showProgressBar(nbGeneration,i);
                pop.startGamesDebug(this.maxNbTick, timeTick, timeUnit);
                i++;
                pop = evolvePopulation(pop);
            }
            watcher.interrupt();
            generationCount+=nbGeneration;
            System.out.println("Generation : " + generationCount);
            System.out.println("Best score : " + pop.getFittest().getScore());
            System.out.println("Movement length : " + pop.getFittest().getCreature().getMovements().size());
            nbGeneration = displayDebugMenu();
        }

        return pop.getFittest();
    }

    @Override
    public String runAlgorithmHistory(int maxIter) throws InterruptedException, CloneNotSupportedException {
        return null;
    }
}
