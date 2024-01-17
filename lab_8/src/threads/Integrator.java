package threads;

import functions.Functions;

public class Integrator extends Thread {
    private Task task;
    private Semaphore semaphore;

    public Integrator(Task t, Semaphore s) {
        task = t;
        semaphore = s;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < task.getCountTasks(); ++i) {
                semaphore.beginRead();
                System.out.println((i + 1) + " Result <" + task.getLeftBorder() + "> <" + task.getRightBorder() + "> <" + task.getStep() + "> <" + Functions.integrate(task.getFunction(), task.getLeftBorder(), task.getRightBorder(), task.getStep()) + ">");
                semaphore.endRead();
            }
        } catch (InterruptedException e) {
            System.out.println("Поток прерван");
        }
    }
}
