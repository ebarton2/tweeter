package edu.byu.cs.tweeter.client.model.service.backgroundTask.observers;

import java.util.List;

public interface PagedServiceObserver extends ServiceObserver {
    void handleSuccess(List items, boolean hasMorePages);
}
