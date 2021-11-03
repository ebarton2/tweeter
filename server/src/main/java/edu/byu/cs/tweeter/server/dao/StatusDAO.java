package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.util.FakeData;

public class StatusDAO {

    public FeedResponse getFeed(FeedRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        boolean success = true;
        assert request.getLimit() > 0;
        if (request.getLimit() <= 0) success = false;
        assert request.getUserAlias() != null;
        if (request.getUserAlias() == null) success = false;

        List<Status> allStatuses = getDummyFeed();
        List<Status> responseFeed = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allStatuses != null) {
                int feedIndex = getFeedStartingIndex(request.getLastStatus(), allStatuses);

                for(int limitCounter = 0; feedIndex < allStatuses.size() && limitCounter < request.getLimit(); feedIndex++, limitCounter++) {
                    responseFeed.add(allStatuses.get(feedIndex));
                }

                hasMorePages = feedIndex < allStatuses.size();
            }
        }

        return new FeedResponse(responseFeed, success, hasMorePages);
    }

    public StoryResponse getStory(StoryRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        boolean success = true;
        assert request.getLimit() > 0;
        if (request.getLimit() <= 0) success = false;
        assert request.getUserAlias() != null;
        if (request.getUserAlias() == null) success = false;

        List<Status> allStatuses = getDummyFeed();
        List<Status> responseStory = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allStatuses != null) {
                int storyIndex = getFeedStartingIndex(request.getLastStatus(), allStatuses);

                for(int limitCounter = 0; storyIndex < allStatuses.size() && limitCounter < request.getLimit(); storyIndex++, limitCounter++) {
                    responseStory.add(allStatuses.get(storyIndex));
                }

                hasMorePages = storyIndex < allStatuses.size();
            }
        }

        return new StoryResponse(responseStory, success, hasMorePages);
    }

    public PostStatusResponse getPostStatus(PostStatusRequest request) {
        if (request.getStatus() != null) {
            return new PostStatusResponse(true);
        } else {
            return new PostStatusResponse(false, "Unable to find Status to post");
        }
    }

    private int getFeedStartingIndex(Status lastFeedStatus, List<Status> allStatuses) {
        int statusIndex = 0;

        if(lastFeedStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastFeedStatus.getPost().equals(allStatuses.get(i).getPost())
                        && lastFeedStatus.getDate().equals(allStatuses.get(i).getDate())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusIndex = i + 1;
                    break;
                }
            }
        }

        return statusIndex;
    }

    private int getStoryStartingIndex(Status lastStoryStatus, List<Status> allStatuses) {
        int statusIndex = 0;

        if(lastStoryStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStoryStatus.getPost().equals(allStatuses.get(i).getPost())
                        && lastStoryStatus.getDate().equals(allStatuses.get(i).getDate())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusIndex = i + 1;
                    break;
                }
            }
        }

        return statusIndex;
    }


    List<Status> getDummyFeed() {
        return getFakeData().getFakeStatuses();
    }

    FakeData getFakeData() {
        return new FakeData();
    }


}
