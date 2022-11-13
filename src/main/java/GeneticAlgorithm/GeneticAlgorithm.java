package GeneticAlgorithm;

import Game.Creature.Creature;
import Game.Environment.Environment;
import Game.Game;
import GeneticAlgorithm.Selection.Selection;
import GeneticAlgorithm.Utility.CrossOverUtil;
import GeneticAlgorithm.Utility.MutationUtil;
import GeneticAlgorithm.Utility.SelectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static Util.Util.input;

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
        uniformRate = 0.5;
        mutationFlipRate = 0.025;
        mutationAddRate = 0.025;
        mutationSubRate = 0.025;
        this.solution = 0;
        this.environment = null;
        this.percentageParentsToKeep = 0.0;
        this.maxNbTick = 1000;
    }

    protected GeneticAlgorithm(double uniformRate, double mutationFlipRate, double mutationAddRate, double mutationSubRate, double percentageParentsToKeep, double solution, Environment environment,
                               int maxNbTick) {
        this.uniformRate = uniformRate;
        this.mutationFlipRate = mutationFlipRate;
        this.mutationAddRate = mutationAddRate;
        this.mutationSubRate = mutationSubRate;
        this.percentageParentsToKeep = percentageParentsToKeep;
        this.solution = solution;
        this.environment = environment;
        this.maxNbTick = maxNbTick;
    }

    protected GeneticAlgorithm(int nbCreature, double mutationFlipRate, double mutationAddRate, double mutationSubRate, double percentageParentsToKeep,
                               double solution, Selection parentSelection, Selection crossOverSelection, int maxNbTick) {
        this.nbCreature = nbCreature;
        this.mutationFlipRate = mutationFlipRate;
        this.mutationAddRate = mutationAddRate;
        this.mutationSubRate = mutationSubRate;
        this.percentageParentsToKeep = percentageParentsToKeep;
        this.solution = solution;
        this.parentSelection = parentSelection;
        this.crossOverSelection = crossOverSelection;
        this.maxNbTick = maxNbTick;
    }

    public abstract Game runAlgorithm(int maxIter) throws InterruptedException, CloneNotSupportedException;

    public abstract Game runAlgorithmDebug(int timeTick, TimeUnit timeUnit) throws InterruptedException, CloneNotSupportedException, IOException;

    public Population evolvePopulation(Population pop) throws CloneNotSupportedException {
        Population newPop = new Population(pop.getGames().size(), (Environment) this.environment.clone(), false);
        int elitismOffset = 0;
        if (this.percentageParentsToKeep != 0.0) {
            elitismOffset = (int) (pop.getGames().size() * percentageParentsToKeep);
            newPop.getGames().addAll(SelectionUtil.tournamentSelection(pop, 5, elitismOffset));

        }

        for (int i = elitismOffset; i < pop.getGames().size(); i++) {
            Game g1 = SelectionUtil.tournamentSelection(pop, 10, 1).get(0);
            Game g2 = SelectionUtil.tournamentSelection(pop, 10, 1).get(0);
            Creature newCreature = CrossOverUtil.crossOverKeepFromBest(g1, g2, this.uniformRate);
            newPop.getGames().add(i, new Game((Environment) this.environment.clone(), newCreature));
        }
        for (int i = elitismOffset; i < pop.getGames().size(); i++) {
            newPop.getGame(i).setCreature(MutationUtil.mutate(newPop.getGame(i), this.mutationFlipRate, this.mutationAddRate, this.mutationSubRate));

        }

        return newPop;
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
}
