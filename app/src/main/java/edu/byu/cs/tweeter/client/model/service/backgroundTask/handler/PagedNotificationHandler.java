package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.observers.PagedServiceObserver;

public abstract class PagedNotificationHandler<T extends PagedServiceObserver> extends BackgroundTaskHandler<T> {
    public PagedNotificationHandler(T observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(T observer, Bundle bundle) {
        List<T> items = (List<T>) bundle.getSerializable(GetItems());
        boolean hasMorePages = bundle.getBoolean(GetPages());
        observer.handleSuccess(items, hasMorePages);
    }

    protected abstract String GetItems();

    protected abstract String GetPages();
}
