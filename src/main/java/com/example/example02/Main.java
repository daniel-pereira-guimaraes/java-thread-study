package com.example.example02;

public class Main {

    public static void main(String[] args) {

        var thread2 = new Thread(Main::processing, "Second Thread");
        var thread3 = new Thread(Main::processing, "Third Thread");

        thread2.setPriority(Thread.MIN_PRIORITY);
        thread3.setPriority(Thread.MAX_PRIORITY);

        thread2.start();
        thread3.start();
        processing();
    }

    private static void processing() {
        for (int i = 0; i < 5; i++) {
            System.out.println(currentThreadDescription());
        }
    }

    private static String currentThreadDescription() {
        return Thread.currentThread().getName()
                + ", priority = " + Thread.currentThread().getPriority();
    }

}
