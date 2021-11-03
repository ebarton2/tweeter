package edu.byu.cs.tweeter.server.dao;

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
import edu.byu.cs.tweeter.server.util.FakeData;

/**
 * A DAO for accessing 'following' data from the database.
 */
public class FollowDAO {

    /**
     * Gets the count of users from the database that the user specified is following. The
     * current implementation uses generated data and doesn't actually access a database.
     *
     * @param follower the User whose count of how many following is desired.
     * @return said count.
     */
    public Integer getFolloweeCount(User follower) {
        // TODO: uses the dummy data.  Replace with a real implementation.
        assert follower != null;
        return getDummyFollowees().size();
    }

    public Integer getFollowerCount(User follower) {
        assert follower != null;
        return getDummyFollowers().size();
    }

    /**
     * Gets the users from the database that the user specified in the request is following. Uses
     * information in the request object to limit the number of followees returned and to return the
     * next set of followees after any that were returned in a previous request. The current
     * implementation returns generated data and doesn't actually access a database.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        boolean success = true;
        assert request.getLimit() > 0;
        if (request.getLimit() <= 0) success = false;
        assert request.getFollowerAlias() != null;
        if (request.getFollowerAlias() == null) success = false;

        List<User> allFollowees = getDummyFollowees();
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowees != null) {
                int followeesIndex = getFolloweesStartingIndex(request.getLastFolloweeAlias(), allFollowees);

                for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                    responseFollowees.add(allFollowees.get(followeesIndex));
                }

                hasMorePages = followeesIndex < allFollowees.size();
            }
        }

        return new FollowingResponse(responseFollowees, success, hasMorePages);
    }

    public FollowersResponse getFollowers(FollowersRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        boolean success = true;
        assert request.getLimit() > 0;
        if (request.getLimit() <= 0) success = false;
        assert request.getFolloweeAlias() != null;
        if (request.getFolloweeAlias() == null) success = false;

        List<User> allFollowers = getDummyFollowers();
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowers != null) {
                int followersIndex = getFollowersStartingIndex(request.getLastFollowerAlias(), allFollowers);

                for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
                    responseFollowees.add(allFollowers.get(followersIndex));
                }

                hasMorePages = followersIndex < allFollowers.size();
            }
        }

        return new FollowersResponse(responseFollowees, success, hasMorePages);
    }



    public FollowingCountResponse getFollowingCount(FollowingCountRequest request) {
        assert request.getUser() != null;
        User follower = request.getUser();
        FollowingCountResponse response;

        if (follower != null) response = new FollowingCountResponse(true, getDummyFollowers().size());
        else response = new FollowingCountResponse(false, "Unable to find user", 0);

        return response;
    }

    public FollowerCountResponse getFollowerCount(FollowerCountRequest request) {
        assert request.getUser() != null;
        User follower = request.getUser();
        FollowerCountResponse response;

        if (follower != null) response = new FollowerCountResponse(true, getDummyFollowers().size());
        else response = new FollowerCountResponse(false, "Unable to find user", 0);

        return response;
    }

    public IsFollowerResponse getIsFollower(IsFollowerRequest request) {
        IsFollowerResponse response;
        if (request.getFollowee() == null || request.getFollower() == null) {
            response = new IsFollowerResponse(false, "Unable to connect User or Follower");

        } else {
            //TODO: Note that this is currently random
            response = new IsFollowerResponse(true, new Random().nextInt() > 0);
        }
        return response;
    }

    /**
     * Determines the index for the first followee in the specified 'allFollowees' list that should
     * be returned in the current request. This will be the index of the next followee after the
     * specified 'lastFollowee'.
     *
     * @param lastFolloweeAlias the alias of the last followee that was returned in the previous
     *                          request or null if there was no previous request.
     * @param allFollowees the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFolloweesStartingIndex(String lastFolloweeAlias, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFolloweeAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFolloweeAlias.equals(allFollowees.get(i).getAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                    break;
                }
            }
        }

        return followeesIndex;
    }

    private int getFollowersStartingIndex(String lastFollowerAlias, List<User> allFollowers) {
        int followeesIndex = 0;

        if(lastFollowerAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowers.size(); i++) {
                if(lastFollowerAlias.equals(allFollowers.get(i).getAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                    break;
                }
            }
        }

        return followeesIndex;
    }

    public FollowResponse getFollow(FollowRequest request) {
        FollowResponse response;
        if (request.getFollowee() == null) {
            response = new FollowResponse(false, "Unable to connect User or Follower");

        } else {
            //TODO: Note that this is currently random
            response = new FollowResponse(true);
        }
        return response;
    }

    public UnfollowResponse getUnfollow(UnfollowRequest request) {
        UnfollowResponse response;
        if (request.getFollower() == null) {
            response = new UnfollowResponse(false, "Unable to connect User or Follower");

        } else {
            //TODO: Note that this is currently random
            response = new UnfollowResponse(true);
        }
        return response;

    }

    /**
     * Returns the list of dummy followee data. This is written as a separate method to allow
     * mocking of the followees.
     *
     * @return the followees.
     */
    List<User> getDummyFollowees() {
        return getFakeData().getFakeUsers();
    }

    List<User> getDummyFollowers() { return getFakeData().getFakeUsers(); }

    /**
     * Returns the {@link FakeData} object used to generate dummy followees.
     * This is written as a separate method to allow mocking of the {@link FakeData}.
     *
     * @return a {@link FakeData} instance.
     */
    FakeData getFakeData() {
        return new FakeData();
    }


}
