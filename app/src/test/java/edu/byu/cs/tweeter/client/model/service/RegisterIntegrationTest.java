package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.RegisterHandler;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;

public class RegisterIntegrationTest {
    private static final String URL_PATH = "/register";

    private RegisterRequest requestValid;
    private RegisterResponse responseValid;

    private RegisterRequest requestInvalid;
    private RegisterResponse responseInvalid;

    private ServerFacade serverFacadeSpy;
    @BeforeEach
    public void setUp() {

        serverFacadeSpy = Mockito.spy(new ServerFacade());


        User currentUser = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        AuthToken authToken = new AuthToken(null);

        requestValid = new RegisterRequest("@u", "u", currentUser.getFirstName(), currentUser.getLastName(), currentUser.getImageUrl());
        requestInvalid = new RegisterRequest(null, null, null, null, null);

        responseValid = new RegisterResponse(currentUser, authToken);
        responseInvalid = new RegisterResponse("Failed to register");


    }

    @Test
    public void register_success() {
        /*serverFacadeSpy.register(requestValid, URL_PATH);

        Mockito.verify()*/
        try {
            RegisterResponse response = serverFacadeSpy.register(requestValid, URL_PATH);

            Assertions.assertEquals(responseValid.getAuthToken().getToken(), response.getAuthToken().getToken());
            Assertions.assertEquals(responseValid.isSuccess(), response.isSuccess());

            Mockito.verify(serverFacadeSpy).register(requestValid, URL_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void register_fail() {
        try {
            RegisterResponse response = serverFacadeSpy.register(requestInvalid, URL_PATH);

            Assertions.assertEquals(responseInvalid.getMessage(), response.getMessage());
            Assertions.assertEquals(responseInvalid.isSuccess(), response.isSuccess());

            Mockito.verify(serverFacadeSpy).register(requestInvalid, URL_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
    }
}
