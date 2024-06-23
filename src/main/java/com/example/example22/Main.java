package com.example.example22;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        var metrics = new Metrics();
        var metricsPrinter = new MetricsPrinter(metrics);
        var businessLogic1 = new BusinessLogic(metrics);
        var businessLogic2 = new BusinessLogic(metrics);

        metricsPrinter.start();
        businessLogic1.start();
        businessLogic2.start();
    }


    public static class MetricsPrinter extends Thread {
        private final Metrics metrics;

        public MetricsPrinter(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
                double currentAverage = metrics.getAverage();
                System.out.println("Current average: " + currentAverage);
            }
        }

    }

    public static class BusinessLogic extends Thread {
        private final Metrics metrics;
        private final Random random = new Random();

        public BusinessLogic(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                long start = System.currentTimeMillis();
                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException ignored) {
                }
                long end = System.currentTimeMillis();
                metrics.addSample(end - start);
            }
        }
    }

    public static class Metrics {
        private long count = 0;
        private volatile double average = 0.0;

        public synchronized void addSample(long sample) {
            double currentSum = count * average;
            count++;
            average = (currentSum + sample) / count;
        }

        public double getAverage() {
            return average;
        }
    }
}
