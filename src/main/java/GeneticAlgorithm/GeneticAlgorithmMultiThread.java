package GeneticAlgorithm;

import Game.Environment.Environment;
import Game.Game;
import GeneticAlgorithm.Selection.Selection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GeneticAlgorithmMultiThread extends GeneticAlgorithm{

    private int nbThread;

    public GeneticAlgorithmMultiThread(int nbThread) {
        this.nbThread = nbThread;
    }

    public GeneticAlgorithmMultiThread(double uniformRate, double mutationFlipRate, double mutationAddRate, double mutationSubRate, double percentageParentsToKeep, double solution, Environment environment, int nbThread) {
        super(uniformRate, mutationFlipRate, mutationAddRate, mutationSubRate, percentageParentsToKeep, solution, environment);
        this.nbThread = nbThread;
    }

    public GeneticAlgorithmMultiThread(int nbCreature, double mutationFlipRate, double mutationAddRate, double mutationSubRate, double percentageParentsToKeep, double solution, Selection parentSelection, Selection crossOverSelection, int nbThread) {
        super(nbCreature, mutationFlipRate, mutationAddRate, mutationSubRate, percentageParentsToKeep, solution, parentSelection, crossOverSelection);
        this.nbThread = nbThread;
    }



    private void dispatchGameStarting(Population pop,int nbCreaturePerThread, ExecutorService executorService) throws InterruptedException {
        int i;
        for(i =0;i<nbThread-1;i++)
        {
            int finalI = i;
            executorService.execute(() -> {
                try {
                    pop.startGames((finalI *nbCreaturePerThread), (finalI*(nbCreaturePerThread*2)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        int finalI = i;
        executorService.execute(()->{
            try {
                pop.startGames((finalI *nbCreaturePerThread));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }
    @Override
    public Game runAlgorithm(int maxIter) throws InterruptedException, CloneNotSupportedException {
        Population globalPop = new Population(nbCreature,environment, true);
        int nbCreaturePerThread = nbCreature/nbThread;
        ExecutorService executorService = Executors.newFixedThreadPool(nbThread);
        int generationCount = 1;
        dispatchGameStarting(globalPop, nbCreaturePerThread, executorService);
        while(globalPop.getFittest().getScore()>solution && generationCount<maxIter)
        {

            System.out.println("Generation : " + generationCount);
            System.out.println("Best score : " + globalPop.getFittest().getScore());
            System.out.println("Movement length : " + globalPop.getFittest().getCreature().getMovements().size());
            globalPop=evolvePopulation(globalPop);
            globalPop.startGames();

            generationCount++;
        }
        if(generationCount==maxIter)
            System.out.println("Limit of iterations reached !");
        else
            System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        System.out.println("Genes: ");
        System.out.println(globalPop.getFittest());
        return globalPop.getFittest();
    }
}
