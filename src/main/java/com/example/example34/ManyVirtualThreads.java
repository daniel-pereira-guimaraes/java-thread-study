package com.example.example34;

import java.util.ArrayList;

public class ManyVirtualThreads {
    private static final int NUMBER_OF_VIRTUAL_THREADS = 1000;

    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println(Thread.currentThread());

        var threads = new ArrayList<Thread>();
        for (int i = 0; i < NUMBER_OF_VIRTUAL_THREADS; i++) {
            threads.add(Thread.startVirtualThread(runnable));
        }

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
