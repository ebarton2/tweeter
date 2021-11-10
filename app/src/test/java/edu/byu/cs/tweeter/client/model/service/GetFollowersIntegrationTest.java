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
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.util.FakeData;

public class GetFollowersIntegrationTest {
    private static final String URL_PATH = "/getfollowers";

    private ServerFacade serverFacadeSpy;

    private FollowersRequest validRequest;
    private FollowersRequest invalidRequest;

    private FollowersResponse validResponse;
    private FollowersResponse invalidResponse;

    @BeforeEach
    public void setUp() {
        serverFacadeSpy = Mockito.spy(new ServerFacade());

        User user = new User("Allen", "Anderson", "@allen", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        AuthToken authToken = new AuthToken("");

        FakeData fakeData = new FakeData();

        validRequest = new FollowersRequest(authToken, user.getAlias(), 21, "@john");
        invalidRequest = new FollowersRequest(null, null, 0, null);

        validResponse = new FollowersResponse(fakeData.getFakeUsers(), true, false);
        invalidResponse = new FollowersResponse(null);

    }

    @Test
    public void test_success() {
        try {
            FollowersResponse response = serverFacadeSpy.getFollowers(validRequest, URL_PATH);

            Assertions.assertEquals(validResponse.getHasMorePages(), response.getHasMorePages());
            Assertions.assertEquals(validResponse.isSuccess(), response.isSuccess());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_failure() {
        try {
            FollowersResponse response = serverFacadeSpy.getFollowers(invalidRequest, URL_PATH);

            Assertions.assertEquals(invalidResponse.getHasMorePages(), response.getHasMorePages());
            Assertions.assertEquals(invalidResponse.isSuccess(), response.isSuccess());
            System.out.println(response.getMessage());
            Assertions.assertEquals(null, response.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
    }
}
