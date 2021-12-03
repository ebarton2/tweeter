package edu.byu.cs.tweeter.server.dao;

import java.text.ParseException;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;

public interface FeedDAO {
    List<Status> getFeed(String alias, Status lastStatus, int limit) throws ParseException;
    void updateFeedTableBatch(String targetAlias, long epoch, String statusJSON, String[] people);
}
