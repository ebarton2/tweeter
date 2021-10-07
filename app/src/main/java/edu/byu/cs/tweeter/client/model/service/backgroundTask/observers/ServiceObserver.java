package edu.byu.cs.tweeter.client.model.service.backgroundTask.observers;

public interface ServiceObserver {
    void handleFailure(String message);
}
