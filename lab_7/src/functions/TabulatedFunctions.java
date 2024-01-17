package functions;
import java.io.*;

public class TabulatedFunctions {
    private TabulatedFunctions() {
    }

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) throws IllegalArgumentException, Exception {
        if (leftX < rightX && pointsCount > 1) {
            FunctionPoint[] arr = new FunctionPoint[pointsCount];
            double step = (rightX - leftX) / (arr.length - 1);
            for (int i = 0; leftX <= rightX; ++i, leftX += step) { //заполнение массива
                arr[i] = new FunctionPoint(leftX, function.getFunctionValue(leftX));
            }
            //return new LinkedListTabulatedFunction(arr);
            return new ArrayTabulatedFunction(arr);
        } else
            throw new IllegalArgumentException("Некорректный ввод границ или кол-во точек");
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {
        try (DataOutputStream sout = new DataOutputStream(out)) {
            sout.writeInt(function.getPointsCount());
            for (int i = 0; i < function.getPointsCount(); ++i) {
                sout.writeDouble(function.getPointX(i));
                sout.writeDouble(function.getPointY(i));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException, Exception {
        try (DataInputStream inStream = new DataInputStream(in)) {
            FunctionPoint[] arr = new FunctionPoint[inStream.readInt()];
            for (int i = 0; i < arr.length; ++i) {
                arr[i] = new FunctionPoint(inStream.readDouble(), inStream.readDouble());
            }
            return new ArrayTabulatedFunction(arr);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InappropriateFunctionPointException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException {
        try (BufferedWriter printOut = new BufferedWriter(out)) {
            printOut.write(function.getPointsCount() + " ");
            for (int i = 0; i < function.getPointsCount(); ++i)
                printOut.write(function.getPointX(i) + " " + function.getPointY(i) + " ");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException, Exception {
        try {
            StreamTokenizer sToken = new StreamTokenizer(in);
            double x, y;
            sToken.nextToken();
            FunctionPoint[] arr = new FunctionPoint[(int) sToken.nval];
            for (int i = 0; sToken.nextToken() != sToken.TT_EOF; ++i) {
                x = sToken.nval;
                sToken.nextToken();
                y = sToken.nval;
                arr[i] = new FunctionPoint(x, y);
            }

            return new ArrayTabulatedFunction(arr);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
