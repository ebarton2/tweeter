package edu.byu.cs.tweeter.server.service;

import org.hamcrest.core.Is;
import org.junit.AssumptionViolatedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.FollowerCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.FollowerCountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingCountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;
import edu.byu.cs.tweeter.server.dao.AuthenticationDAO;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.FollowDynamoDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.factory.DAOFactory;
import edu.byu.cs.tweeter.server.util.FakeData;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowService extends AbstractService {

    public FollowService(DAOFactory factory) {
        super(factory);
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link FollowDynamoDAO} to
     * get the followees.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request) {
        try {
            if(getAuthenticationDAO().isAuthorized(request.getAuthToken().getToken())) {
                boolean success = true;
                checkRequest(request.getLimit(), request.getFollowerAlias());

                List<String> aliases = getFollowingDAO().getFollowees(request.getFollowerAlias(), request.getLastFolloweeAlias(), request.getLimit());
                List<User> responseFollowees = fillResponse(aliases, request.getLimit());

                boolean hasMorePages = false;
                if (aliases.size() == 10) {
                    hasMorePages = true;
                }
                return new FollowingResponse(responseFollowees, success, hasMorePages);
            }
            return new FollowingResponse("Unauthorized to get followees");
        } catch (AssertionError | Exception e) {
            return new FollowingResponse(e.getMessage());
        }
    }

    public FollowersResponse getFollowers(FollowersRequest request) {
        try {
            if(getAuthenticationDAO().isAuthorized(request.getAuthToken().getToken())) {
                boolean success = true;
                checkRequest(request.getLimit(), request.getFolloweeAlias());

                List<String> aliases = getFollowingDAO().getFollowers(request.getFolloweeAlias(), request.getLastFollowerAlias(), request.getLimit());
                List<User> responseFollowers = fillResponse(aliases ,request.getLimit());
                boolean hasMorePages = false;
                if (aliases.size() == 10) {
                    hasMorePages = true;
                }
                return new FollowersResponse(responseFollowers, success, hasMorePages);
            }
            return new FollowersResponse("Unauthorized access");
        } catch (AssertionError | Exception e) {
            return new FollowersResponse(e.getMessage());
        }
    }

    private List<User> fillResponse(List<String> aliases, int limit) {
        List<User> responseList = new ArrayList<>(limit);
        for (int i = 0; i < aliases.size(); ++i) {
            responseList.add(getUserDAO().getUser(aliases.get(i)));
        }
        return responseList;
    }

    private void checkRequest(int limit, String alias) throws AssertionError {
        if (limit <= 0) throw new AssertionError("Improper limit");
        if (alias == null) throw new AssertionError("null alias");
    }

    public FollowingCountResponse getFollowingCount(FollowingCountRequest request) {
        try {
            if (getAuthenticationDAO().isAuthorized(request.getAuthToken().getToken())) {
                assert request.getUser() != null;
                User follower = request.getUser();
                int following_count = getUserDAO().getFolloweeCount(follower);
                if (follower != null) return new FollowingCountResponse(true, following_count);
                else return new FollowingCountResponse(false, "Unable to find user", 0);
            }
            return new FollowingCountResponse(false, "Unauthorized access", 0);
        } catch (Exception e) {
            e.printStackTrace();
            return new FollowingCountResponse(false, e.getMessage(), 0);
        }
    }

    public FollowerCountResponse getFollowerCount(FollowerCountRequest request) {
        try {
            if (getAuthenticationDAO().isAuthorized(request.getAuthToken().getToken())) {
                assert request.getUser() != null;
                User follower = request.getUser();
                int follower_count = getUserDAO().getFollowerCount(follower);
                if (follower != null) return new FollowerCountResponse(true, follower_count);
                else return new FollowerCountResponse(false, "Unable to find user", 0);
            }
            return new FollowerCountResponse(false, "Unauthorized", 0);
        } catch (Exception e) {
            e.printStackTrace();
            return new FollowerCountResponse(false, e.getMessage(), 0);
        }
    }

    public IsFollowerResponse getIsFollower(IsFollowerRequest request) {
        //TODO: Check for authToken
        try {
            if(getAuthenticationDAO().isAuthorized(request.getAuthToken().getToken())) {
                if (request.getFollowee() == null || request.getFollower() == null) {
                    return new IsFollowerResponse(false, "Follower or Followee is null");
                }
                boolean isFollowing = getFollowingDAO().getIsFollower(request.getFollower().getAlias(), request.getFollowee().getAlias());
                System.out.println("Are we following?: " + isFollowing);
                return new IsFollowerResponse(true, isFollowing);
            }
            return new IsFollowerResponse(false, "Unauthorized");
        } catch (AssertionError | Exception e) {
            return new IsFollowerResponse(false, e.getMessage());
        }
    }

    public FollowResponse getFollow(FollowRequest request) {
        try {
            if(getAuthenticationDAO().isAuthorized(request.getAuthToken().getToken())) {
                if (request.getFollowee() == null) {
                    return new FollowResponse(false, "Unable to connect User or Follower");
                }
                String userAlias = getAuthenticationDAO().getAliasFromToken(request.getAuthToken().getToken());
                boolean isFollowed = getFollowingDAO().getFollow(userAlias, request.getFollowee().getAlias());
                updateFollows(userAlias, request.getFollowee().getAlias(), true);
                return new FollowResponse(true);
            }
            return new FollowResponse(false, "Unauthorized");
        } catch (AssertionError | Exception e) {
            return new FollowResponse(false, e.getMessage());
        }
    }

    public UnfollowResponse getUnfollow(UnfollowRequest request) {
        try {
            if(getAuthenticationDAO().isAuthorized(request.getAuthToken().getToken())) {
                if (request.getFollower() == null) {
                    return new UnfollowResponse(false, "Unable to connect User or Follower");
                }
                String userAlias = getAuthenticationDAO().getAliasFromToken(request.getAuthToken().getToken());
                boolean isFollowed = getFollowingDAO().getUnfollow(userAlias, request.getFollower().getAlias());
                updateFollows(userAlias, request.getFollower().getAlias(), false);
                return new UnfollowResponse(true);
            }
            return new UnfollowResponse(false, "Unauthorized");
        } catch (AssertionError | Exception e) {
            return new UnfollowResponse(false, e.getMessage());
        }
    }

    private void updateFollows(String alias, String followAlias, boolean increase) throws Exception {
        getUserDAO().updateFolloweeCount(alias, increase);
        getUserDAO().updateFollowerCount(followAlias, increase);
    }
}
