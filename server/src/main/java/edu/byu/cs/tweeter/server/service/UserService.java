package edu.byu.cs.tweeter.server.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

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
import edu.byu.cs.tweeter.server.dao.AuthenticationDynamoDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.dao.UserDynamoDAO;
import edu.byu.cs.tweeter.server.factory.DAOFactory;

public class UserService extends AbstractService {

    public UserService(DAOFactory factory) {
        super(factory);
    }

    public LoginResponse login(LoginRequest request) {
        if(request.getPassword() == null || request.getUsername() == null) return new LoginResponse("Failed to login");

        User user = getUserDAO().login(request.getUsername(), request.getPassword());
        AuthToken authToken = generateAuthToken();
        getAuthenticationDAO().createAuthToken(request.getUsername(), authToken);

        if(user == null || authToken == null) return new LoginResponse("Failed to login: User or AuthToken null");
        return new LoginResponse(user, authToken);
    }

    public RegisterResponse register(RegisterRequest request) {
        if(request.getPassword() == null || request.getUsername() == null
                || request.getFirstName() == null || request.getLastName() == null
                || request.getImage() == null) return new RegisterResponse("Failed to register: incomplete request");

        String salt = getSalt();
        String securePass = getUserDAO().hashPassword(request.getPassword(), salt);

        User user = getUserDAO().register(request.getUsername(),
                securePass, salt,
                request.getFirstName(),
                request.getLastName(),
                request.getImage());
        AuthToken authToken = generateAuthToken();
        getAuthenticationDAO().createAuthToken(request.getUsername(), authToken);
        if(user == null || authToken == null) return new RegisterResponse("Failed to register: User or AuthToken null");
        return new RegisterResponse(user, authToken);
    }

    public LogoutResponse logout(LogoutRequest request) {
        try {
            boolean logoutSuccess = getAuthenticationDAO().logout(request.getAuthToken().getToken());
            return new LogoutResponse(logoutSuccess);
        } catch (Exception e) {
            e.printStackTrace();
            return new LogoutResponse(true, e.getMessage());
        }
    }

    public GetUserResponse getUser(GetUserRequest request) {
        String alias = request.getAlias();
        AuthToken authToken = request.getAuthToken();
        try {
            if(getAuthenticationDAO().isAuthorized(authToken.getToken())) {
                User user = getUserDAO().getUser(alias);
                if (user != null) {
                    return new GetUserResponse(true, user);
                } else {
                    return new GetUserResponse(false, "Failed to find user");
                }
            }
            return new GetUserResponse(false, "Unauthorized access");
        } catch (Exception e) {
            e.printStackTrace();
            return new GetUserResponse(false, e.getMessage());
        }
    }

    private String getSalt() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return Base64.getEncoder().encodeToString(salt);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return "FAILED TO GET SALT";
    }

    private static String createID() {
        return UUID.randomUUID().toString();
    }

    private AuthToken generateAuthToken() {
        return new AuthToken(createID(), Long.toString(Instant.now().toEpochMilli()/1000));
    }
}
