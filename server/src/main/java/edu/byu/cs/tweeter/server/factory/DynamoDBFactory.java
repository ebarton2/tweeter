package edu.byu.cs.tweeter.server.factory;

import edu.byu.cs.tweeter.server.dao.AuthenticationDAO;
import edu.byu.cs.tweeter.server.dao.AuthenticationDynamoDAO;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.FeedDynamoDAO;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.FollowDynamoDAO;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.dao.StoryDynamoDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.dao.UserDynamoDAO;

public class DynamoDBFactory implements DAOFactory {
    @Override
    public UserDAO getUserDAO() {
        return new UserDynamoDAO();
    }

    @Override
    public AuthenticationDAO getAuthenticationDAO() {
        return new AuthenticationDynamoDAO();
    }

    @Override
    public FollowDAO getFollowDAO() {
        return new FollowDynamoDAO();
    }

    @Override
    public StoryDAO getStoryDAO() {
        return new StoryDynamoDAO();
    }

    @Override
    public FeedDAO getFeedDAO() {
        return new FeedDynamoDAO();
    }
}
