package com.example.example05;

public final class Vault {
    private final int password;

    public Vault(int password) {
        this.password = password;
    }

    public boolean isCorrectPassword(int guess) {
        try {
            Thread.sleep(5);
        } catch (InterruptedException ignored) {
        }
        return this.password == guess;
    }
}
