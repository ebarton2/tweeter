package edu.byu.cs.tweeter.server.dao;

import java.text.ParseException;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class StoryDynamoDAO extends DynamoStatusDAO implements StoryDAO {

    @Override
    public List<Status> getStory(String alias, Status lastStatus, int limit) throws ParseException {
        return getPage(alias, lastStatus, limit, STORY_TABLE);
    }

}
