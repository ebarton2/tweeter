package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.observers.CountServiceObserver;

public abstract class CountNotificationHandler<T extends CountServiceObserver> extends BackgroundTaskHandler<T> {
    public CountNotificationHandler(T observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(T observer, Bundle bundle) {
        int count = bundle.getInt(getCountKey());
        observer.handleSuccess(count);
    }

    protected abstract String getCountKey();
}
