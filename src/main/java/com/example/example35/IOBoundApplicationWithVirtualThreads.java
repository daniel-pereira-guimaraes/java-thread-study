package com.example.example35;

import java.util.concurrent.Executors;

public class IOBoundApplicationWithVirtualThreads {
    private static final int NUMBER_OF_TASKS = 100000;

    public static void main(String[] args) {
        System.out.printf("Running %d tasks\n", NUMBER_OF_TASKS);
        long start = System.currentTimeMillis();
        performTasks();
        System.out.printf("Tasks took %dms to complete\n", System.currentTimeMillis() - start);
    }

    private static void performTasks() {
        try (var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < NUMBER_OF_TASKS; i++) {
                executorService.submit(IOBoundApplicationWithVirtualThreads::simulateLongBlockingIO);
            }
        }
    }

    private static void simulateLongBlockingIO() {
        System.out.println("Executing a blocking task from thread: " + Thread.currentThread());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
