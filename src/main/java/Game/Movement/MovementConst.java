package Game.Movement;

import java.util.Arrays;

public enum MovementConst {
    UP(new byte[]{0,0,0}),
    UP_RIGHT(new byte[]{0,0,1}),
    RIGHT(new byte[]{0,1,0}),
    DOWN_RIGHT(new byte[]{0,1,1}),
    DOWN(new byte[]{1,0,0}),
    DOWN_LEFT(new byte[]{1,0,1}),
    LEFT(new byte[]{1,1,0}),
    UP_LEFT(new byte[]{1,1,1});

    public final byte[] bytes;
    MovementConst(byte[] b)
    {
        this.bytes = b;
    }

    public static MovementConst fromByteArray(byte[] b)
    {
        for(MovementConst movement : MovementConst.values())
        {
            if(Arrays.equals(movement.bytes, b))
                return movement;
        }
        return null;
    }


}
