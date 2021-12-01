package edu.byu.cs.tweeter.server.dao;

import java.util.List;

public interface FollowDAO {
    List<String> getFollowees(String alias, String lastFollowee, int limit) throws Exception;
    List<String> getFollowers(String userAlias, String lastFollowee, int limit) throws Exception;
    boolean getIsFollower(String alias, String potential) throws Exception;
    boolean getFollow(String alias, String toFollow);
    boolean getUnfollow(String alias, String toUnfollow) throws Exception;
}
