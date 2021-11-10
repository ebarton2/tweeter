package edu.byu.cs.tweeter.server.dao;

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
import edu.byu.cs.tweeter.server.util.FakeData;

public class AuthenticationDAO {

    public RegisterResponse register(RegisterRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        if(request.getPassword() == null || request.getUsername() == null
                || request.getFirstName() == null || request.getLastName() == null
                || request.getImage() == null) return new RegisterResponse("Failed to register");

        User user = getDummyUser();
        AuthToken authToken = getDummyAuthToken();
        return new RegisterResponse(user, authToken);
    }

    public LoginResponse login(LoginRequest request) {

        // TODO: Generates dummy data. Replace with a real implementation.
        if(request.getPassword() == null || request.getUsername() == null) return new LoginResponse("Failed to login");
        User user = getDummyUser();
        AuthToken authToken = getDummyAuthToken();
        return new LoginResponse(user, authToken);
    }

    public LogoutResponse logout(LogoutRequest request) {
        return new LogoutResponse(true);
    }

    public GetUserResponse getUser(GetUserRequest request) {
        User user = getFakeData().findUserByAlias(request.getAlias());
        if (user != null) {
            return new GetUserResponse(true, user);
        } else {
            return new GetUserResponse(false, "Unable to find user");
        }
    }

    /**
     * Returns the dummy user to be returned by the login operation.
     * This is written as a separate method to allow mocking of the dummy user.
     *
     * @return a dummy user.
     */
    User getDummyUser() {
        return getFakeData().getFirstUser();
    }

    /**
     * Returns the dummy auth token to be returned by the login operation.
     * This is written as a separate method to allow mocking of the dummy auth token.
     *
     * @return a dummy auth token.
     */
    AuthToken getDummyAuthToken() {
        return getFakeData().getAuthToken();
    }


    FakeData getFakeData() {
        return new FakeData();
    }



}
