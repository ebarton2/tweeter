package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.server.dao.AuthenticationDAO;

public class UserService {

    public LoginResponse login(LoginRequest request) {
        return getAuthenticationDAO().login(request);
    }

    public RegisterResponse register(RegisterRequest request) {
        return getAuthenticationDAO().register(request);
    }

    public LogoutResponse logout(LogoutRequest request) {
        return getAuthenticationDAO().logout(request);
    }

    public GetUserResponse getUser(GetUserRequest request) {
        return getAuthenticationDAO().getUser(request);
    }

    AuthenticationDAO getAuthenticationDAO() { return new AuthenticationDAO(); }
}
