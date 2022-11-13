package GeneticAlgorithm.Configuration;

public class GAConfig {

    private double percentageParentsToKeep;
    private int nbCreature;
    private double solution;
    private int nbThread;
    private SelectionConfig parentSelection;
    private SelectionConfig evolveSelection;
    private double mutationFlipRate;
    private double mutationAddRate;
    private double mutationSubRate;
    private int maxNbTick;
    public GAConfig(){}

    public GAConfig(double percentageParentsToKeep, int nbCreature, double solution, int nbThread, SelectionConfig parentSelection, SelectionConfig evolveSelection,
                    double mutationFlipRate, double mutationAddRate, double mutationSubRate, int maxNbTick) {
        this.percentageParentsToKeep = percentageParentsToKeep;
        this.nbCreature = nbCreature;
        this.solution = solution;
        this.nbThread = nbThread;
        this.parentSelection = parentSelection;
        this.evolveSelection = evolveSelection;
        this.mutationFlipRate = mutationFlipRate;
        this.mutationAddRate = mutationAddRate;
        this.mutationSubRate = mutationSubRate;
        this.maxNbTick = maxNbTick;
    }

    public int getNbThread() {
        return nbThread;
    }

    public void setNbThread(int nbThread) {
        this.nbThread = nbThread;
    }

    public double getPercentageParentsToKeep() {
        return percentageParentsToKeep/100.0;
    }

    public void setPercentageParentsToKeep(double percentageParentsToKeep) {
        this.percentageParentsToKeep = percentageParentsToKeep;
    }

    public int getNbCreature() {
        return nbCreature;
    }

    public void setNbCreature(int nbCreature) {
        this.nbCreature = nbCreature;
    }

    public double getSolution() {
        return solution;
    }

    public void setSolution(double solution) {
        this.solution = solution;
    }

    public SelectionConfig getParentSelection() {
        return parentSelection;
    }

    public void setParentSelection(SelectionConfig parentSelection) {
        this.parentSelection = parentSelection;
    }

    public SelectionConfig getEvolveSelection() {
        return evolveSelection;
    }

    public void setEvolveSelection(SelectionConfig evolveSelection) {
        this.evolveSelection = evolveSelection;
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

    public int getMaxNbTick() {
        return maxNbTick;
    }

    public void setMaxNbTick(int maxNbTick) {
        this.maxNbTick = maxNbTick;
    }

    @Override
    public String toString() {
        return "GAConfig{" +
                "percentageParentsToKeep=" + percentageParentsToKeep +
                ", nbCreature=" + nbCreature +
                ", solution=" + solution +
                ", parentSelection=" + parentSelection +
                ", evolveSelection=" + evolveSelection +
                ", mutationFlipRate=" + mutationFlipRate +
                ", mutationAddRate=" + mutationAddRate +
                ", mutationSubRate=" + mutationSubRate +
                '}';
    }
}
