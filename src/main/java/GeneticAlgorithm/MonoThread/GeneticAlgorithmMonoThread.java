package GeneticAlgorithm.MonoThread;

import Game.Creature.Creature;
import Game.Environment.Environment;
import Game.Game;
import GeneticAlgorithm.Configuration.GAConfig;
import GeneticAlgorithm.Selection.Selection;
import GeneticAlgorithm.Utility.CrossOverUtil;
import GeneticAlgorithm.Utility.MutationUtil;
import GeneticAlgorithm.Utility.SelectionUtil;

public class GeneticAlgorithmMonoThread {

    private double uniformRate;
    private double mutationFlipRate;
    private double mutationAddRate;
    private double mutationSubRate;
    private double percentageParentsToKeep;
    private double solution;
    private Environment environment;
    private Selection parentSelection;
    private Selection crossOverSelection;
    private int nbCreature;
    public GeneticAlgorithmMonoThread(){
        uniformRate = 0.5;
        mutationFlipRate = 0.025;
        mutationAddRate = 0.025;
        mutationSubRate = 0.025;
        this.solution = 0;
        this.environment=null;
        this.percentageParentsToKeep=0.0;
    }

    public GeneticAlgorithmMonoThread(double uniformRate, double mutationFlipRate, double mutationAddRate, double mutationSubRate, double percentageParentsToKeep, double solution, Environment environment) {
        this.uniformRate = uniformRate;
        this.mutationFlipRate = mutationFlipRate;
        this.mutationAddRate = mutationAddRate;
        this.mutationSubRate = mutationSubRate;
        this.percentageParentsToKeep = percentageParentsToKeep;
        this.solution = solution;
        this.environment = environment;
    }

    public GeneticAlgorithmMonoThread(int nbCreature, double mutationFlipRate, double mutationAddRate, double mutationSubRate, double percentageParentsToKeep,
                                      double solution,Selection parentSelection, Selection crossOverSelection) {
        this.nbCreature=nbCreature;
        this.mutationFlipRate = mutationFlipRate;
        this.mutationAddRate = mutationAddRate;
        this.mutationSubRate = mutationSubRate;
        this.percentageParentsToKeep = percentageParentsToKeep;
        this.solution = solution;
        this.parentSelection = parentSelection;
        this.crossOverSelection = crossOverSelection;
    }



    public Game runAlgorithm(Environment environment, int maxIter) throws InterruptedException, CloneNotSupportedException {
        this.environment = environment;
        this.percentageParentsToKeep = percentageParentsToKeep;
        Population myPop = new Population(nbCreature,environment, true);
        int generationCount = 1;
        myPop.startGame();
        while(myPop.getFittest().getScore()>solution && generationCount<maxIter)
        {

            System.out.println("Generation : " + generationCount);
            System.out.println("Best score : " + myPop.getFittest().getScore());
            System.out.println("Movement length : " + myPop.getFittest().getCreature().getMovements().size());
            myPop=evolvePopulation(myPop);
            myPop.startGame();

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
