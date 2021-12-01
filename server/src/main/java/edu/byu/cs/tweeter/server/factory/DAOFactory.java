package edu.byu.cs.tweeter.server.factory;

import edu.byu.cs.tweeter.server.dao.AuthenticationDAO;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public interface DAOFactory {
    UserDAO getUserDAO();
    AuthenticationDAO getAuthenticationDAO();
    FollowDAO getFollowDAO();
    StoryDAO getStoryDAO();
    FeedDAO getFeedDAO();
}
