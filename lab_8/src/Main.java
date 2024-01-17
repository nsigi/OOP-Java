import functions.*;
import functions.basic.*;
import functions.meta.*;
import gui.FunctionParametersDialog;
import threads.*;

import java.io.*;
import java.util.Random;

public class Main {
    public static void nonThread() {
        Task task = new Task();
        task.setCountTasks(100);
        for (int i = 0; i < task.getCountTasks(); ++i) {
            task.setFunction(new Log(Math.random() * 9 + 1));
            task.setLeftBorder(Math.random() * 100);
            task.setRightBorder(Math.random() * 100 + 100);
            task.setStep(Math.random());
            System.out.println("Source <" + task.getLeftBorder() + "> <" + task.getRightBorder() + "> <" + task.getStep() + ">");
            System.out.println("Result <" + task.getLeftBorder() + "> <" + task.getRightBorder() + "> <" + task.getStep() + "> <" + Functions.integrate(task.getFunction(), task.getLeftBorder(), task.getRightBorder(), task.getStep()) + ">");
        }
    }

    public static void simpleThreads() {
        Task task = new Task();
        task.setCountTasks(100);
        Thread threadG = new Thread(new SimpleGenerator(task));
        Thread threadI = new Thread(new SimpleIntegrator(task));
        threadG.setPriority(Thread.MIN_PRIORITY);
        threadI.setPriority(Thread.MAX_PRIORITY);
        threadG.start();
        threadI.start();
    }

    public static void complicatedThreads() {
        Task task = new Task();
        task.setCountTasks(5);
        Semaphore semaphore = new Semaphore();
        Generator generator = new Generator(task, semaphore);
        Integrator integrator = new Integrator(task, semaphore);
        generator.setPriority(Thread.MAX_PRIORITY);
        integrator.setPriority(Thread.NORM_PRIORITY);
        generator.start();
        integrator.start();
        try {
            Thread.currentThread().sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        generator.interrupt();
        integrator.interrupt();
    }

    public static void main(String[] args) {
        try {
            TabulatedFunction f;
            TabulatedFunctions.setTabulatedFunctionFactory(new
                    ArrayTabulatedFunction.ArrayTabulatedFunctionFactory());
            f = TabulatedFunctions.tabulate(new Exp(), 1, 10, 10);
            System.out.println(f.getClass());
            System.out.println(f);

            TabulatedFunctions.setTabulatedFunctionFactory(new
                    LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());
            f = TabulatedFunctions.tabulate(new Exp(), 1, 10, 10);
            System.out.println(f.getClass());
            System.out.println(f);

        } catch (FunctionPointIndexOutOfBoundsException | IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}