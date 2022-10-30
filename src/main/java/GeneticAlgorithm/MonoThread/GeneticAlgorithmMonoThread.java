package GeneticAlgorithm.MonoThread;

import Game.Creature.Creature;
import Game.Environment.Environment;
import Game.Game;
import GeneticAlgorithm.Utility.CrossOverUtil;
import GeneticAlgorithm.Utility.MutationUtil;
import GeneticAlgorithm.Utility.SelectionUtil;

public class GeneticAlgorithmMonoThread {

    private double uniformRate = 0.5;
    private double allOrNoneRate = 0.25;
    private double mutationFlipRate = 0.025;
    private double mutationAddRate = 0.025;
    private double mutationSubRate = 0.025;
    private double percentageParentsToKeep;
    private Environment environment;

    public Game runAlgorithm(Environment environment, int nbCreature,double solution, int maxIter, double percentageParentsToKeep) throws InterruptedException, CloneNotSupportedException {
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
