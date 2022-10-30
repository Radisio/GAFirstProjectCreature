package GeneticAlgorithm.Utility;

import Game.Creature.Creature;
import Game.Environment.Environment;
import Game.Game;
import Game.Movement.MovementConst;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class CrossOverUtil {

    public static Creature crossOver(Creature creature1, Creature creature2, double rate)
    {
        List<MovementConst> movements = new ArrayList<>();
        int size = creature1.getMovements().size();
        for(int i = 0; i<size;i++)
        {
            if(Math.random()<=rate)
                movements.add(creature1.getMovements().get(i));
            else
                movements.add(creature2.getMovements().get(i));
        }
        return new Creature(movements);
    }

    public static Creature crossOverKeepFromBest(Game game1, Game game2, double rate)
    {
        if(game1.getCreature().getMovements().size() == game2.getCreature().getMovements().size())
            return crossOver(game1.getCreature(), game2.getCreature(), rate);

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

    public static Creature crossOverCaseByCase(Game game1, Game game2, double rate)
    {
        if(game1.getCreature().getMovements().size() == game2.getCreature().getMovements().size())
        {
            return crossOver(game1.getCreature(), game2.getCreature(), rate);
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
