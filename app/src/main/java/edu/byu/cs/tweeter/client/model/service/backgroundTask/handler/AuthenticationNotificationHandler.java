package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.observers.AuthenticationServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class AuthenticationNotificationHandler<T extends AuthenticationServiceObserver> extends BackgroundTaskHandler<T> {
    public AuthenticationNotificationHandler(T observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(T observer, Bundle bundle) {
        User registeredUser = (User) bundle.getSerializable(GetUserKey());
        AuthToken authToken = (AuthToken) bundle.getSerializable(GetAuthTokenKey());
        observer.handleSuccess(registeredUser, authToken);
    }

    protected abstract String GetUserKey();

    protected abstract String GetAuthTokenKey();
}
