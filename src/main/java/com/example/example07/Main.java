package com.example.example07;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        var blockingThread = new Thread(blockingTask());
        blockingThread.setName("BlockingThread");
        blockingThread.start();

        Thread.currentThread().setName("MainThread");
        System.out.println(threadName() + " waiting...");
        Thread.sleep(5000);
        System.out.println(threadName() + " interrupting " + blockingThread.getName() + "...");
        blockingThread.interrupt();
        System.out.println(threadName() + " finishing...");
    }

    private static Runnable blockingTask() {
        return () -> {
            try {
                System.out.println(threadName() + " running...");
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                System.out.println(threadName() + " interrupted!");
            }
        };
    }

    private static String threadName() {
        return Thread.currentThread().getName();
    }
}
