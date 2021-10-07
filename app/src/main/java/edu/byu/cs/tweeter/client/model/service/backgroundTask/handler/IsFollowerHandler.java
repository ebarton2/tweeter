package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;

public class IsFollowerHandler extends BooleanNotificationHandler {
    public IsFollowerHandler(FollowService.IsFollowerObserver observer)
    {
        super(observer);
    }

    @Override
    protected String GetFollowerKey() {
        return IsFollowerTask.IS_FOLLOWER_KEY;
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to determine following relationship";
    }
}
