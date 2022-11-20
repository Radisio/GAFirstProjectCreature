package GeneticAlgorithm;

import java.util.concurrent.Callable;

public class Task implements Callable<Void> {

    private Population pop;
    private int start;
    private int end;
    private int maxTicks;
    public Task(Population pop, int start, int maxTicks)
    {
        this.pop = pop;
        this.start = start;
        this.end=-1;
        this.maxTicks = maxTicks;
    }

    public Task(Population pop, int start, int end, int maxTicks) {
        this.pop = pop;
        this.start = start;
        this.end = end;
        this.maxTicks = maxTicks;
    }

    @Override
    public Void call() throws Exception {
        if(this.end!=-1)
            pop.startGames(this.start, this.end, this.maxTicks);
        else
            pop.startGames(this.start, this.maxTicks);
        return null;
    }
}
