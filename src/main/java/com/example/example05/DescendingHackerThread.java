package com.example.example05;

public final class DescendingHackerThread extends HackerThread {
    public DescendingHackerThread(Vault vault) {
        super(vault);
    }

    @Override
    public void run() {
        for (int guess = Main.MAX_PASSWORD; guess >= 0; guess--) {
            if (vault.isCorrectPassword(guess)) {
                System.out.println(getName() + " guessed the password " + guess);
                System.exit(0);
            }
        }
    }
}
