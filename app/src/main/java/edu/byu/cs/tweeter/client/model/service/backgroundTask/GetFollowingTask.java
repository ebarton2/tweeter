package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of other users being followed by a specified user.
 */
public class GetFollowingTask extends PagedTask<User>{
    private static final String LOG_TAG = "GetFollowingTask";
    private static final String URL_PATH = "/getfollowing";

    /**
     * The user whose following is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    private User targetUser;

    private FollowingRequest request;

    //AuthToken authToken, User targetUser, int limit, User lastFollowee,
    public GetFollowingTask(AuthToken authToken, User targetUser, int limit, User lastFollowee,
                            Handler messageHandler) {
        super(messageHandler, authToken, limit, lastFollowee);
        if (lastFollowee == null){
            this.request = new FollowingRequest(authToken, targetUser.getAlias(), limit, null);
        } else {
            this.request = new FollowingRequest(authToken, targetUser.getAlias(), limit, lastFollowee.getAlias());
        }

        this.targetUser = targetUser;
    }

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
            FollowingResponse response = getServerFacade().getFollowees(request, URL_PATH);
            if(response.isSuccess()) {
                items = response.getFollowees();
                hasMorePages = response.getHasMorePages();

                loadImages(items);
            } else {
                throw new IOException(response.getMessage());
            }

    }

    @Override
    protected Pair<List<User>, Boolean> getItems() {
        return getFakeData().getPageOfUsers((User) getLastItem(), getLimit(), targetUser);
    }

    @Override
    protected List<User> convertItemsToUsers(List<User> items) {
        return items;
    }
}
