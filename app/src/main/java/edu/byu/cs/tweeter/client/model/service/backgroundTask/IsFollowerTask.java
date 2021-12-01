package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.Random;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;

/**
 * Background task that determines if one user is following another.
 */
public class IsFollowerTask extends AuthorizedTask {
    private static final String LOG_TAG = "IsFollowerTask";
    private static final String URL_PATH = "/isfollower";

    public static final String IS_FOLLOWER_KEY = "is-follower";

    /**
     * The alleged follower.
     */
    private User follower;
    /**
     * The alleged followee.
     */
    private User followee;

    private IsFollowerRequest request;

    private boolean followingState;


    public IsFollowerTask(AuthToken authToken, User follower, User followee, Handler messageHandler) {
        super(messageHandler, authToken);
        //this.authToken = authToken;
        this.follower = follower;
        this.followee = followee;
        this.request = new IsFollowerRequest(follower, followee, authToken);
    }

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        System.out.println("Checking to see if you are following this user.");

        IsFollowerResponse response = getServerFacade().getIsFollower(request, URL_PATH);
            if (response.isSuccess()){
                followingState = response.isFollowing();
                System.out.println(followingState);
            } else {
                throw new IOException("Failed to get correct input: IsFollowerTask");
            }
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        msgBundle.putBoolean(IS_FOLLOWER_KEY, followingState);
        // TODO: Replace random variable
    }
}
