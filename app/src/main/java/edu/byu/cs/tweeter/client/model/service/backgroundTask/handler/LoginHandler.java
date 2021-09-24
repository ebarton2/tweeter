package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Message handler (i.e., observer) for LoginTask
 */
public class LoginHandler extends Handler
{
    private UserService.LoginObserver observer;

    public LoginHandler(UserService.LoginObserver observer)
    {
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg)
    {
        boolean success = msg.getData().getBoolean(LoginTask.SUCCESS_KEY);
        if (success)
        {
            User loggedInUser = (User) msg.getData().getSerializable(LoginTask.USER_KEY);
            AuthToken authToken = (AuthToken) msg.getData().getSerializable(LoginTask.AUTH_TOKEN_KEY);
            observer.handleSuccess(loggedInUser, authToken);
        }
        else if (msg.getData().containsKey(LoginTask.MESSAGE_KEY))
        {
            String message = msg.getData().getString(LoginTask.MESSAGE_KEY);
            observer.handleFailure("Failed to login: " + message);
        }
        else if (msg.getData().containsKey(LoginTask.EXCEPTION_KEY))
        {
            Exception ex = (Exception) msg.getData().getSerializable(LoginTask.EXCEPTION_KEY);
            observer.handleFailure("Failed to login because of exception: " + ex.getMessage());
        }
    }
}