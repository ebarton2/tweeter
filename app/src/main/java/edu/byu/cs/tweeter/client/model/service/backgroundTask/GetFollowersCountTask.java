package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that queries how many followers a user has.
 */
public class GetFollowersCountTask extends AuthorizedTask {
    private static final String LOG_TAG = "LogoutTask";

    public static final String COUNT_KEY = "count";

    /**
     * The user whose follower count is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    private User targetUser;

    public GetFollowersCountTask(AuthToken authToken, User targetUser, Handler messageHandler) {
        super(messageHandler, authToken);
        this.targetUser = targetUser;
    }

    @Override
    protected void runTask() throws IOException {

    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        msgBundle.putInt(COUNT_KEY, 20);
    }

}
