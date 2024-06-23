package com.example.exemple19;

public class SynchronizedObject {

    public synchronized void synchronizedMethod1() {
        System.out.println(getClass().getSimpleName() + ".synchronizedMethod1 - starting");
        sleep(2000);
        System.out.println(getClass().getSimpleName() + ".synchronizedMethod1 - finished");
    }

    public synchronized void synchronizedMethod2() {
        System.out.println(getClass().getSimpleName() + ".synchronizedMethod2 - starting");
        sleep(2000);
        System.out.println(getClass().getSimpleName() + ".synchronizedMethod2 - finished");
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
