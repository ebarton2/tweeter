package edu.byu.cs.tweeter.model.net.request;

import java.security.PrivateKey;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowRequest {
    private User followee;
    private AuthToken authToken;

    public FollowRequest(User followee, AuthToken authToken) {
        this.followee = followee;
        this.authToken = authToken;
    }

    private FollowRequest() {}

    public User getFollowee() {
        return followee;
    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
