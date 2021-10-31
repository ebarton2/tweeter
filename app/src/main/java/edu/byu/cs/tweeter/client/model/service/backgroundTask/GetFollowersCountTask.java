package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowerCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingCountRequest;
import edu.byu.cs.tweeter.model.net.response.FollowerCountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingCountResponse;

/**
 * Background task that queries how many followers a user has.
 */
public class GetFollowersCountTask extends CountTask {
    private static final String LOG_TAG = "GetFollowerCountTask";
    private static final String URL_PATH = "/getfollowercount";

    private final FollowerCountRequest request;

    /**
     * The user whose follower count is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */

    public GetFollowersCountTask(FollowerCountRequest request, Handler messageHandler) {
        super(messageHandler, request.getAuthToken(), request.getUser());
        this.request = request;
    }

    @Override
    protected void runTask() throws IOException {
        try {
            FollowerCountResponse response = getServerFacade().getFollowerCountResponse(request, URL_PATH);
            if(response.isSuccess()) {
                setCount(response.getCount());
                sendSuccessMessage();
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
    }
}
