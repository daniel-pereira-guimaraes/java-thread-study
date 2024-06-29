package com.example.example27;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class QueueProducerConsumerWithSemaphore {

    public static void main(String[] args) {
        var empty = new Semaphore(5);
        var full = new Semaphore(0);
        var queue = new LinkedList<Integer>();
        var producer = new Producer(empty, full, queue);
        var consumer = new Consumer(empty, full, queue);

        producer.start();
        consumer.start();
    }

    protected static abstract class ProducerConsumer extends Thread {
        protected final Semaphore empty;
        protected final Semaphore full;
        protected final Queue<Integer> queue;
        protected final Random random = new Random();

        protected ProducerConsumer(Semaphore empty, Semaphore full, Queue<Integer> queue) {
            this.empty = empty;
            this.full = full;
            this.queue = queue;
        }

        protected void log(String message) {
            System.out.println(getClass().getSimpleName() + ": " + message);
        }

        protected void randomDelay() {
            try {
                Thread.sleep(random.nextInt(0, 3000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    operate();
                    log("Queue: " + queue);
                } catch (InterruptedException e) {
                    log(e.getMessage());
                }
            }
        }

        protected abstract void operate() throws InterruptedException;
    }

    private static class Consumer extends ProducerConsumer {

        private Consumer(Semaphore empty, Semaphore full, Queue<Integer> queue) {
            super(empty, full, queue);
        }

        @Override
        public void operate() throws InterruptedException {
            waitForAvailability();
            consume();
            empty.release();
        }

        private void consume() {
            log("Consuming...");
            randomDelay();
            var number = receive();
            log("Consumed number: " + number);
        }

        private Integer receive() {
            synchronized (queue) {
                return queue.poll();
            }
        }

        private void waitForAvailability() throws InterruptedException {
            if (!full.tryAcquire()) {
                log("Waiting...");
                full.acquire();
            }
        }
    }

    private static class Producer extends ProducerConsumer {

        private Producer(Semaphore empty, Semaphore full, Queue<Integer> queue) {
            super(empty, full, queue);
        }

        @Override
        public void operate() throws InterruptedException {
            waitForAvailability();
            send(produce());
            full.release();
        }

        private void waitForAvailability() throws InterruptedException {
            if (!empty.tryAcquire()) {
                log("Waiting...");
                empty.acquire();
            }
        }

        private int produce() {
            log("Producing...");
            randomDelay();
            var number = random.nextInt(1000);
            log("Produced number: " + number);
            return number;
        }

        private void send(Integer number) {
            synchronized (queue) {
                queue.add(number);
            }
        }
    }
}
