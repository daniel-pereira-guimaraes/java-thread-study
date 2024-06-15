package com.example.example05;

public final class AscendingHackerThread extends HackerThread {
    public AscendingHackerThread(Vault vault) {
        super(vault);
    }

    @Override
    public void run() {
        for (int guess = 0; guess <= Main.MAX_PASSWORD; guess++) {
            if (vault.isCorrectPassword(guess)) {
                System.out.println(getName() + " guessed the password " + guess);
                System.exit(0);
            }
        }
    }
}
