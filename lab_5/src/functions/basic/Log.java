package functions.basic;
import functions.Function;

public class Log implements Function {
    private double base;

    public Log(double b) {
        base = b;
    }

    public double getLeftDomainBorder() {

        return 0.0;
    }

    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;

    }

    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder() || base == 1)//and base????????????????????????
            return Double.NaN;
        else
            return Math.log(x) / Math.log(base);
    }
}
