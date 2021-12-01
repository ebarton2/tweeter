package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of followers.
 */
public class GetFollowersTask extends PagedTask<User> {
    private static final String LOG_TAG = "GetFollowersTask";
    private static final String URL_PATH = "/getfollowers";


    /**
     * The user whose followers are being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    private User targetUser;

    private FollowersRequest request;

    public GetFollowersTask(AuthToken authToken, User targetUser, int limit, User lastFollower,
                            Handler messageHandler) {
        super(messageHandler, authToken, limit, lastFollower);
        if (lastFollower == null){
            this.request = new FollowersRequest(authToken, targetUser.getAlias(), limit, null);
        } else {
            this.request = new FollowersRequest(authToken, targetUser.getAlias(), limit, lastFollower.getAlias());
        }
        this.targetUser = targetUser;
    }

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        FollowersResponse response = getServerFacade().getFollowers(request, URL_PATH);
        if(response.isSuccess()) {
            items = response.getFollowers();
            hasMorePages = response.getHasMorePages();

            loadImages(items);
        } else {
            throw new IOException(response.getMessage());
        }
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
