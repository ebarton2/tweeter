package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that establishes a following relationship between two users.
 */
public class FollowTask extends AuthorizedTask {
    private static final String LOG_TAG = "FollowTask";


    /**
     * The user that is being followed.
     */
    private User followee;

    public FollowTask(AuthToken authToken, User followee, Handler messageHandler) {
        super(messageHandler, authToken);
        this.followee = followee;
    }

    @Override
    protected void runTask() throws IOException {
        //TODO: implement later
    }


    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        //TODO: implement later
    }

}
