package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observers.ServiceObserver;

public class GetFollowersCountHandler extends CountNotificationHandler {

    public GetFollowersCountHandler(FollowService.FollowersCountObserver observer) {
        super(observer);
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to get followers count";
    }

    @Override
    protected String getCountKey() {
        return GetFollowersCountTask.COUNT_KEY;
    }
}
