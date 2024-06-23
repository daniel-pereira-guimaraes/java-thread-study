package com.example.example25;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {

    public static void main(String[] args) {
        var shared = new Shared();
        var random = new Random();

        new Thread(() -> {
            while (true) {
                shared.action1();
            }
        }).start();

        new Thread(() -> {
            while (true) {
                shared.action2();
                sleep(random.nextInt(10));
            }
        }).start();
    }

    private static class Shared {
        private ReentrantLock lock1 = new ReentrantLock();
        private ReentrantLock lock2 = new ReentrantLock();

        public void action1() {
            if (lock1.tryLock()) {
                try {
                    if (lock2.tryLock()) {
                        lock2.unlock();
                    } else {
                        System.out.println("cannot lock2 by " + Thread.currentThread().getName());
                    }
                } finally {
                    lock1.unlock();
                }
            }
        }

        public void action2() {
            if (lock2.tryLock()) {
                try {
                    if (lock1.tryLock()) {
                       lock1.unlock();
                    } else {
                        System.out.println("cannot lock1 by " + Thread.currentThread().getName());
                    }
                } finally {
                    lock2.unlock();
                }
            }
        }

    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}
