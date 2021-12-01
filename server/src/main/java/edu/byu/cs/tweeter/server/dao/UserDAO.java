package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public interface UserDAO {
    User getUser(String alias);
    User register(String username, String secPassword, String salt, String firstName, String lastName, String image);
    User login(String username, String password);
    String hashPassword(String password, String salt);
    int getFolloweeCount(User follower);
    int getFollowerCount(User follower);
    void updateFolloweeCount(String alias, boolean increase) throws Exception;
    void updateFollowerCount(String alias, boolean increase) throws Exception;
    Item getUserItem(String alias);
}
