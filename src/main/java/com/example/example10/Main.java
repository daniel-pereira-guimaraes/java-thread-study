package com.example.example10;

public class Main {

    public static void main(String[] args) {

        var task = new WaitForUserInput('q');
        var thread = new Thread(task);

        thread.start();
    }
}
