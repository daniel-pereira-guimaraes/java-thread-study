package com.example.example06;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
        new MultiExecutor(getTasks()).executeAll();
    }

    private static List<Runnable> getTasks() {
        return List.of(
                printNumbers(),
                printLowerLetters(),
                printUpperLetters()
        );
    }

    private static Runnable printNumbers() {
        return () -> printItems(i -> i);
    }

    private static Runnable printLowerLetters() {
        return () -> printItems(i -> String.valueOf((char) (97 + i % 26)));
    }

    private static Runnable printUpperLetters() {
        return () -> printItems(i -> String.valueOf((char) (65 + i % 26)));
    }

    private static void printItems(Function<Integer,Object> get) {
        for (int i = 0; i < 10; i++) {
            System.out.print(get.apply(i));
            randomDelay();
        }
    }

    private static void randomDelay() {
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException ignore) {
        }
    }
}
