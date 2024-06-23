package com.example.example23;

public class Main {

    public static void main(String[] args) {
        var unsafe = new UnsafeDataRace();
        var safe = new SafeDataRace();

        new Thread(() -> {
            while (true) {
                unsafe.increment();
                safe.increment();
            }
        }).start();

        new Thread(() -> {
            while (true) {
                unsafe.checkForDataRace();
                safe.checkForDataRace();
            }
        }).start();

    }

    private static abstract class DataRace {

        public void checkForDataRace() {
            if (isDataRaceDetected()) {
                System.out.println(getClass().getSimpleName() +  ": y > x - Data Race is detected!");
            }
        }

        protected abstract boolean isDataRaceDetected();
    }

    private static final class UnsafeDataRace extends DataRace {
        private int x = 0;
        private int y = 0;

        public void increment() {
            x++;
            y++;
        }

        @Override
        protected boolean isDataRaceDetected() {
            return y > x;
        }
    }

    private static final class SafeDataRace extends DataRace {
        private volatile int x = 0; // volatile!!!
        private volatile int y = 0;

        public void increment() {
            x++;
            y++;
        }

        @Override
        protected boolean isDataRaceDetected() {
            return y > x;
        }
    }

}
