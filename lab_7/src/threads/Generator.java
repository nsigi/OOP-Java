package threads;

import functions.basic.Log;

public class Generator extends Thread {
    private Task task;
    private Semaphore semaphore;

    public Generator(Task t, Semaphore s) {
        task = t;
        semaphore = s;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < task.getCountTasks(); ++i) {
                semaphore.beginWrite();
                task.setFunction(new Log(Math.random() * 9 + 1));
                task.setLeftBorder(Math.random() * 100);
                task.setRightBorder(Math.random() * 100 + 100);
                task.setStep(Math.random());
                System.out.println("Source <" + task.getLeftBorder() + "> <" + task.getRightBorder() + "> <" + task.getStep() + ">");
                semaphore.endWrite();
            }
        } catch (InterruptedException e) {
            System.out.println("Поток прерван");

        }
    }
}
