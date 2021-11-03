package edu.byu.cs.tweeter.server.service;

import java.sql.PreparedStatement;

import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.StatusDAO;

public class StatusService {

    StatusDAO getStatusDAO() {
        return new StatusDAO();
    }

    public FeedResponse getFeed(FeedRequest request) {
        return getStatusDAO().getFeed(request);
    }

    public StoryResponse getStory(StoryRequest request) { return getStatusDAO().getStory(request); }

    public PostStatusResponse getPostStatus(PostStatusRequest request) { return getStatusDAO().getPostStatus(request); }
}
