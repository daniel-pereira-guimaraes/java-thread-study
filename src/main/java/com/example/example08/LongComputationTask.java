package com.example.example08;

import java.math.BigInteger;

public class LongComputationTask implements Runnable {
    private final BigInteger base;
    private final BigInteger power;

    public LongComputationTask(BigInteger base, BigInteger power) {
        this.base = base;
        this.power = power;
    }

    @Override
    public void run() {
        try {
            System.out.println(base + " ^ " + power + " = " + calculate());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private BigInteger calculate() {
        var result = BigInteger.ONE;
        for (var i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
            if (Thread.currentThread().isInterrupted()) {
                throw new RuntimeException("Prematurely interrupted computation");
            }
            result = result.multiply(base);
        }
        return result;
    }
}
