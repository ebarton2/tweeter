package edu.byu.cs.tweeter.server.dao;

import java.text.ParseException;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class FeedDynamoDAO extends DynamoStatusDAO implements FeedDAO {

    @Override
    public List<Status> getFeed(String alias, Status lastStatus, int limit) throws ParseException {
        return getPage(alias, lastStatus, limit, FEED_TABLE);
    }
}
