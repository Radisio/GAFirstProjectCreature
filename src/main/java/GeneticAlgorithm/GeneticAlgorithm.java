package GeneticAlgorithm;

import Game.Creature.Creature;
import Game.Environment.Environment;
import Game.Game;
import GeneticAlgorithm.CrossOver.CrossOver;
import GeneticAlgorithm.Selection.Selection;
import GeneticAlgorithm.Utility.MutationUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static Util.Util.input;

public abstract class GeneticAlgorithm implements Cloneable{
    protected double mutationFlipRate;
    protected double mutationAddRate;
    protected double mutationSubRate;
    protected double percentageParentsToKeep;
    protected double solution;
    protected Environment environment;
    protected Selection parentSelection;
    protected Selection crossOverSelection;
    protected CrossOver crossOver;
    protected int generationCount;

    protected int nbCreature;
    protected int maxNbTick;
    protected Population pop;
    protected final Thread watcher = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                String userInput = null;
                while (userInput == null) {
                    System.out.println("Display best creature : 's' (click again to stop the display)");
                    try {
                        userInput = input();
                    } catch (IOException e) {
                        e.printStackTrace();
                        userInput = null;
                    }
                    if (userInput!=null && !userInput.isBlank() && !userInput.isEmpty()) {
                        userInput = userInput.trim();
                        if (!userInput.equals("s"))
                            userInput = null;
                    }
                }
                int index = pop.getFittestRunningIndex();
                pop.getGame(index).setDebug(true);
                userInput = null;
                while (userInput == null) {
                    try {
                        userInput = input();
                    } catch (IOException e) {
                        e.printStackTrace();
                        userInput = null;
                    }
                    if (userInput!=null && !userInput.isBlank() && !userInput.isEmpty()) {
                        userInput = userInput.trim();
                        if (!userInput.equals("s"))
                            userInput = null;
                    }
                }
                pop.getGame(index).setDebug(false);
            }
        }
    });

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    protected GeneticAlgorithm() {
        mutationFlipRate = 0.025;
        mutationAddRate = 0.025;
        mutationSubRate = 0.025;
        this.solution = 0;
        this.environment = null;
        this.percentageParentsToKeep = 0.0;
        this.maxNbTick = 1000;
    }
    protected GeneticAlgorithm(int maxNbTick)
    {
        this.maxNbTick = maxNbTick;
    }
    protected GeneticAlgorithm(double mutationFlipRate, double mutationAddRate, double mutationSubRate, double percentageParentsToKeep, double solution, Environment environment,
                               int maxNbTick) {
        this.mutationFlipRate = mutationFlipRate;
        this.mutationAddRate = mutationAddRate;
        this.mutationSubRate = mutationSubRate;
        this.percentageParentsToKeep = percentageParentsToKeep;
        this.solution = solution;
        this.environment = environment;
        this.maxNbTick = maxNbTick;
    }

    protected GeneticAlgorithm(int nbCreature, double mutationFlipRate, double mutationAddRate, double mutationSubRate, double percentageParentsToKeep,
                               double solution, Selection parentSelection, Selection crossOverSelection, CrossOver crossOver, int maxNbTick) {
        this.nbCreature = nbCreature;
        this.mutationFlipRate = mutationFlipRate;
        this.mutationAddRate = mutationAddRate;
        this.mutationSubRate = mutationSubRate;
        this.percentageParentsToKeep = percentageParentsToKeep;
        this.solution = solution;
        this.parentSelection = parentSelection;
        this.crossOverSelection = crossOverSelection;
        this.crossOver = crossOver;
        this.maxNbTick = maxNbTick;
    }

    public double getSolution() {
        return solution;
    }

    public void setSolution(double solution) {
        this.solution = solution;
    }

    public abstract Game runAlgorithm(int maxIter) throws InterruptedException, CloneNotSupportedException;

    public abstract Game runAlgorithmDebug(int timeTick, TimeUnit timeUnit) throws InterruptedException, CloneNotSupportedException, IOException;
    public abstract String runAlgorithmHistory(int maxIter) throws InterruptedException, CloneNotSupportedException;
    public Population evolvePopulation(Population pop) throws CloneNotSupportedException {
        Population newPop = new Population(0, (Environment) this.environment.clone(), false);
        int elitismOffset = 0;
        if (this.percentageParentsToKeep != 0.0) {
            elitismOffset = (int) (pop.getGames().size() * percentageParentsToKeep);
            newPop.getGames().addAll(parentSelection.select(pop, elitismOffset));

        }
        for (int i = elitismOffset; i < pop.getGames().size(); i++) {
            Game g1 = crossOverSelection.select(pop,  1).get(0);
            Game g2 = crossOverSelection.select(pop, 1).get(0);
            Creature newCreature = crossOver.crossOver(g1,g2);
            newPop.getGames().add(i, new Game((Environment) this.environment.clone(), newCreature));
        }
        for (int i = elitismOffset; i < pop.getGames().size(); i++) {
            newPop.getGame(i).setCreature(MutationUtil.mutate(newPop.getGame(i), this.mutationFlipRate, this.mutationAddRate, this.mutationSubRate));

        }
        return newPop;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        GeneticAlgorithm cloned = (GeneticAlgorithm) super.clone();
        cloned.setSolution(this.getSolution());
        cloned.setCrossOverSelection(this.getCrossOverSelection());
        cloned.setNbCreature(getNbCreature());
        cloned.setPercentageParentsToKeep(this.getPercentageParentsToKeep());
        cloned.setMutationFlipRate(this.getMutationFlipRate());
        cloned.setMutationSubRate(this.getMutationSubRate());
        cloned.setMutationAddRate(this.getMutationAddRate());
        cloned.setCrossOver(this.getCrossOver());
        cloned.setParentSelection(this.getParentSelection());
        cloned.setEnvironment(this.getEnvironment());
        cloned.setMaxNbTick(this.getMaxNbTick());
        return cloned;
    }

    public int displayDebugMenu() throws IOException {
        System.out.println("-------- Debug Mode --------");
        System.out.println("Next generation: 'n'");
        System.out.println("Next G additional generations : 'n G' (eg. n 5)");
        System.out.println("Quit : any");
        String userInput = input();
        if (!userInput.isEmpty() && !userInput.isBlank()) {
            userInput = userInput.trim();
            if (userInput.equals("n")) {
                return 1;
            } else {
                if (userInput.matches("/^([n]\\s)+[0-9]+")) {
                    String intValue = userInput.substring(2);
                    return Integer.parseInt(intValue);
                }
            }
        }
        return 0;
    }



    public double getMutationFlipRate() {
        return mutationFlipRate;
    }

    public void setMutationFlipRate(double mutationFlipRate) {
        this.mutationFlipRate = mutationFlipRate;
    }

    public double getMutationAddRate() {
        return mutationAddRate;
    }

    public void setMutationAddRate(double mutationAddRate) {
        this.mutationAddRate = mutationAddRate;
    }

    public double getMutationSubRate() {
        return mutationSubRate;
    }

    public void setMutationSubRate(double mutationSubRate) {
        this.mutationSubRate = mutationSubRate;
    }

    public double getPercentageParentsToKeep() {
        return percentageParentsToKeep;
    }

    public void setPercentageParentsToKeep(double percentageParentsToKeep) {
        this.percentageParentsToKeep = percentageParentsToKeep;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public Selection getParentSelection() {
        return parentSelection;
    }

    public void setParentSelection(Selection parentSelection) {
        this.parentSelection = parentSelection;
    }

    public Selection getCrossOverSelection() {
        return crossOverSelection;
    }

    public void setCrossOverSelection(Selection crossOverSelection) {
        this.crossOverSelection = crossOverSelection;
    }

    public int getNbCreature() {
        return nbCreature;
    }

    public void setNbCreature(int nbCreature) {
        this.nbCreature = nbCreature;
    }

    public int getMaxNbTick() {
        return maxNbTick;
    }

    public void setMaxNbTick(int maxNbTick) {
        this.maxNbTick = maxNbTick;
    }

    public CrossOver getCrossOver() {
        return crossOver;
    }

    public void setCrossOver(CrossOver crossOver) {
        this.crossOver = crossOver;
    }

    public int getGenerationCount() {
        return generationCount;
    }

    @Override
    public String toString() {
        return "GeneticAlgorithm{" +
                "mutationFlipRate=" + mutationFlipRate +
                ", mutationAddRate=" + mutationAddRate +
                ", mutationSubRate=" + mutationSubRate +
                ", percentageParentsToKeep=" + percentageParentsToKeep +
                ", solution=" + solution +
                ", environment=" + environment +
                ", parentSelection=" + parentSelection +
                ", crossOverSelection=" + crossOverSelection +
                ", crossOver=" + crossOver +
                ", generationCount=" + generationCount +
                ", nbCreature=" + nbCreature +
                ", maxNbTick=" + maxNbTick +
                ", pop=" + pop +
                ", watcher=" + watcher +
                '}';
    }
}
