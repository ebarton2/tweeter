package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

public abstract class CountTask extends BackgroundTask {
    public CountTask(Handler messageHandler) {
        super(messageHandler);
    }
}
