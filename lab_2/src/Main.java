import functions.*;

public class Main {
    public static void main(String[] args) {
        TabulatedFunction tabulatedFunction = new TabulatedFunction(1, 5, new double[]{1, 4, 9, 16, 25});

        tabulatedFunction.printArr();

        System.out.println(tabulatedFunction.getFunctionValue(1));

        tabulatedFunction.addPoint(new FunctionPoint(1.5, 9));

        tabulatedFunction.printArr();
    }
}
