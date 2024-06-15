package com.example.example05;

import java.util.List;
import java.util.Random;

public class Main {
    public static final int MAX_PASSWORD = 9999;

    public static void main(String[] args) {

        var vault = new Vault(new Random().nextInt(MAX_PASSWORD));
        var threads = List.of(
                new AscendingHackerThread(vault),
                new DescendingHackerThread(vault),
                new PoliceThread()
        );

        threads.forEach(Thread::start);
    }

}
