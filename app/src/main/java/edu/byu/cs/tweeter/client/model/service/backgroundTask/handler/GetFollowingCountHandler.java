package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;

public class GetFollowingCountHandler extends CountNotificationHandler {

    public GetFollowingCountHandler(FollowService.FollowingCountObserver observer) {
        super(observer);
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to get following count";
    }

    @Override
    protected String getCountKey() {
        return GetFollowingCountTask.COUNT_KEY;
    }
}
