package threads;

import functions.*;
import functions.basic.Log;

public class SimpleGenerator implements Runnable {
    private Task task;

    public SimpleGenerator(Task t) {
        task = t;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.getCountTasks(); ++i) {
            synchronized (task) {
                task.setFunction(new Log(Math.random() * 9 + 1));
                task.setLeftBorder(Math.random() * 100);
                task.setRightBorder(Math.random() * 100 + 100);
                task.setStep(Math.random());
                System.out.println("Source <" + task.getLeftBorder() + "> <" + task.getRightBorder() + "> <" + task.getStep() + ">");
            }
        }
    }
}