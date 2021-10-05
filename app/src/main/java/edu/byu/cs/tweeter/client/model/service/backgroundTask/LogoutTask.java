package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;

/**
 * Background task that logs out a user (i.e., ends a session).
 */
public class LogoutTask extends BackgroundTask {
    private static final String LOG_TAG = "LogoutTask";

    /**
     * Auth token for logged-in user.
     */
    private AuthToken authToken;

    public LogoutTask(AuthToken authToken, Handler messageHandler) {
        super(messageHandler);
        this.authToken = authToken;
    }


    @Override
    protected void runTask() throws IOException {
        //Todo
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        //TODO
    }
}
