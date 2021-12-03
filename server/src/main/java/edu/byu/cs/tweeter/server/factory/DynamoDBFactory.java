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
    private UserDynamoDAO userDynamoDAO = null;
    private AuthenticationDynamoDAO authenticationDynamoDAO = null;
    private FollowDynamoDAO followDynamoDAO = null;
    private StoryDynamoDAO storyDynamoDAO = null;
    private FeedDynamoDAO feedDynamoDAO = null;

    @Override
    public UserDAO getUserDAO() {
        if (userDynamoDAO == null) {
            userDynamoDAO = new UserDynamoDAO();
        }
        return userDynamoDAO;
    }

    @Override
    public AuthenticationDAO getAuthenticationDAO() {
        if (authenticationDynamoDAO == null) {
            authenticationDynamoDAO = new AuthenticationDynamoDAO();
        }
        return authenticationDynamoDAO;    }

    @Override
    public FollowDAO getFollowDAO() {
        if (followDynamoDAO == null) {
            followDynamoDAO = new FollowDynamoDAO();
        }
        return followDynamoDAO;
    }

    @Override
    public StoryDAO getStoryDAO() {
        if (storyDynamoDAO == null) {
            storyDynamoDAO = new StoryDynamoDAO();
        }
        return storyDynamoDAO;
    }

    @Override
    public FeedDAO getFeedDAO() {
        if (feedDynamoDAO == null) {
            feedDynamoDAO = new FeedDynamoDAO();
        }
        return feedDynamoDAO;
    }
}
