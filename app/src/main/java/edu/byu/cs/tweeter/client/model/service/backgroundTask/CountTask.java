package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class CountTask extends AuthorizedTask {
    public static final String COUNT_KEY = "count";

    public CountTask(Handler messageHandler, AuthToken authToken) {
        super(messageHandler, authToken);
    }


    @Override
    protected void runTask() throws IOException {

    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        msgBundle.putInt(COUNT_KEY, 20);
    }
}
