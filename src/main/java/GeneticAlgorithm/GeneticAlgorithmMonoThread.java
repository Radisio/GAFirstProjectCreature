package GeneticAlgorithm;

import Game.Environment.Environment;
import Game.Game;
import GeneticAlgorithm.Selection.Selection;

public class GeneticAlgorithmMonoThread extends GeneticAlgorithm {

    public GeneticAlgorithmMonoThread() {
        super();
    }

    public GeneticAlgorithmMonoThread(double uniformRate, double mutationFlipRate, double mutationAddRate, double mutationSubRate, double percentageParentsToKeep, double solution, Environment environment) {
        super(uniformRate, mutationFlipRate, mutationAddRate, mutationSubRate, percentageParentsToKeep, solution, environment);
    }

    public GeneticAlgorithmMonoThread(int nbCreature, double mutationFlipRate, double mutationAddRate, double mutationSubRate, double percentageParentsToKeep, double solution, Selection parentSelection, Selection crossOverSelection) {
        super(nbCreature, mutationFlipRate, mutationAddRate, mutationSubRate, percentageParentsToKeep, solution, parentSelection, crossOverSelection);
    }

    @Override
    public Game runAlgorithm( int maxIter) throws InterruptedException, CloneNotSupportedException {
        Population myPop = new Population(nbCreature,environment, true);
        int generationCount = 1;
        myPop.startGames();
        while(myPop.getFittest().getScore()>solution && generationCount<maxIter)
        {

            System.out.println("Generation : " + generationCount);
            System.out.println("Best score : " + myPop.getFittest().getScore());
            System.out.println("Movement length : " + myPop.getFittest().getCreature().getMovements().size());
            myPop=evolvePopulation(myPop);
            myPop.startGames();

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


}
