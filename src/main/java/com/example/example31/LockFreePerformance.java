package com.example.example31;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class LockFreePerformance {

    public static void main(String[] args) throws InterruptedException {

        var stacks = List.of(
                new StackWithLock<Integer>(),
                new LockFreeStack<Integer>()
        );
        var random = new Random();

        for (var stack : stacks) {
            var pushThread = new Thread(() -> {
                while (true) {
                    stack.push(random.nextInt(1000));
                }
            });

            var popThread = new Thread(() -> {
                while (true) {
                    stack.pop();
                }
            });

            pushThread.setDaemon(true);
            popThread.setDaemon(true);

            pushThread.start();
            popThread.start();

            Thread.sleep(1000);

            System.out.println(stack.getClass().getSimpleName() + ": " + stack.getCounter() + " operations by 1s");
        }
    }

    private interface Stack<T> {
        void push(T value);
        T pop();
        int getCounter();
    }

    public static class LockFreeStack<T> implements Stack<T> {
        private final AtomicReference<StackNode<T>> head = new AtomicReference<>();
        private final AtomicInteger counter = new AtomicInteger(0);

        @Override
        public void push(T value) {
            var newHeadNode = new StackNode<>(value);

            while (true) {
                var currentHeadNode = head.get();
                newHeadNode.next = currentHeadNode;
                if (head.compareAndSet(currentHeadNode, newHeadNode)) {
                    break;
                } else {
                    LockSupport.parkNanos(1);
                }
            }
            counter.incrementAndGet();
        }

        @Override
        public T pop() {
            var currentHeadNode = head.get();
            StackNode<T> newHeadNode;
            while (currentHeadNode != null) {
                newHeadNode = currentHeadNode.next;
                if (head.compareAndSet(currentHeadNode, newHeadNode)) {
                    break;
                } else {
                    currentHeadNode = head.get();
                }
            }
            counter.incrementAndGet();
            return currentHeadNode != null ? currentHeadNode.value : null;
        }

        public int getCounter() {
            return counter.get();
        }
    }

    private static class StackWithLock<T> implements Stack<T> {
        private StackNode<T> head;
        private int counter = 0;

        @Override
        public synchronized void push(T value) {
            var node = new StackNode<>(value);
            node.next = head;
            head = node;
            counter++;
        }

        @Override
        public synchronized T pop() {
            counter++;
            if (head == null) {
                return null;
            }
            var value = head.value;
            head = head.next;
            return value;
        }

        @Override
        public synchronized int getCounter() {
            return counter;
        }
    }

    private static class StackNode<T> {
        public T value;
        public StackNode<T> next;

        public StackNode(T value) {
            this.value = value;
        }
    }
}
