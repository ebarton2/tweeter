package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.util.FakeData;

/**
 * Background task that returns the profile for a specified user.
 */
public class GetUserTask extends AuthorizedTask {
    private static final String LOG_TAG = "GetUserTask";
    private static final String URL_PATH = "/getuser";
    public static final String USER_KEY = "user";

    /**
     * Alias (or handle) for user whose profile is being retrieved.
     */
    private String alias;

    private User targetUser;
    private GetUserRequest request;

    public GetUserTask(AuthToken authToken, String alias, Handler messageHandler) {
        super(messageHandler, authToken);
        this.authToken = authToken;
        this.alias = alias;
        this.request = new GetUserRequest(alias, authToken);
    }

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        //TODO: Fix when we switch to live data rather than dummy data
            GetUserResponse response = getServerFacade().getUser(request, URL_PATH);
            if (response.isSuccess()) {
                targetUser = response.getTargetUser();
                BackgroundTaskUtils.loadImage(targetUser);
            } else {
                throw new IOException(response.getMessage());
            }
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, targetUser);
    }

    private User getUser() {
        User user = getFakeData().findUserByAlias(alias);
        return user;
    }


}
