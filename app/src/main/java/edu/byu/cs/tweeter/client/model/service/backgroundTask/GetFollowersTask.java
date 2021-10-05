package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of followers.
 */
public class GetFollowersTask extends PagedTask<User> {
    private static final String LOG_TAG = "GetFollowersTask";

    /**
     * The user whose followers are being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    private User targetUser;

    public GetFollowersTask(AuthToken authToken, User targetUser, int limit, User lastFollower,
                            Handler messageHandler) {
        super(messageHandler, authToken, limit, lastFollower);
        this.targetUser = targetUser;
    }

    @Override
    protected Pair<List<User>, Boolean> getItems() {
        Pair<List<User>, Boolean> pageOfUsers = getFakeData().getPageOfUsers(getLastItem(), getLimit(), targetUser);
        return pageOfUsers;
    }

    @Override
    protected List<User> convertItemsToUsers(List<User> items) {
        return items;
    }

}
