package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowerCountRequest {

    private AuthToken authToken;
    private User user;

    public FollowerCountRequest(AuthToken authToken, User user)
    {
        this.authToken = authToken;
        this.user = user;
    }

    private FollowerCountRequest() {}

    public User getUser() { return user; }

    public AuthToken getAuthToken() { return authToken; }

    public void setAuthToken(AuthToken authToken) { this.authToken = authToken; }

    public void setUser(User user) { this.user = user; }
}
