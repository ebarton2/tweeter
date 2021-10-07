package edu.byu.cs.tweeter.client.model.service.backgroundTask.observers;

public interface CountServiceObserver extends ServiceObserver {
    void handleSuccess(int count);
}
