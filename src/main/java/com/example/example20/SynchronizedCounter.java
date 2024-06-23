package com.example.example20;

public class SynchronizedCounter {

    private final Counter counter = new Counter();

    public void inc() {
        printStage("inc", "starting");
        sleep();
        synchronized (counter) {
            printStage("inc", "incrementing");
            counter.inc();
            sleep();
            printStage("inc", "incremented");
        }
        printStage("inc", "finished");
    }

    public void dec() {
        printStage("dec", "starting");
        sleep();
        synchronized (counter) {
            printStage("dec", "decrementing");
            counter.dec();
            sleep();
            printStage("dec", "decremented");
        }
        printStage("dec", "finished");
    }

    public void otherMethod() {
        printStage("otherMethod", "run");
    }

    private void printStage(String method, String stage) {
        System.out.println(Thread.currentThread().getName() + " - " +
                getClass().getSimpleName() + "." + method + " - " + stage);
    }

    public void printCount() {
        synchronized (counter) {
            System.out.println("Count: " + counter.getCount());
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
