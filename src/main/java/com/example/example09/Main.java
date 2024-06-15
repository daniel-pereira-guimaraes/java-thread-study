package com.example.example09;

import java.math.BigInteger;

public class Main {
    private static final int TIMEOUT = 5000;

    public static void main(String[] args) throws InterruptedException {
        var base = BigInteger.valueOf(2);
        var power = BigInteger.valueOf(1000000);
        var task = new LongComputationTask(base, power);
        var thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

        System.out.println("Computing...");
        waitFor(thread, TIMEOUT);
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
