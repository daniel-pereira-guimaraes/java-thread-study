package com.example.example26;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class ProducerConsumerWithSemaphore {

    public static void main(String[] args) {
        var container = new Container();
        var empty = new Semaphore(1);
        var full = new Semaphore(0);
        var producer = new Producer(empty, full, container);
        var consumer = new Consumer(empty, full, container);

        producer.start();
        consumer.start();
    }

    protected static abstract class ProducerConsumer extends Thread {
        protected final Semaphore empty;
        protected final Semaphore full;
        protected final Container container;
        protected final Random random = new Random();

        protected ProducerConsumer(Semaphore empty, Semaphore full, Container container) {
            this.empty = empty;
            this.full = full;
            this.container = container;
        }

        protected void log(String message) {
            System.out.println(getClass().getSimpleName() + ": " + message);
        }

        protected void randomDelay() {
            try {
                Thread.sleep(random.nextInt(2000, 3000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    operate();
                } catch (InterruptedException e) {
                    log(e.getMessage());
                }
            }
        }

        protected abstract void operate() throws InterruptedException;
    }

    private static class Consumer extends ProducerConsumer {

        private Consumer(Semaphore empty, Semaphore full, Container container) {
            super(empty, full, container);
        }

        @Override
        public void operate() throws InterruptedException {
            log("Waiting...");
            full.acquire();
            log("Consuming...");
            randomDelay();
            var number = container.getNumber();
            log("Consumed number: " + number);
            empty.release();
        }
    }

    private static class Producer extends ProducerConsumer {

        private Producer(Semaphore empty, Semaphore full, Container container) {
            super(empty, full, container);
        }

        @Override
        public void operate() throws InterruptedException {
            log("Waiting...");
            empty.acquire();
            log("Producing...");
            randomDelay();
            var number = random.nextInt(1000);
            container.setNumber(number);
            log("Produced number: " + number);
            full.release();
        }
    }

    public static class Container {
        private int number;

        public void setNumber(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }
    }
}
