package functions.meta;
import functions.Function;

public class Sum implements Function {
    private Function func1;
    private Function func2;

    public Sum(Function f1, Function f2) {
        func1 = f1;
        func2 = f2;
    }

    public double getLeftDomainBorder() {
        if (func1.getLeftDomainBorder() < func2.getLeftDomainBorder())
            return func2.getLeftDomainBorder();
        else
            return func1.getLeftDomainBorder();
    }

    public double getRightDomainBorder() {
        if (func1.getRightDomainBorder() > func2.getRightDomainBorder())
            return func2.getRightDomainBorder();
        else
            return func1.getRightDomainBorder();
    }

    public double getFunctionValue(double x) {
        return func1.getFunctionValue(x) + func2.getFunctionValue(x);
    }
}
