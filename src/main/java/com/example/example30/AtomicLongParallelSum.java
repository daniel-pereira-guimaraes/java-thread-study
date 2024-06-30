package com.example.example30;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongParallelSum {

    public static void main(String[] args) throws InterruptedException {
        var sum = new AtomicLong(0);

        var thread1 = new Thread(() -> {
            for (int i = 1; i <= 5000; i++) {
                sum.addAndGet(i);
            }
        });

        var thread2 = new Thread(() -> {
           for (int i = 5001; i <= 10000; i++) {
               sum.addAndGet(i);
           }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        var expectedSum = (1 + 10000) * 10000 / 2; // Arithmetic Progression sum!
        System.out.println("expected sum: " + expectedSum);
        System.out.println("calculated sum: " + sum.get());
    }
}
