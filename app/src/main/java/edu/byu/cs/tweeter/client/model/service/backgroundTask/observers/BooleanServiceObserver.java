package edu.byu.cs.tweeter.client.model.service.backgroundTask.observers;

public interface BooleanServiceObserver extends ServiceObserver {
    void handleSuccess(boolean flip);
}
