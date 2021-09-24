package edu.byu.cs.tweeter.client.presenter;

import android.content.Intent;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.view.main.MainActivity;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter
{
    private LoginView view;
    private UserService userService;

    public interface LoginView
    {
        void infoMessage(String message);
        void clearInfoMessage();
        void setErrorMessage(String message);
        void navigateToUser(User user);
    }

    public LoginPresenter(LoginView view)
    {
        this.view = view;
        userService = new UserService();
    }

    public void login(String alias, String password)
    {
        String validation = validateLogin(alias, password);
        if (validation == null)
        {
            view.setErrorMessage(validation);
            view.infoMessage("Logging In...");
            userService.login(alias, password, new UserService.LoginObserver() {
                @Override
                public void handleSuccess(User user, AuthToken authToken) {
                    // Cache user session information
                    Cache.getInstance().setCurrUser(user);
                    Cache.getInstance().setCurrUserAuthToken(authToken);
                    view.clearInfoMessage();
                    view.infoMessage("Hello " + Cache.getInstance().getCurrUser().getName());
                    view.navigateToUser(user);
                }

                @Override
                public void handleFailure(String message) {
                    view.infoMessage(message);
                }
            });
        }
        else
        {
            view.setErrorMessage(validation);
        }
    }

    public String validateLogin(String alias, String password)
    {
        String validation = null;
        if (alias.length() == 0) {// Needed so app won't crash trying to look for a null string at an index
            return "Alias cannot be empty.";
        }
        if (alias.charAt(0) != '@') {
            return "Alias must begin with @.";
            //throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
            //throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            return "Password cannot be empty.";
            //throw new IllegalArgumentException();
        }
        return validation;
    }
}
