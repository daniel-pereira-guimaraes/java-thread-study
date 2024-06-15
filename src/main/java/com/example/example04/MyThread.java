package com.example.example04;

public class MyThread extends Thread {

    public MyThread() {
        super("My Thread");
        setPriority(Thread.MAX_PRIORITY);
    }

    @Override
    public void run() {
        System.out.println(getName());
    }
}
