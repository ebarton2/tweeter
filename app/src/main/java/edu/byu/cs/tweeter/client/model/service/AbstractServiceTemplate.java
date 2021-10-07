package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;

public abstract class AbstractServiceTemplate<T extends BackgroundTask> {
    public void executeTask(T task) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(task);
    }
}
