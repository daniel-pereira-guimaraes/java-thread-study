package com.example.example21;

public class StaticSynchronizedMethods {

    public static synchronized void staticSynchronizedMethod1() {
        printStage("staticSynchronizedMethod1", "starting");
        sleep();
        printStage("staticSynchronizedMethod1", "finished");
    }

    public static synchronized void staticSynchronizedMethod2() {
        printStage("staticSynchronizedMethod2", "starting");
        sleep();
        printStage("staticSynchronizedMethod2", "finished");
    }

    private static void printStage(String method, String stage) {
        System.out.println(Thread.currentThread().getName()
                + " - " + method + " - " + stage);
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
