package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's story.
 */
public class GetStoryTask extends PagedTask<Status> {
    private static final String LOG_TAG = "GetStoryTask";
    private static final String URL_PATH = "/getstory";

    /**
     * The user whose story is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    private User targetUser;

    private StoryRequest request;

    public GetStoryTask(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                        Handler messageHandler) {
        super(messageHandler, authToken, limit, lastStatus);
        if (lastStatus == null) {
            this.request = new StoryRequest(authToken, targetUser.getAlias(), limit, null);
        } else {
            this.request = new StoryRequest(authToken, targetUser.getAlias(), limit, lastStatus);
        }
        this.targetUser = targetUser;
    }

    @Override
    protected void runTask() throws IOException {
        try {
            StoryResponse response = getServerFacade().getStory(request, URL_PATH);
            if(response.isSuccess()) {
                items = response.getStatuses();
                hasMorePages = response.getHasMorePages();

                loadImages(items);
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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