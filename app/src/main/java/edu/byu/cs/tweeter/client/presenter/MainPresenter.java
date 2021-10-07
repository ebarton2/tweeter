package edu.byu.cs.tweeter.client.presenter;

import android.util.Log;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetFollowingCountHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PostStatusHandler;
import edu.byu.cs.tweeter.client.view.interfaces.ViewInterface;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter extends AbstractPresenter<MainPresenter.MainView>
{
    private static final String LOG_TAG = "MainActivity";
    private FollowService followService;
    private UserService userService;
    private StatusService statusService;

    public interface MainView extends ViewInterface
    {
        void logoutUser();
        void isFollowerButton(boolean isFollower);
        void setFollowerCount(int count);
        void setFollowingCount(int count);
        void updateFollowButton(boolean toRemove);
        void enableFollowButton(boolean enable);
    }

    public MainPresenter(MainView view)
    {
        super(view);
        followService = new FollowService();
        userService = new UserService();
        statusService = new StatusService();
    }

    public void unfollow(User selectedUser) //TODO: Unfinished
    {
        followService.unfollow(selectedUser, new FollowService.UnfollowObserver() {
            @Override
            public void handleSuccess()
            {
                updateSelectedUserFollowingAndFollowers(selectedUser);
                view.updateFollowButton(true);
            }

            @Override
            public void handleFailure(String message)
            {
                view.infoMessage(message);
            }
        });
        view.enableFollowButton(true);
        view.infoMessage("Removing " + selectedUser.getName() + "...");
    }

    public void follow(User selectedUser) //TODO: Unfinished
    {
        followService.follow(selectedUser, new FollowService.FollowObserver() {
            @Override
            public void handleSuccess() {
                updateSelectedUserFollowingAndFollowers(selectedUser);
                view.updateFollowButton(false);
            }

            @Override
            public void handleFailure(String message) {
                view.infoMessage(message);
            }
        });
        view.enableFollowButton(true);
        view.infoMessage("Adding " + selectedUser.getName() + "...");
    }

    public void isFollower(User selectedUser)
    {
        followService.isFollower(selectedUser, new FollowService.IsFollowerObserver() {
            @Override
            public void handleSuccess(boolean isFollower)
            {
                view.isFollowerButton(isFollower);
            }

            @Override
            public void handleFailure(String message) {
                view.infoMessage(message);
            }
        });
    }

    public void logoutUser()
    {
        view.infoMessage("Logging Out...");
        userService.logout(new UserService.LogoutObserver() {
            @Override
            public void handleSuccess() {
                view.clearInfoMessage();
                //Clear user data (cached data).
                Cache.getInstance().clearCache();
                view.logoutUser();
            }
            @Override
            public void handleFailure(String message) {
                view.infoMessage(message);
            }
        });
    }

    public void updateSelectedUserFollowingAndFollowers(User selectedUser) { //TODO: Goes into a service
        followService.getFollowersCount(selectedUser, new FollowService.FollowersCountObserver() {
            @Override
            public void handleSuccess(int count) {
                view.setFollowerCount(count);
            }

            @Override
            public void handleFailure(String message) {
                view.infoMessage(message);
            }
        });
        followService.getFollowingCount(selectedUser, new FollowService.FollowingCountObserver() {
            @Override
            public void handleSuccess(int count) {
                view.setFollowingCount(count);
            }

            @Override
            public void handleFailure(String message) {
                view.infoMessage(message);
            }
        });
    }

    public void postStatus(String post)
    {
        view.infoMessage("Posting Status...");
        try {
            Status newStatus = new Status(post, Cache.getInstance().getCurrUser(), getFormattedDateTime(), parseURLs(post), parseMentions(post));

            statusService.postStatus(newStatus, new StatusService.PostStatusObserver(){
                @Override
                public void handleSuccess() {
                    view.clearInfoMessage();
                    view.infoMessage("Successfully Posted!");
                }

                @Override
                public void handleFailure(String message) {
                    view.infoMessage(message);
                }
            });


        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            view.infoMessage("Failed to post the status because of exception: " + ex.getMessage());
        }
    }

    private String getFormattedDateTime() throws ParseException {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }

    private List<String> parseURLs(String post) throws MalformedURLException {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }

    private List<String> parseMentions(String post) { //TODO: Goes into a service
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }

    private int findUrlEndIndex(String word) { //TODO: Goes into a service
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }

}
