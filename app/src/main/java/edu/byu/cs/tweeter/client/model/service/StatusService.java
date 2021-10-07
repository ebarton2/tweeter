package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetFeedHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetStoryHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PostStatusHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observers.PagedServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observers.SimpleNotificationServiceObserver;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService extends AbstractServiceTemplate
{

    public interface FeedObserver extends PagedServiceObserver<Status> {}

    public interface StoryObserver extends PagedServiceObserver<Status> {}

    public interface PostStatusObserver extends SimpleNotificationServiceObserver {}

    public void getFeed(User user, int pageSize, Status lastStatus, FeedObserver observer)
    {
        GetFeedTask getFeedTask = new GetFeedTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastStatus, new GetFeedHandler(observer));
        executeTask(getFeedTask);
    }

    public void getStory(User user, int pageSize, Status lastStatus, StoryObserver observer)
    {
        GetStoryTask getStoryTask = new GetStoryTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastStatus, new GetStoryHandler(observer));
        executeTask(getStoryTask);
    }

    public void postStatus(Status newStatus, PostStatusObserver postStatusObserver)
    {
        PostStatusTask statusTask = new PostStatusTask(Cache.getInstance().getCurrUserAuthToken(),
                newStatus, new PostStatusHandler(postStatusObserver));
        executeTask(statusTask);
    }
}
