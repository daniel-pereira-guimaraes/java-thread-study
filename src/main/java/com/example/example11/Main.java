package com.example.example11;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        var thread = new Thread(() -> {
            try {
                System.out.println("Secondary thread running...");
                Thread.sleep(2000);
                System.out.println("Secondary thread finishing...");
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        });

        thread.start();

        System.out.println("Main thread waiting...");
        thread.join();
        System.out.println("Main thread finishing...");
    }
}
