package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;

/**
 * Message handler (i.e., observer) for GetStoryTask.
 */
public class GetStoryHandler extends PagedNotificationHandler {
    private StatusService.StoryObserver observer;

    public GetStoryHandler(StatusService.StoryObserver observer)
    {
        super(observer);
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to get story";
    }

    @Override
    protected String GetItems() {
        return GetStoryTask.ITEMS_KEY;
    }

    @Override
    protected String GetPages() {
        return GetStoryTask.MORE_PAGES_KEY;
    }
}