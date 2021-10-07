package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observers.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.User;

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
