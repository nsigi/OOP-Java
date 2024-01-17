package functions;

import java.io.Externalizable;
import java.io.Serializable;

public class FunctionPoint implements Serializable {
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

    double get_X() {
        return x;
    }

    double get_Y() {
        return y;
    }

    void set_X(double val) {
        x = val;
    }

    void set_Y(double val) {
        y = val;
    }
}
