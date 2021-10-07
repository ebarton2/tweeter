package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.observers.BooleanServiceObserver;

public abstract class BooleanNotificationHandler<T extends BooleanServiceObserver> extends BackgroundTaskHandler<T> {
    public BooleanNotificationHandler(T observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(T observer, Bundle bundle) {
        boolean isFollower = bundle.getBoolean(GetFollowerKey());
        observer.handleSuccess(isFollower);
    }

    protected abstract String GetFollowerKey();
}
