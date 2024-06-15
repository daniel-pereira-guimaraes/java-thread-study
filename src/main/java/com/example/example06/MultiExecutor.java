package com.example.example06;

import java.util.Collection;

public class MultiExecutor {

    private final Collection<Runnable> tasks;

    public MultiExecutor(Collection<Runnable> tasks) {
        this.tasks = tasks;
    }

    public void executeAll() {
        tasks.forEach(task -> new Thread(task).start());
    }
}
