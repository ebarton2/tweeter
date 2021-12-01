package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;

public interface AuthenticationDAO {
    AuthToken createAuthToken(String alias, AuthToken authToken);
    String getAliasFromToken(String token) throws Exception;
    boolean isAuthorized(String authToken) throws Exception;
    boolean logout(String token) throws Exception;
}
