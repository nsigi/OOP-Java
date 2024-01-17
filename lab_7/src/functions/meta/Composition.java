package functions.meta;
import functions.Function;

public class Composition implements Function {
    private Function func1;
    private Function func2;

    public Composition(Function f1, Function f2) {
        func1 = f1;
        func2 = f2;
    }

    public double getLeftDomainBorder() {
        return func1.getLeftDomainBorder();
    }

    public double getRightDomainBorder() {
        return func1.getRightDomainBorder();
    }

    public double getFunctionValue(double x) {
        return func1.getFunctionValue(func2.getFunctionValue(x));
    }
}