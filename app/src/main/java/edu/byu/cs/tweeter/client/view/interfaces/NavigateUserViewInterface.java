package edu.byu.cs.tweeter.client.view.interfaces;

import edu.byu.cs.tweeter.model.domain.User;

public interface NavigateUserViewInterface extends ViewInterface {
    void navigateToUser(User user);
}
