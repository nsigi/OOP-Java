package functions.meta;
import functions.Function;

public class Shift implements Function {
    private Function func;
    private double cX;
    private double cY;

    public Shift(Function f, double X, double Y) {
        func = f;
        cX = X;
        cY = Y;
    }

    public double getLeftDomainBorder() {
        return func.getLeftDomainBorder() + cX;
    }

    public double getRightDomainBorder() {
        return func.getRightDomainBorder() + cX;
    }

    public double getFunctionValue(double x) {
        return cY + func.getFunctionValue(cX + x);
    }
}