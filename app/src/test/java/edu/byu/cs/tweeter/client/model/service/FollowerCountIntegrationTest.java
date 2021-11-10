package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowerCountRequest;
import edu.byu.cs.tweeter.model.net.response.FollowerCountResponse;

public class FollowerCountIntegrationTest {
    private static final String URL_PATH = "/getfollowercount";

    private ServerFacade serverFacadeSpy;

    private FollowerCountRequest validRequest;
    private FollowerCountRequest invalidRequest;

    private FollowerCountResponse successResponse;
    private FollowerCountResponse failureResponse;

    @BeforeEach
    public void setup() {
        User user = new User("lastName", "firstName", null);

        AuthToken authToken = new AuthToken("");

        validRequest = new FollowerCountRequest(authToken, user);
        invalidRequest = new FollowerCountRequest(null, null);

        successResponse = new FollowerCountResponse(true, 21);
        failureResponse = new FollowerCountResponse(false, "Unable to find user", 0);

        ServerFacade serverFacade = new ServerFacade();
        serverFacadeSpy = Mockito.spy(serverFacade);
    }

    @Test
    public void testGetFollowerCount_validRequest() {
        try {
            FollowerCountResponse getFollowerCountResponse = serverFacadeSpy.getFollowerCountResponse(validRequest, URL_PATH);

            Assertions.assertEquals(successResponse.getCount(), getFollowerCountResponse.getCount());
            Assertions.assertEquals(successResponse.isSuccess(), getFollowerCountResponse.isSuccess());
            Assertions.assertEquals(successResponse.getMessage(), getFollowerCountResponse.getMessage());
            Mockito.verify(serverFacadeSpy).getFollowerCountResponse(validRequest, URL_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetFollowerCount_InvalidRequest() {
        try {
            FollowerCountResponse getFollowerCountResponse = serverFacadeSpy.getFollowerCountResponse(invalidRequest, URL_PATH);

            Assertions.assertEquals(failureResponse.getCount(), getFollowerCountResponse.getCount());
            Assertions.assertEquals(failureResponse.isSuccess(), getFollowerCountResponse.isSuccess());
            Assertions.assertEquals(failureResponse.getMessage(), getFollowerCountResponse.getMessage());
            Mockito.verify(serverFacadeSpy).getFollowerCountResponse(invalidRequest, URL_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
    }
}