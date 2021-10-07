package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;

/**
 * Message handler (i.e., observer) for LoginTask
 */
public class LoginHandler extends AuthenticationNotificationHandler
{
    public LoginHandler(UserService.LoginObserver observer)
    {
        super(observer);
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to login";
    }

    @Override
    protected String GetUserKey() {
        return LoginTask.USER_KEY;
    }

    @Override
    protected String GetAuthTokenKey() {
        return LoginTask.AUTH_TOKEN_KEY;
    }
}