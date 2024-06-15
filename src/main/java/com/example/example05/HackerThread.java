package com.example.example05;

public abstract class HackerThread extends Thread {
    protected final Vault vault;

    protected HackerThread(Vault vault) {
        this.vault = vault;
        this.setName(getClass().getSimpleName());
        this.setPriority(Thread.MAX_PRIORITY);
    }

    @Override
    public synchronized void start() {
        System.out.println("Starting " + getName());
        super.start();
    }
}
