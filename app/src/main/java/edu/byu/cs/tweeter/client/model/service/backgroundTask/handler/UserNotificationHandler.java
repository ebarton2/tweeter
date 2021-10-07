package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observers.UserServiceObserver;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class UserNotificationHandler<T extends UserServiceObserver> extends BackgroundTaskHandler<T>{
    public UserNotificationHandler(T observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(T observer, Bundle bundle) {
        User user = (User) bundle.getSerializable(GetUserKey());
        observer.handleSuccess(user);
    }

    protected abstract String GetUserKey();
}
