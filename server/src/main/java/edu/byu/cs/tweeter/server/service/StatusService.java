package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.sqs.model.MessageAttributeValue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.factory.DAOFactory;
import edu.byu.cs.tweeter.server.util.FakeData;

public class StatusService extends AbstractService {

    public StatusService(DAOFactory factory) {
        super(factory);
    }

    public FeedResponse getFeed(FeedRequest request) {
        try {
            if(getAuthenticationDAO().isAuthorized(request.getAuthToken().getToken())) {
                boolean success = true;
                if (request.getLimit() <= 0) throw new AssertionError("Improper limit");
                if (request.getUserAlias() == null) throw new AssertionError("null alias");

                List<Status> statuses = getFeedDAO().getFeed(request.getUserAlias(),
                        request.getLastStatus(), request.getLimit());
                boolean hasMorePages = false;

                if (statuses.size() == 10) {
                    hasMorePages = true;
                }
                return new FeedResponse(statuses, success, hasMorePages);
            }
            return new FeedResponse(false, "Unauthorized", false);
        } catch (AssertionError | Exception e) {
            e.printStackTrace();
            return new FeedResponse(false, e.getMessage(), false);
        }
    }

    public StoryResponse getStory(StoryRequest request) {
        try {
            if(getAuthenticationDAO().isAuthorized(request.getAuthToken().getToken())) {
                boolean success = true;
                if (request.getLimit() <= 0) throw new AssertionError("Improper limit");
                if (request.getUserAlias() == null) throw new AssertionError("null alias");

                List<Status> statuses = getStoryDAO().getStory(request.getUserAlias(),
                        request.getLastStatus(), request.getLimit());
                boolean hasMorePages = false;
                if (statuses.size() == 10) {
                    hasMorePages = true;
                }
                return new StoryResponse(statuses, success, hasMorePages);
            }
            return new StoryResponse(false, "Unauthorized", false);
        } catch (AssertionError | Exception e) {
            e.printStackTrace();
            return new StoryResponse(false, e.getMessage(), false);
        }
    }

    //Actually posts the status to the story and feed, needs to use both StoryDAO and FeedDAO
    //Here is where we will do most of our work for M4B
    public PostStatusResponse getPostStatus(PostStatusRequest request) {
        try {
            if(getAuthenticationDAO().isAuthorized(request.getAuthToken().getToken())) {
                String alias = getAuthenticationDAO().getAliasFromToken(request.getAuthToken().getToken());
                Item item = getUserDAO().getUserItem(alias);
                int limit = item.getInt("followers_count");

                //List<String> people = getFollowingDAO().getFollowers(alias, null, limit);

                // This one line might need to be split into two, one for the story and one for the feed.
                boolean posted = getStoryDAO().getPostStatus(alias, request.getStatus(), limit);


                return new PostStatusResponse(posted);
            }
            return new PostStatusResponse(false, "Unauthorized");
        } catch (Exception e) {
            e.printStackTrace();
            return new PostStatusResponse(false, e.getMessage());
        }
    }

    public void updateFeedBatch(String alias, long epoch, String statusJSON, String peopleList) {
        String[] peopleArray = peopleList.split(",");
        getFeedDAO().updateFeedTableBatch(alias, epoch, statusJSON, peopleArray);
    }
}
