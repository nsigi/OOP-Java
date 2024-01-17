package functions.basic;
import functions.Function;

public class Exp implements Function {
    public double getLeftDomainBorder() {

        return Double.NEGATIVE_INFINITY;
    }

    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;

    }

    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder())
            return Double.NaN;
        else
            return Math.exp(x);
    }
}
