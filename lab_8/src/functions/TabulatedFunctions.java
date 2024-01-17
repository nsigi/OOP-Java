package functions;
import functions.basic.Exp;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TabulatedFunctions {
    private static TabulatedFunctionFactory functionFactory = new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();

    private TabulatedFunctions() {
    }

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) throws IllegalArgumentException, Exception {
        if (leftX < rightX && pointsCount > 1) {
            FunctionPoint[] arr = new FunctionPoint[pointsCount];
            double step = (rightX - leftX) / (arr.length - 1);
            for (int i = 0; leftX <= rightX; ++i, leftX += step) { //заполнение массива
                arr[i] = new FunctionPoint(leftX, function.getFunctionValue(leftX));
            }

            return functionFactory.createTabulatedFunction(arr);
        } else
            throw new IllegalArgumentException("Некорректный ввод границ или кол-во точек");
    }

    public static TabulatedFunction tabulate(Class<? extends TabulatedFunction> curClass, Function function, double leftX, double rightX, int pointsCount) throws IllegalArgumentException, Exception {
        if (leftX < rightX && pointsCount > 1) {
            FunctionPoint[] arr = new FunctionPoint[pointsCount];
            double step = (rightX - leftX) / (arr.length - 1);
            for (int i = 0; leftX <= rightX; ++i, leftX += step) { //заполнение массива
                arr[i] = new FunctionPoint(leftX, function.getFunctionValue(leftX));
            }

            return createTabulatedFunction(curClass, arr);
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
            return functionFactory.createTabulatedFunction(arr);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static TabulatedFunction inputTabulatedFunction(Class<? extends TabulatedFunction> curCLass, InputStream in) throws IOException, Exception {
        try (DataInputStream inStream = new DataInputStream(in)) {
            FunctionPoint[] arr = new FunctionPoint[inStream.readInt()];
            for (int i = 0; i < arr.length; ++i) {
                arr[i] = new FunctionPoint(inStream.readDouble(), inStream.readDouble());
            }
            return createTabulatedFunction(curCLass, arr);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
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

            return functionFactory.createTabulatedFunction(arr);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static TabulatedFunction readTabulatedFunction(Class<? extends TabulatedFunction> curCLass, Reader in) throws IOException, Exception {
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

            return createTabulatedFunction(curCLass, arr);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory funcFactory) {
        functionFactory = funcFactory;
    }

    public static TabulatedFunction createTabulatedFunction(Class<LinkedListTabulatedFunction> linkedListTabulatedFunctionClass, Exp exp, double leftX, double rightX, int pointsCount) {
        return functionFactory.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    public static TabulatedFunction createTabulatedFunction(Class<ArrayTabulatedFunction> arrayTabulatedFunctionClass, Exp exp, double leftX, double rightX, double[] values) {
        return functionFactory.createTabulatedFunction(leftX, rightX, values);
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
        return functionFactory.createTabulatedFunction(points);
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> curClass,
                                                            double leftX, double rightX, int pointsCount) {
        TabulatedFunction function;
        try {
            Constructor<? extends TabulatedFunction> curConstructor =
                    curClass.getConstructor(double.class, double.class, int.class);
            function = curConstructor.newInstance(leftX, rightX, pointsCount);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return function;
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> curClass,
                                                            double leftX, double rightX, double[] values) {
        TabulatedFunction function;
        try {
            Constructor<? extends TabulatedFunction> curConstructor =
                    curClass.getConstructor(double.class, double.class, double[].class);
            function = curConstructor.newInstance(leftX, rightX, values);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return function;
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> curClass, FunctionPoint[] points) {
        TabulatedFunction function;
        try {
            function = curClass.getConstructor(FunctionPoint[].class).newInstance((Object) points);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
        return function;
    }
}