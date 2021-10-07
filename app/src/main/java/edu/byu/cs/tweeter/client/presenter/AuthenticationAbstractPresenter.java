package edu.byu.cs.tweeter.client.presenter;

import android.widget.ImageView;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.view.interfaces.AuthenticationViewInterface;

public class AuthenticationAbstractPresenter<T extends AuthenticationViewInterface> extends AbstractPresenter<T>
{
    protected UserService userService;

    public AuthenticationAbstractPresenter(T view) {
        super(view);
        userService = new UserService();
    }

    protected String validate(String firstName, String lastName,
                              String alias, String password, ImageView image) {
        String validation = null;
        if (firstName != null) {
            if (firstName.length() == 0) {
                return "First Name cannot be empty.";
            }
            if (lastName.length() == 0) {
                return "Last Name cannot be empty.";
            }
            if (image.getDrawable() == null) {
                return "Profile image must be uploaded.";
            }
        }
        if (alias.length() == 0) {
            return "Alias cannot be empty.";
        }
        if (alias.charAt(0) != '@') {
            return "Alias must begin with @.";
        }
        if (alias.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return "Password cannot be empty.";
        }

        return validation;
    }
}
