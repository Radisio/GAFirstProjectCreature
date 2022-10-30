package MathUtil;

import java.io.Serializable;
import java.util.Objects;

public class Position2D implements Serializable {
    public int x;
    public int y;

    public Position2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position2D that = (Position2D) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Position2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
