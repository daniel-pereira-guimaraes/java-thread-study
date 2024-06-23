package com.example.example24;

public class DeadLock {

    public static void main(String[] args) {
        var shared = new Shared();

        new Thread(() -> {
            while (true) {
                shared.action1();
            }
        }).start();

        new Thread(() -> {
            while (true) {
                shared.action2();
            }
        }).start();
    }

    private static class Shared {

        private Object lock1 = new Object();
        private Object lock2 = new Object();

        public void action1() {
            System.out.println("action1 locking lock1");
            synchronized (lock1) {
                System.out.println("action1 locking lock2");
                synchronized (lock2) {
                    System.out.println("Running action1");
                }
            }
        }

        public void action2() {
            System.out.println("action2 locking lock2");
            synchronized (lock2) {
                System.out.println("action2 locking lock1");
                synchronized (lock1) {
                    System.out.println("Running action2");
                }
            }
        }

    }

}
