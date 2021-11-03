package edu.byu.cs.tweeter.model.net.request;

import org.omg.CORBA.PRIVATE_MEMBER;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UnfollowRequest {
    private User follower;
    private AuthToken authToken;

    public UnfollowRequest(User follower, AuthToken authToken) {
        this.follower = follower;
        this.authToken = authToken;
    }

    private UnfollowRequest() {}

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
