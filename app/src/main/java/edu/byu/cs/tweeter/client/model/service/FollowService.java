package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.FollowHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetFollowersCountHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetFollowersHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetFollowingCountHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetFollowingHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.IsFollowerHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.UnfollowHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observers.BooleanServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observers.CountServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observers.PagedServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observers.SimpleNotificationServiceObserver;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowerCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingCountRequest;

public class FollowService extends AbstractServiceTemplate
{

    public interface FollowingObserver extends PagedServiceObserver<User> {}

    public interface FollowersObserver extends PagedServiceObserver<User> {}

    public interface FollowObserver extends SimpleNotificationServiceObserver {}

    public interface FollowersCountObserver extends CountServiceObserver {}

    public interface FollowingCountObserver extends CountServiceObserver {}

    public interface IsFollowerObserver extends BooleanServiceObserver {}

    public interface UnfollowObserver extends SimpleNotificationServiceObserver {}

    public void loadMoreItems(User user, int pageSize, User lastFollowee, FollowingObserver followingObserver) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastFollowee, new GetFollowingHandler(followingObserver));
        executeTask(getFollowingTask);
    }

    public void loadMoreItems(User user, int pageSize, User lastFollowee, FollowersObserver followersObserver) {
        GetFollowersTask getFollowersTask = new GetFollowersTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastFollowee, new GetFollowersHandler(followersObserver));
        executeTask(getFollowersTask);
    }

    public void isFollower(User selectedUser, IsFollowerObserver observer)
    {
        IsFollowerTask isFollowerTask = new IsFollowerTask(Cache.getInstance().getCurrUserAuthToken(),
                Cache.getInstance().getCurrUser(), selectedUser, new IsFollowerHandler(observer));
        executeTask(isFollowerTask);
    }

    public void unfollow(User selectedUser, UnfollowObserver observer)
    {
        UnfollowTask unfollowTask = new UnfollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new UnfollowHandler(observer));
        executeTask(unfollowTask);
    }

    public void follow(User selectedUser, FollowObserver observer)
    {
        FollowTask followTask = new FollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new FollowHandler(observer));
        executeTask(followTask);
    }

    public void getFollowersCount(User selectedUser, FollowersCountObserver observer)
    {
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(new FollowerCountRequest(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser), new GetFollowersCountHandler(observer));
        executeTask(followersCountTask);
    }

    public void getFollowingCount(User selectedUser, FollowingCountObserver observer)
    {
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(new FollowingCountRequest(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser), new GetFollowingCountHandler(observer));
        executeTask(followingCountTask);
    }
}
