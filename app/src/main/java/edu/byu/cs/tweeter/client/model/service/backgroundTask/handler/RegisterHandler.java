package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;

public class RegisterHandler extends AuthenticationNotificationHandler {
    public RegisterHandler(UserService.RegisterObserver observer)
    {
        super(observer);
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to register";
    }

    @Override
    protected String GetUserKey() {
        return RegisterTask.USER_KEY;
    }

    @Override
    protected String GetAuthTokenKey() {
        return RegisterTask.AUTH_TOKEN_KEY;
    }
}