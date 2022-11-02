package GeneticAlgorithm.CrossOver;

import Game.Creature.Creature;
import Game.Game;
import Game.Movement.MovementConst;

import java.util.ArrayList;
import java.util.List;

public class KeepFromBestCrossOver extends CrossOver{
    public KeepFromBestCrossOver(double rate) {
        super(rate);
    }

    @Override
    public Creature crossOver(Game game1, Game game2) {
        if(game1.getCreature().getMovements().size() == game2.getCreature().getMovements().size())
            return super.crossOver(game1, game2);

        int sizeMax = game1.getCreature().getMovements().size();
        List<MovementConst> movements = game1.getCreature().getMovements();
        int sizeMin = game2.getCreature().getMovements().size();
        double score = game1.getScore();
        boolean bestIsLongest = game1.getScore() < game2.getScore();
        if(sizeMax < sizeMin)
        {
            sizeMax = game2.getCreature().getMovements().size();
            movements = game2.getCreature().getMovements();
            sizeMin = game1.getCreature().getMovements().size();
            bestIsLongest = game1.getScore() > game2.getScore();
        }
        List<MovementConst> newMovements = new ArrayList<>();
        for(int i = 0;i<sizeMin;i++)
        {
            if(Math.random()<=rate)
                newMovements.add(game1.getCreature().getMovements().get(i));
            else
                newMovements.add(game2.getCreature().getMovements().get(i));
        }
        if(bestIsLongest)
            for(int i = sizeMin; i<sizeMax;i++)
            {
                newMovements.add(movements.get(i));
            }

        return new Creature(newMovements);
    }
}
