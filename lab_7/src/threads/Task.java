package threads;

import functions.Function;

public class Task {
    private Function function;
    private double leftBorder;
    private double rightBorder;
    private double step;
    private int countTasks;

    public Task() {
    }

    public Task(Function func, double leftB, double rightB, double st, int count) {
        function = func;
        leftBorder = leftB;
        rightBorder = rightB;
        step = st;
        countTasks = count;
    }

    public double getLeftBorder() {
        return leftBorder;
    }

    public double getRightBorder() {
        return rightBorder;
    }

    public double getStep() {
        return step;
    }

    public Function getFunction() {
        return function;
    }

    public int getCountTasks() {
        return countTasks;
    }

    public void setCountTasks(int count) {
        countTasks = count;
    }

    public void setFunction(Function func) {
        function = func;
    }

    public void setLeftBorder(double leftB) {
        leftBorder = leftB;
    }

    public void setRightBorder(double rightB) {
        rightBorder = rightB;
    }

    public void setStep(double s) {
        step = s;
    }
}
