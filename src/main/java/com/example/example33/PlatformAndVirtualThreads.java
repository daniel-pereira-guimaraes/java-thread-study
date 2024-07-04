package com.example.example33;

import java.util.List;

public class PlatformAndVirtualThreads {

    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println("Running... " + Thread.currentThread());

        var allThreads = List.of(
            new Thread(runnable),
            Thread.ofPlatform().unstarted(runnable),
            Thread.ofPlatform().start(runnable),
            Thread.ofVirtual().unstarted(runnable),
            Thread.ofVirtual().start(runnable),
            Thread.startVirtualThread(runnable)
        );

        allThreads.forEach(thread -> {
            if (thread.getState() == Thread.State.NEW) {
                System.out.println("Starting... " + thread);
                thread.start();
            }
        });

        allThreads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
