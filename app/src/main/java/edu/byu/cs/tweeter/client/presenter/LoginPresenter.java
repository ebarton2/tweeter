package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.view.interfaces.AuthenticationViewInterface;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter extends AuthenticationAbstractPresenter<LoginPresenter.LoginView>
{
    public interface LoginView extends AuthenticationViewInterface {}

    public LoginPresenter(LoginView view)
    {
        super(view);
    }

    public void login(String alias, String password)
    {
        String validation = validate(null, null, alias, password, null);
        if (validation == null)
        {
            view.setErrorMessage(validation);
            view.infoMessage("Logging In...");
            userService.login(alias, password, new UserService.LoginObserver() {
                @Override
                public void handleSuccess(User user, AuthToken authToken) {
                    authenticateSuccess(user, authToken);
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
}
