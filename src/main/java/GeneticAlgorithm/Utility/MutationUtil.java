package GeneticAlgorithm.Utility;

import Game.Creature.Creature;
import Game.Game;
import Game.Movement.MovementConst;
import Game.Movement.MovementUtil;

import java.util.ArrayList;
import java.util.List;

public class MutationUtil {

    private static byte flip(byte b)
    {
        return b==1 ? (byte)0:(byte)1;
    }

    public static Creature mutate(Game game, double flipRate, double mutationAddRate, double mutationSubRate)
    {
        List<Byte> bytes = MovementUtil.movementListToByteList(game.getCreature().getMovements());
        int size = bytes.size();
        for(int i = 0;i<size;i++)
        {
            if(Math.random()<= flipRate)
            {
                byte gene = flip(bytes.get(i));
                bytes.set(i,gene);
            }
        }
        List<MovementConst> movements = new ArrayList<>(MovementUtil.byteListToMovementList(bytes));
        if(Math.random()<=mutationAddRate)
        {
            movements.add(MovementUtil.generateRandomMovement(1).get(0));

        }
        if(Math.random()<=mutationSubRate)
        {
            //System.out.println("SUB MUTATION !!!");
            if(movements.size()>0)
                movements.remove((int)(Math.random() * (movements.size()-1)));

        }
        return new Creature(movements);
    }
}
