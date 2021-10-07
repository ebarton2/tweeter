package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import edu.byu.cs.tweeter.client.model.service.FollowService;

public class UnfollowHandler extends SimpleNotificationHandler {
    public UnfollowHandler(FollowService.UnfollowObserver observer) { super(observer); }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to unfollow";
    }
}
