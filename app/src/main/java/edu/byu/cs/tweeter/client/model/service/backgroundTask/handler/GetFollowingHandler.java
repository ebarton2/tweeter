package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;

/**
 * Message handler (i.e., observer) for GetFollowingTask.
 */
public class GetFollowingHandler extends PagedNotificationHandler {

    public GetFollowingHandler(FollowService.FollowingObserver observer) {
        super(observer);
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to get following";
    }

    @Override
    protected String GetItems() {
        return GetFollowingTask.ITEMS_KEY;
    }

    @Override
    protected String GetPages() {
        return GetFollowingTask.MORE_PAGES_KEY;
    }
}
