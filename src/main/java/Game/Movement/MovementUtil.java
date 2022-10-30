package Game.Movement;

import Game.Creature.Creature;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class MovementUtil {

    private static List<MovementConst> allMovements = new ArrayList<>(){
        {
            add(MovementConst.UP);
            add(MovementConst.UP_RIGHT);
            add(MovementConst.RIGHT);
            add(MovementConst.DOWN_RIGHT);
            add(MovementConst.DOWN);
            add(MovementConst.DOWN_LEFT);
            add(MovementConst.LEFT);
            add(MovementConst.UP_LEFT);
            add(MovementConst.LEFT);
            add(MovementConst.UP_LEFT);
        }
    };


    public static List<MovementConst> generateRandomMovement(int size)
    {
        List<MovementConst> movements = new ArrayList<>();
        for(int i =0;i<size;i++)
        {
            movements.add(allMovements.get((int)(Math.random()*10)));
        }
        return movements;
    }

    public static List<Byte> movementListToByteList(List<MovementConst> movements)
    {
        List<Byte> bytes = new ArrayList<>();
        for(int i = 0;i< movements.size();i++)
        {
            for(int j = 0;j<3;j++)
                bytes.add(movements.get(i).bytes[j]);
        }
        return bytes;
    }



    public static List<MovementConst> byteListToMovementList(List<Byte> bMovements)
    {
        List<MovementConst> returnedList = new ArrayList<>();
        List<Byte> tmp;
        for(int i = 0;i<bMovements.size();i++)
        {
            tmp = new ArrayList<>();
            for(int j =0; j<3 && i<bMovements.size();j++,i++)
            {
                tmp.add(bMovements.get(i));
            }
            i--;
            if(tmp.size()==3)
            {
                returnedList.add(MovementConst.fromByteArray(ArrayUtils.toPrimitive(tmp.toArray(new Byte[3]))));
            }
            else System.out.println("COMMENT EST CE POSSIBLE ? tmp size = " + tmp.size());
        }
        return returnedList;
    }
}
