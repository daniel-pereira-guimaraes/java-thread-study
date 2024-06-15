package com.example.example01;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        var runnable = new Runnable() {
            @Override
            public void run() {
                slowProcessing();
            }
        };

        var thread = new Thread(runnable);

        thread.setName("Second Thread");
        Thread.currentThread().setName("Main Thread");

        thread.start();
        slowProcessing();
    }

    private static void slowProcessing() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName());
            randomDelay();
        }
    }

    private static void randomDelay() {
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
