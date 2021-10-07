package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observers.ServiceObserver;

public class LogoutHandler extends SimpleNotificationHandler {
    private UserService.LogoutObserver observer;

    public LogoutHandler(UserService.LogoutObserver observer)
    {
        super(observer);
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(LogoutTask.SUCCESS_KEY);
        if (success) {
            observer.handleSuccess();
        } else if (msg.getData().containsKey(LogoutTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(LogoutTask.MESSAGE_KEY);
            observer.handleFailure(": " + message);
        } else if (msg.getData().containsKey(LogoutTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(LogoutTask.EXCEPTION_KEY);
            observer.handleFailure("Failed to logout because of exception: " + ex.getMessage());
        }
    }

    @Override
    protected void handleSuccessMessage(ServiceObserver observer, Bundle bundle) {

    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to logout";
    }
}
