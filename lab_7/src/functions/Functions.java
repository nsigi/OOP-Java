package functions;
import functions.meta.*;

public class Functions {
    private  Functions(){}

    public static Function shift(Function f, double shiftX, double shiftY) {
        return new Shift(f, shiftX, shiftY);
    }

    public static Function scale(Function f, double scaleX, double scaleY) {
        return new Scales(f, scaleX, scaleY);
    }

    public static Function power(Function f, double power) {
        return new Power(f, power);
    }

    public static Function sum(Function f1, Function f2) {
        return new Sum(f1, f2);
    }

    public static Function mult(Function f1, Function f2) {
        return new Mult(f1, f2);
    }

    public static Function composition(Function f1, Function f2) {
        return new Composition(f1, f2);
    }

    public static double integrate(Function function, double leftBorder, double rightBorder, double dx) {
        int step = (int) Math.ceil((rightBorder - leftBorder) / dx);
        double result = 0;
        double x = leftBorder;
        for (double i = 0; i < step - 1; ++i, x += dx) {
            result += dx * (function.getFunctionValue(x) + function.getFunctionValue(x + dx)) / 2;
        }
        result += dx * (function.getFunctionValue(x) + function.getFunctionValue(rightBorder)) / 2;
        return result;
    }
}
