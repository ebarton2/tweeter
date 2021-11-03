package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;

public class RegisterResponse extends Response {
    private User user;
    private AuthToken authToken;

    public RegisterResponse(String message) {
        super(false, message);
    }

    public RegisterResponse(User user, AuthToken authToken) {
        super(true, null);
        this.authToken = authToken;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
