package com.example.example29;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerExample {

    public static void main(String[] args) throws InterruptedException {
        var atomicInteger = new AtomicInteger(0);

        var incrementThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                var value = atomicInteger.incrementAndGet();
                System.out.println(Thread.currentThread().getName() + ": " + value);
            }
        });

        var decrementThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                var value = atomicInteger.decrementAndGet();
                System.out.println(Thread.currentThread().getName() + ": " + value);
            }
        });

        incrementThread.start();
        decrementThread.start();

        incrementThread.join();
        decrementThread.join();

        System.out.println("Final value: " + atomicInteger.get());
    }
}
