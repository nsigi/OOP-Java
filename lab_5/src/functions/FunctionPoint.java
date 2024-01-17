package functions;

import java.io.Externalizable;
import java.io.Serializable;

public class FunctionPoint implements Serializable, Cloneable {
    private double x, y;

    public FunctionPoint(double X, double Y) {
        x = X;
        y = Y;
    }

    public FunctionPoint(FunctionPoint point) {
        x = point.x;
        y = point.y;
    }

    public FunctionPoint() {
        x = y = 0;
    }

    public double get_X() {
        return x;
    }

    public double get_Y() {
        return y;
    }

    public void set_X(double val) {
        x = val;
    }

    public void set_Y(double val) {
        y = val;
    }

    public String toString() {
        return "(" + x + "; " + y + ")";
    }

    public boolean equals(Object o) {
        return (o instanceof FunctionPoint) && x == ((FunctionPoint) o).get_X() && y == ((FunctionPoint) o).get_Y();
    }

    public int hashCode() {
        //return 11 * Double.hashCode(x) + 13 * Double.hashCode(y);
        long dX = Double.doubleToLongBits(x);
        long dY = Double.doubleToLongBits(y);
        int result = 1;
        result = result * 31 + (int)(dX ^ (dX >>> 32));
        result = result * 31 + (int)(dY ^ (dY >>> 32));

        return result;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
