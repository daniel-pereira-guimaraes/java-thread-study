package com.example.example21;

public class Main {

    public static void main(String[] args) {
        new Thread(StaticSynchronizedMethods::staticSynchronizedMethod1).start();
        new Thread(StaticSynchronizedMethods::staticSynchronizedMethod2).start();
    }
}
