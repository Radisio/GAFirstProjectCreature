package Game.Creature;

import Game.Movement.MovementConst;

import java.util.ArrayList;
import java.util.List;

public class Creature {
    private List<MovementConst> movements;

    public Creature(List<MovementConst> movements) {
        this.movements = movements;
    }


    public List<MovementConst> getMovements() {
        return movements;
    }

    public void setMovements(List<MovementConst> movements) {
        this.movements = movements;
    }

    @Override
    public String toString() {
        return "Creature{" +
                "movements=" + movements +
                '}';
    }
}
