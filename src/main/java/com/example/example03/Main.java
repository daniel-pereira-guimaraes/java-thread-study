package com.example.example03;

public class Main {

    public static void main(String[] args) {

        var thread = new Thread(() -> {
            throw new RuntimeException("Any exception!");
        });

        thread.setUncaughtExceptionHandler((t, e) ->
                System.out.println(t.getName() + ": " + e.getMessage())
        );

        thread.start();
    }

}
