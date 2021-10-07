package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;

/**
 * Message handler (i.e., observer) for GetFeedTask.
 */
public class GetFeedHandler extends PagedNotificationHandler {
    public GetFeedHandler(StatusService.FeedObserver observer)
    {
        super(observer);
    }

    @Override
    protected String GetItems() {
        return GetFeedTask.ITEMS_KEY;
    }

    @Override
    protected String GetPages() {
        return GetFeedTask.MORE_PAGES_KEY;
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to get feed";
    }
}