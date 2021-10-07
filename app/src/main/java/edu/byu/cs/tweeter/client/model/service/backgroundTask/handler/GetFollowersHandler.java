package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;

/**
 * Message handler (i.e., observer) for GetFollowersTask.
 */
public class GetFollowersHandler extends PagedNotificationHandler
{
    public GetFollowersHandler(FollowService.FollowersObserver observer)
    {
        super(observer);
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to get followers";
    }

    @Override
    protected String GetItems() {
        return GetFollowersTask.ITEMS_KEY;
    }

    @Override
    protected String GetPages() {
        return GetFollowersTask.MORE_PAGES_KEY;
    }
}
