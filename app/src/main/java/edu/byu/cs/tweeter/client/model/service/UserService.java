package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetUserHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.LoginHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.LogoutHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.RegisterHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observers.AuthenticationServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observers.SimpleNotificationServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observers.UserServiceObserver;

public class UserService extends AbstractServiceTemplate
{

    public interface GetUserObserver extends UserServiceObserver {}

    public interface LoginObserver extends AuthenticationServiceObserver {}

    public interface RegisterObserver extends AuthenticationServiceObserver {}

    public interface LogoutObserver extends SimpleNotificationServiceObserver {}

    public void logout(LogoutObserver observer)
    {
        LogoutTask logoutTask = new LogoutTask(Cache.getInstance().getCurrUserAuthToken(), new LogoutHandler(observer));
        executeTask(logoutTask);
    }

    public void getUser(String alias, GetUserObserver getUserObserver)
    {
        GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
                alias, new GetUserHandler(getUserObserver));
        executeTask(getUserTask);
    }

    public void login(String alias, String password, LoginObserver observer)
    {
        LoginTask loginTask = new LoginTask(alias, password,
                new LoginHandler(observer));
        executeTask(loginTask);
    }

    public void register(String firstName, String lastName,
                         String alias, String password,
                         String imageBytesBase64, RegisterObserver observer)
    {
        RegisterTask registerTask = new RegisterTask(firstName, lastName,
                alias, password, imageBytesBase64, new RegisterHandler(observer));
        executeTask(registerTask);
    }
}
