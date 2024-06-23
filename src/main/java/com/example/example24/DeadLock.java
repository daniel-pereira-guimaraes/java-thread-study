package com.example.example24;

import java.util.Random;

public class DeadLock {

    public static void main(String[] args) {
        var shared = new Shared();

        new Thread(() -> {
            while (true) {
                randomDelay();
                shared.action1();
            }
        }).start();

        new Thread(() -> {
            while (true) {
                randomDelay();
                shared.action2();
            }
        }).start();
    }

    private static class Shared {

        private Object resource1 = new Object();
        private Object resource2 = new Object();

        public void action1() {
            synchronized (resource1) {
                System.out.println("resource1 locked by " + Thread.currentThread().getName());
                synchronized (resource2) {
                    System.out.println("resource2 locked by " + Thread.currentThread().getName());
                }
            }
        }

        public void action2() {
            synchronized (resource2) {
                System.out.println("resource2 locked by " + Thread.currentThread().getName());
                synchronized (resource1) {
                    System.out.println("resource1 locked by " + Thread.currentThread().getName());
                }
            }
        }
    }

    private static void randomDelay() {
        try {
            Thread.sleep(new Random().nextInt(20));
        } catch (InterruptedException ignored) {
        }
    }


}
