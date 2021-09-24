package edu.byu.cs.tweeter.client.model.service;

import android.media.tv.TvContract;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetUserHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.LoginHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.LogoutHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.RegisterHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService
{

    public interface GetUserObserver
    {
        void handleSuccess(User user);
        void handleFailure(String message);
    }

    public interface LoginObserver
    {
        void handleSuccess(User user, AuthToken authToken);
        void handleFailure(String message);
    }

    public interface RegisterObserver
    {
        void handleSuccess(User user, AuthToken authToken);
        void handleFailure(String message);
    }

    public interface LogoutObserver
    {
        void handleSuccess();
        void handleFailure(String message);
    }

    public void logout(LogoutObserver observer)
    {
        LogoutTask logoutTask = new LogoutTask(Cache.getInstance().getCurrUserAuthToken(), new LogoutHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(logoutTask);
    }

    public void getUser(String alias, GetUserObserver getUserObserver)
    {
        GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
                alias, new GetUserHandler(getUserObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getUserTask);
    }

    public void login(String alias, String password, LoginObserver observer)
    {
        LoginTask loginTask = new LoginTask(alias, password,
                new LoginHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(loginTask);
    }

    public void register(String firstName, String lastName,
                         String alias, String password,
                         String imageBytesBase64, RegisterObserver observer)
    {
        RegisterTask registerTask = new RegisterTask(firstName, lastName,
                alias, password, imageBytesBase64, new RegisterHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(registerTask);
    }
}
