package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;

/**
 * Background task that establishes a following relationship between two users.
 */
public class FollowTask extends AuthorizedTask {
    private static final String LOG_TAG = "FollowTask";
    private static final String URL_PATH = "/follow";

    /**
     * The user that is being followed.
     */
    private User followee;

    private FollowRequest request;

    public FollowTask(AuthToken authToken, User followee, Handler messageHandler) {
        super(messageHandler, authToken);
        this.request = new FollowRequest(followee, authToken);
        this.followee = followee;
    }

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        //TODO: implement later
            FollowResponse response = getServerFacade().getFollow(request, URL_PATH);
            if (response.isSuccess()){
                System.out.println("I followed out today!");
            } else {
                throw new IOException("Failed to get correct input: FollowTask");
            }
    }


    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        //TODO: implement later
    }

}
