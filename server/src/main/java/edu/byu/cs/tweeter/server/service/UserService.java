package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.server.dao.AuthenticationDAO;
import edu.byu.cs.tweeter.server.util.FakeData;

public class UserService {

    public LoginResponse login(LoginRequest request) {
        return getAuthenticateionDAO().login(request);
    }

    public RegisterResponse register(RegisterRequest request) {
        return getAuthenticateionDAO().register(request);
    }

    public LogoutResponse logout(LogoutRequest request) {
        return getAuthenticateionDAO().logout(request);
    }

    public GetUserResponse getUser(GetUserRequest request) {
        return getAuthenticateionDAO().getUser(request);
    }

    AuthenticationDAO getAuthenticateionDAO() { return new AuthenticationDAO(); }
}
