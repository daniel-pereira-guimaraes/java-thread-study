package com.example.example12;

import java.math.BigInteger;

public class FactorialThread extends Thread {
    private final long input;
    private BigInteger result;
    private boolean finished = false;

    public FactorialThread(long input) {
        this.input = input;
    }

    @Override
    public void run() {
        calculate();
        finished = true;
    }

    public long input() {
        return input;
    }

    public BigInteger result() {
        return result;
    }

    public boolean isFinished() {
        return finished;
    }

    private void calculate() {
        result = BigInteger.valueOf(input);
        for (long i = input - 1; i > 1; i--) {
            result = result.multiply(BigInteger.valueOf(i));
        }
    }
}
