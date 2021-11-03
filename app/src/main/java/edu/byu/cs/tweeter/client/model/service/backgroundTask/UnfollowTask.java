package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;

/**
 * Background task that removes a following relationship between two users.
 */
public class UnfollowTask extends AuthorizedTask {
    private static final String LOG_TAG = "UnfollowTask";
    private static final String URL_PATH = "/unfollow";

    /**
     * The user that is being followed.
     */
    private User followee;

    private UnfollowRequest request;


    public UnfollowTask(AuthToken authToken, User followee, Handler messageHandler) {
        super(messageHandler, authToken);
        //this.authToken = authToken;
        this.followee = followee;
        this.request = new UnfollowRequest(followee, authToken);
    }

    @Override
    protected void runTask() throws IOException {
        try {
            UnfollowResponse response = getServerFacade().getUnfollow(request, URL_PATH);
            if (response.isSuccess()){
                System.out.println("I unfollowed out today!");
            } else {
                throw new IOException("Failed to get correct input: FollowTask");
            }
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {

    }
}
