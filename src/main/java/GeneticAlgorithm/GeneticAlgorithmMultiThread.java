package GeneticAlgorithm;

import Game.Debug.DebugGame;
import Game.Environment.Environment;
import Game.Game;
import GeneticAlgorithm.Selection.Selection;

import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GeneticAlgorithmMultiThread extends GeneticAlgorithm{

    private int nbThread;

    public GeneticAlgorithmMultiThread(int nbThread) {
        this.nbThread = nbThread;
    }

    public GeneticAlgorithmMultiThread(double uniformRate, double mutationFlipRate, double mutationAddRate, double mutationSubRate,
                                       double percentageParentsToKeep, double solution, Environment environment,
                                       int maxNbTick,int nbThread) {
        super(uniformRate, mutationFlipRate, mutationAddRate, mutationSubRate, percentageParentsToKeep, solution, environment, maxNbTick);
        this.nbThread = nbThread;
    }

    public GeneticAlgorithmMultiThread(int nbCreature, double mutationFlipRate, double mutationAddRate, double mutationSubRate,
                                       double percentageParentsToKeep, double solution, Selection parentSelection, Selection crossOverSelection,
                                       int maxNbTick, int nbThread) {
        super(nbCreature, mutationFlipRate, mutationAddRate, mutationSubRate, percentageParentsToKeep, solution, parentSelection, crossOverSelection, maxNbTick);
        this.nbThread = nbThread;
    }



    private void dispatchGameStarting(Population pop,int nbCreaturePerThread, ExecutorService executorService) throws InterruptedException {
        int i;
        for(i =0;i<nbThread-1;i++)
        {
            int finalI = i;
            executorService.execute(() -> {
                try {
                    pop.startGames((finalI *nbCreaturePerThread), (finalI*(nbCreaturePerThread*2)), this.maxNbTick);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        int finalI = i;
        executorService.execute(()->{
            try {
                pop.startGames((finalI *nbCreaturePerThread), this.maxNbTick);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }
    private void dispatchGameStartingDebug(Population pop,int nbCreaturePerThread, ExecutorService executorService) throws InterruptedException {
        int i;
        int time = 1;
        TimeUnit unit = TimeUnit.SECONDS;
        for(i =0;i<nbThread-1;i++)
        {
            int finalI = i;
            executorService.execute(() -> {
                try {
                    pop.startGamesDebug((finalI *nbCreaturePerThread), (finalI*(nbCreaturePerThread*2)), this.maxNbTick, time, unit);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        int finalI = i;
        executorService.execute(()->{
            try {
                pop.startGamesDebug((finalI *nbCreaturePerThread), this.maxNbTick, time, unit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        executorService.awaitTermination(this.maxNbTick*time+1, unit);
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
            dispatchGameStarting(globalPop, nbCreaturePerThread, executorService);
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

    @Override
    public Game runAlgorithmDebug(int timeTick, TimeUnit timeUnit) throws InterruptedException, CloneNotSupportedException, IOException {
        pop = new Population(nbCreature,environment, true);
        int generationCount = 0;
        int nbCreaturePerThread = nbCreature/nbThread;
        ExecutorService executorService = Executors.newFixedThreadPool(nbThread);
        int nbGeneration = displayDebugMenu();

        int i = 0;
        while(nbGeneration!=0)
        {
            i=0;
            while(i<nbGeneration)
            {
                watcher.start();
                DebugGame.showProgressBar(nbGeneration,i);
                dispatchGameStartingDebug(pop, nbCreaturePerThread, executorService);
                i++;
                pop = evolvePopulation(pop);
                watcher.interrupt();
            }

            generationCount+=nbGeneration;
            System.out.println("Generation : " + generationCount);
            System.out.println("Best score : " + pop.getFittest().getScore());
            System.out.println("Movement length : " + pop.getFittest().getCreature().getMovements().size());
            nbGeneration = displayDebugMenu();
        }

        return pop.getFittest();
    }
}
