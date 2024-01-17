package threads;

import functions.Functions;

public class SimpleIntegrator implements Runnable {
    private Task task;

    public SimpleIntegrator(Task t) {
        task = t;
    }

    @Override
    public void run() {
        try {
            Thread.currentThread().sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < task.getCountTasks(); ++i) {
            if (task.getFunction() == null) continue;
            synchronized (task) {
                System.out.println((i + 1) + " Result <" + task.getLeftBorder() + "> <" + task.getRightBorder() + "> <" + task.getStep() + "> <" + Functions.integrate(task.getFunction(), task.getLeftBorder(), task.getRightBorder(), task.getStep()) + ">");
            }
        }
    }
}
