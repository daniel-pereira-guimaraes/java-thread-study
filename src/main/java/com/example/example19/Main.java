package com.example.example19;

public class Main {

    public static void main(String[] args) {
        var synchronizedObject = new SynchronizedMethods();
        new Thread(synchronizedObject::synchronizedMethod1).start();
        new Thread(synchronizedObject::synchronizedMethod2).start();
    }

}
