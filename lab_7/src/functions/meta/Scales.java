package functions.meta;
import functions.Function;

public class Scales implements Function {
    private Function func;
    private double cX;
    private double cY;
    private boolean chReverseX;

    public Scales(Function f, double X, double Y) {
        func = f;
        cX = X;
        cY = Y;
        chReverseX = cX < 0 ? true : false;
    }

    public double getLeftDomainBorder() {
        if (chReverseX)
            return func.getRightDomainBorder() / cX;
        return func.getLeftDomainBorder() / cX;
    }

    public double getRightDomainBorder() {
        if (chReverseX)
            return func.getLeftDomainBorder() / cX;
        return func.getRightDomainBorder() / cX;
    }

    public double getFunctionValue(double x) {
        return cY * func.getFunctionValue(cX * x);
    }
}
