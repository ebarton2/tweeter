package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's feed.
 */
public class GetFeedTask extends PagedTask<Status> {
    private static final String LOG_TAG = "GetFeedTask";

    /**
     * The user whose feed is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    private User targetUser;

    public GetFeedTask(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                       Handler messageHandler) {
        super(messageHandler, authToken, limit, lastStatus);
        this.targetUser = targetUser;
    }

    @Override
    protected Pair<List<Status>, Boolean> getItems() {
        Pair<List<Status>, Boolean> pageOfStatus = getFakeData().getPageOfStatus(getLastItem(), getLimit());
        return pageOfStatus;
    }

    @Override
    protected List<User> convertItemsToUsers(List<Status> items) {
        List<User> users = items.stream().map(x -> x.user).collect(Collectors.toList());
        return users;
    }
}
