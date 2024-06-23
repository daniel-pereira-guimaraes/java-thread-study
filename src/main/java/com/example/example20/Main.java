package com.example.example20;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        var counter = new SynchronizedCounter();
        var incThread = new Thread(counter::inc);
        var decThread = new Thread(counter::dec);
        var otherThread = new Thread(counter::otherMethod);

        incThread.setName("incThread");
        decThread.setName("decThread");
        otherThread.setName("otherThread");

        incThread.start();
        decThread.start();
        otherThread.start();

        incThread.join();
        decThread.join();
        otherThread.join();

        counter.printCount();
    }
}
