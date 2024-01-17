package functions.meta;
import functions.Function;

public class Power implements Function {
    private Function func;
    private double pow;

    public Power(Function f, double x) {
        func = f;
        pow = x;
    }

    public double getLeftDomainBorder() {
        return func.getLeftDomainBorder();
    }

    public double getRightDomainBorder() {
        return func.getRightDomainBorder();
    }

    public double getFunctionValue(double x) {
        return Math.pow(func.getFunctionValue(x), pow);
    }
}
