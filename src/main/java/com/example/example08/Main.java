package com.example.example08;

import java.math.BigInteger;

public class Main {
    private static final int TIMEOUT = 10000;

    public static void main(String[] args) throws InterruptedException {
        var base = BigInteger.valueOf(2);
        var power = BigInteger.valueOf(1000000);
        var task = new LongComputationTask(base, power);
        var thread = new Thread(task);
        thread.start();

        System.out.println("Computing...");
        waitFor(thread, TIMEOUT);
        tryInterrupt(thread);
    }

    private static void tryInterrupt(Thread thread) {
        if (thread.isAlive()) {
            System.out.println("Interrupting " + thread.getName() + " ...");
            thread.interrupt();
        }
    }

    private static void waitFor(Thread thread, long timeout) throws InterruptedException {
        int step = 1000;
        while (timeout > 0 && thread.isAlive()) {
            System.out.println(timeout / step);
            Thread.sleep(step);
            timeout -= step;
       }
    }
}
