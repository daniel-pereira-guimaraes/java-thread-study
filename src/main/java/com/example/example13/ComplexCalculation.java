package com.example.example13;

import java.math.BigInteger;

public class ComplexCalculation {

    public BigInteger calculateResult(PowerExpression expression1,
                                      PowerExpression expression2, long timeout)
            throws InterruptedException {

        var thread1 = new PowerCalculatingThread(expression1);
        var thread2 = new PowerCalculatingThread(expression2);

        thread1.setDaemon(true);
        thread2.setDaemon(true);

        thread1.start();
        thread2.start();

        thread1.join(timeout);
        thread2.join(timeout);

        if (thread1.calculated && thread2.calculated) {
            return thread1.getResult().add(thread2.getResult());
        }

        throw new RuntimeException("Timeout!");
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private final PowerExpression expression;
        private boolean calculated = false;

        public PowerCalculatingThread(PowerExpression expression) {
            this.expression = expression;
        }

        @Override
        public void run() {
            for (BigInteger i = BigInteger.ONE;
                 i.compareTo(expression.power) <= 0;
                 i = i.add(BigInteger.ONE)) {
                result = result.multiply(expression.base);
            }
            calculated = true;
        }

        public BigInteger getResult() {
            return result;
        }

    }

    public static PowerExpression powerExpression(long base, long power) {
        return new PowerExpression(BigInteger.valueOf(base), BigInteger.valueOf(power));
    }

    public static final class PowerExpression {
        private final BigInteger base;
        private final BigInteger power;

        public PowerExpression(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public String toString() {
            return base + "^" + power;
        }
    }
}
