package edu.byu.cs.tweeter.client.model.service.backgroundTask.observers;

import edu.byu.cs.tweeter.model.domain.User;

public interface UserServiceObserver extends ServiceObserver {
    void handleSuccess(User user);
}
