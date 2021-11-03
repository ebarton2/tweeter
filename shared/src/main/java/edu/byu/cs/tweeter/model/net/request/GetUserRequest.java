package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class GetUserRequest {
    private String alias;
    private AuthToken authToken;

    public GetUserRequest(String alias, AuthToken authToken) {
        this.alias = alias;
        this.authToken = authToken;
    }

    private GetUserRequest() {}

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
