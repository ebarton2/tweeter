package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.AuthenticationDAO;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.factory.DAOFactory;

public class AbstractService {
    protected DAOFactory factory;

    public AbstractService(DAOFactory factory) { this.factory = factory; }

    protected UserDAO getUserDAO() { return factory.getUserDAO(); }

    protected AuthenticationDAO getAuthenticationDAO() { return factory.getAuthenticationDAO(); }

    protected FollowDAO getFollowingDAO() { return factory.getFollowDAO(); }

    protected FeedDAO getFeedDAO() { return factory.getFeedDAO(); }

    protected StoryDAO getStoryDAO() { return factory.getStoryDAO(); }
}
