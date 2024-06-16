package com.example.example12;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        var threads = new ArrayList<FactorialThread>();
        List.of(2, 3, 4, 5, 10, 100, 10000, 500000, 2000000).forEach(n -> {
            var thread = new FactorialThread(n);
            thread.setDaemon(true);
            thread.start();
            threads.add(thread);
        });

        for (var thread : threads) {
            thread.join(1000);
            if (thread.isFinished()) {
                System.out.println(thread.input() + "! = " + thread.result());
            } else {
                System.out.println(thread.input() + "! was not calculated!");
            }
        }
    }
}
