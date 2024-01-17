import functions.*;
import functions.basic.*;
import functions.meta.*;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            /*
            Cos fCos1 = new Cos();
            Sin fSin1 = new Sin();

            for(double i = 0.0; i <= 2 * Math.PI; i += 0.1) {
                System.out.print(fCos1.getFunctionValue(i));
                System.out.print("     ");
                System.out.print(fSin1.getFunctionValue(i));
                System.out.println();
            }

            TabulatedFunction fCos2 = TabulatedFunctions.tabulate(fCos1, 0, 2 * Math.PI, 10);
            TabulatedFunction fSin2 = TabulatedFunctions.tabulate(fSin1, 0, 2 * Math.PI, 10);
            System.out.println();
            Sum nF = new Sum(new Power(fSin2, 2), new Power(fCos2, 2));
            TabulatedFunctions.tabulate(nF, 0, 2 * Math.PI, 10).printCont();*/


            /*TabulatedFunction fExp = TabulatedFunctions.tabulate(new Exp(), 0, 10, 11);
            fExp.printCont();
            TabulatedFunctions.writeTabulatedFunction(fExp, new FileWriter("write.txt"));
            fExp = TabulatedFunctions.readTabulatedFunction(new FileReader("write.txt"));
            fExp.printCont();*/


            /*TabulatedFunction fLog = TabulatedFunctions.tabulate(new Log(2), 0, 10, 11);
            fLog.printCont();
            TabulatedFunctions.outputTabulatedFunction(fLog, new FileOutputStream("output.txt"));
            fLog = TabulatedFunctions.inputTabulatedFunction(new FileInputStream("output.txt"));
            fLog.printCont();*/


            /*TabulatedFunction fLog = TabulatedFunctions.tabulate(new Log(Math.E), 0, 10, 11);
            fLog.printCont();
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("serOutput.txt"));
            out.writeObject(fLog);
            out.close();

            ObjectInputStream in = new ObjectInputStream(new FileInputStream("serOutput.txt"));
            ArrayTabulatedFunction listF = ((ArrayTabulatedFunction) in.readObject());
            listF.printCont();
            in.close();*/


            TabulatedFunction fLog = TabulatedFunctions.tabulate(new Log(Math.E), 0, 10, 11);
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("externalOutput.txt"));
            fLog.printCont();
            out.writeObject(fLog);
            out.close();

            ObjectInputStream in = new ObjectInputStream(new FileInputStream("externalOutput.txt"));
            ArrayTabulatedFunction listF = ((ArrayTabulatedFunction) in.readObject());
            listF.printCont();
            in.close();
        } catch (FunctionPointIndexOutOfBoundsException | IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
