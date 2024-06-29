package com.example.example28;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class QueueProducerConsumerWithWaitAndNotify {
    private static final int PRODUCE_TOTAL = 1000000;

    public static void main(String[] args) {
        var queue = new ThreadSafeQueue();
        var producer = new Producer(queue);
        var consumer = new Consumer(queue);

        producer.start();
        consumer.start();
    }


    private static class Consumer extends Thread {
        private final ThreadSafeQueue queue;

        private Consumer(ThreadSafeQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            Integer number;
            while ((number = queue.remove()) != null) {
                System.out.println("queue.size: " + queue.size() + ", consumed: " + number);
            }
        }
    }

    private static class Producer extends Thread {
        private final ThreadSafeQueue queue;
        private final Random random = new Random();

        private Producer(ThreadSafeQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < PRODUCE_TOTAL; i++) {
                queue.add(random.nextInt(1000));
            }
            queue.terminate();
            System.out.println("All numbers were produced!");
        }
    }

    private static class ThreadSafeQueue {
        private static final int CAPACITY = 50;
        private final Queue<Integer> queue = new LinkedList<>();
        private boolean isEmpty = true;
        private boolean isTerminate = false;

        public synchronized void add(Integer value) {
            while (queue.size() == CAPACITY) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            queue.add(value);
            isEmpty = false;
            notify();
        }

        public synchronized Integer remove() {
            while (isEmpty && !isTerminate) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (queue.size() == 1) {
                isEmpty = true;
            }
            if (queue.isEmpty() && isTerminate) {
                return null;
            }
            var removed = queue.remove();
            if (queue.size() == CAPACITY - 1) {
                notifyAll();
            }
            return removed;
        }

        public synchronized void terminate() {
            isTerminate = true;
            notifyAll();
        }

        public synchronized int size() {
            return queue.size();
        }

    }
}
