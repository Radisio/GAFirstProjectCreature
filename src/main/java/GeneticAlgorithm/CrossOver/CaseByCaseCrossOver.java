package GeneticAlgorithm.CrossOver;

import Game.Creature.Creature;
import Game.Game;
import Game.Movement.MovementConst;

import java.util.ArrayList;
import java.util.List;

public class CaseByCaseCrossOver extends CrossOver{
    public CaseByCaseCrossOver(double rate) {
        super(rate);
    }

    @Override
    public Creature crossOver(Game game1, Game game2) {
        if(game1.getCreature().getMovements().size() == game2.getCreature().getMovements().size())
        {
            return crossOver(game1, game2);
        }
        else{
            int sizeMax = game1.getCreature().getMovements().size();
            List<MovementConst> movements = game1.getCreature().getMovements();
            int sizeMin = game2.getCreature().getMovements().size();
            double score = game1.getScore();
            if(sizeMax < sizeMin)
            {
                sizeMax = game2.getCreature().getMovements().size();
                movements = game2.getCreature().getMovements();
                sizeMin = game1.getCreature().getMovements().size();
            }
            List<MovementConst> newMovements = new ArrayList<>();
            for(int i = 0;i<sizeMin;i++)
            {
                if(Math.random()<=rate)
                    newMovements.add(game1.getCreature().getMovements().get(i));
                else
                    newMovements.add(game2.getCreature().getMovements().get(i));
            }
            for(int i = sizeMin; i<sizeMax;i++)
            {
                if(Math.random() <= rate) {
                    newMovements.add(movements.get(i));
                }
            }

            return new Creature(newMovements);
        }
    }
}
