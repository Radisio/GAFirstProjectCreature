package GeneticAlgorithm;

import Game.Debug.DebugGame;
import Game.Environment.Environment;
import Game.Game;
import GeneticAlgorithm.CrossOver.CrossOver;
import GeneticAlgorithm.Selection.Selection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GeneticAlgorithmMultiThread extends GeneticAlgorithm{

    private int nbThread;

    public GeneticAlgorithmMultiThread(int nbThread) {
        this.nbThread = nbThread;
    }

    public GeneticAlgorithmMultiThread(double mutationFlipRate, double mutationAddRate, double mutationSubRate,
                                       double percentageParentsToKeep, double solution, Environment environment,
                                       int maxNbTick,int nbThread) {
        super(mutationFlipRate, mutationAddRate, mutationSubRate, percentageParentsToKeep, solution, environment, maxNbTick);
        this.nbThread = nbThread;
    }

    public GeneticAlgorithmMultiThread(int maxNbTick, int nbThread)
    {
        super(maxNbTick);
        this.nbThread = nbThread;
    }

    public GeneticAlgorithmMultiThread(int nbCreature, double mutationFlipRate, double mutationAddRate, double mutationSubRate,
                                       double percentageParentsToKeep, double solution, Selection parentSelection, Selection crossOverSelection,
                                       CrossOver crossOver, int maxNbTick, int nbThread) {
        super(nbCreature, mutationFlipRate, mutationAddRate, mutationSubRate, percentageParentsToKeep, solution, parentSelection, crossOverSelection,crossOver, maxNbTick);
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
                    System.out.println("Fin");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        int finalI = i;
        executorService.execute(()->{
            try {
                pop.startGames((finalI *nbCreaturePerThread), this.maxNbTick);
                System.out.println("Fin");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        //executorService.shutdown();
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

    private List<Callable<Void>> getCallableList(Population pop, int nbCreaturePerThread)
    {
        List<Callable<Void>> callables = new ArrayList<>();
        int i = 0;
        for(i =0;i<nbThread-1;i++)
        {
            callables.add(new Task(pop, (i *nbCreaturePerThread), ((i+1)*(nbCreaturePerThread)), this.maxNbTick));
        }
        callables.add(new Task(pop,(i *nbCreaturePerThread), this.maxNbTick));
        return callables;
    }
    private void shutdownAndAwaitTermination(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        GeneticAlgorithmMultiThread gamt= (GeneticAlgorithmMultiThread)super.clone();
        gamt.setNbThread(gamt.getNbThread());
        return gamt;
    }

    @Override
    public Game runAlgorithm(int maxIter) throws InterruptedException, CloneNotSupportedException {
        Population globalPop = new Population(nbCreature,environment, true);
        int nbCreaturePerThread = nbCreature/nbThread;
        ExecutorService executorService = Executors.newFixedThreadPool(nbThread);
        List<Callable<Void>> callables = getCallableList(globalPop, nbCreaturePerThread);

        generationCount = 1;
        //dispatchGameStarting(globalPop, nbCreaturePerThread, executorService);
        executorService.invokeAll(callables);
        System.out.println("After invoke all");
        while(globalPop.getFittest().getScore()>solution && generationCount<maxIter)
        {

            System.out.println("Generation : " + generationCount);
            System.out.println("Best score : " + globalPop.getFittest().getScore());
            System.out.println("Movement length : " + globalPop.getFittest().getCreature().getMovements().size());
            globalPop=evolvePopulation(globalPop);
            //dispatchGameStarting(globalPop, nbCreaturePerThread, executorService);
            callables = getCallableList(globalPop, nbCreaturePerThread);
            executorService.invokeAll(callables);

            System.out.println("After invoke all");

            generationCount++;
        }
        if(generationCount==maxIter)
            System.out.println("Limit of iterations reached !");
        else
            System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        System.out.println("Genes: ");
        System.out.println(globalPop.getFittest());
        shutdownAndAwaitTermination(executorService);
        return globalPop.getFittest();
    }

    @Override
    public Game runAlgorithmDebug(int timeTick, TimeUnit timeUnit) throws InterruptedException, CloneNotSupportedException, IOException {
        pop = new Population(nbCreature,environment, true);
        generationCount = 0;
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


    public String runAlgorithmHistory(int maxIter) throws InterruptedException, CloneNotSupportedException {
        Population globalPop = new Population(nbCreature,environment, true);
        int nbCreaturePerThread = nbCreature/nbThread;
        ExecutorService executorService = Executors.newFixedThreadPool(nbThread);
        List<Callable<Void>> callables = getCallableList(globalPop, nbCreaturePerThread);
        generationCount = 1;
        executorService.invokeAll(callables);
        StringBuilder log = new StringBuilder();
        while(generationCount<maxIter)
        {
            log.append(generationCount).append(";").append(globalPop.getFittest().getScore()).append("\n");
            globalPop=evolvePopulation(globalPop);
            //dispatchGameStarting(globalPop, nbCreaturePerThread, executorService);
            callables = getCallableList(globalPop, nbCreaturePerThread);
            executorService.invokeAll(callables);

            generationCount++;
        }
        shutdownAndAwaitTermination(executorService);
        return log.toString();
    }


    public void setNbThread(int nbThread) {
        this.nbThread = nbThread;
    }

    public int getNbThread() {
        return nbThread;
    }
}
