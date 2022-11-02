package GeneticAlgorithm.CrossOver;

import Game.Creature.Creature;
import Game.Game;
import Game.Movement.MovementConst;

import java.util.ArrayList;
import java.util.List;

public class CrossOver {
    protected double rate;

    public CrossOver(double rate) {
        this.rate = rate;
    }

    public Creature crossOver(Game game1, Game game2){
        List<MovementConst> movements = new ArrayList<>();
        int size = game1.getCreature().getMovements().size();
        for(int i = 0; i<size;i++)
        {
            if(Math.random()<=rate)
                movements.add(game1.getCreature().getMovements().get(i));
            else
                movements.add(game2.getCreature().getMovements().get(i));
        }
        return new Creature(movements);
    }
}
