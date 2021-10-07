package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter
{
    private static final int PAGE_SIZE = 10;

    public interface FollowersView
    {
        void displayMoreItems(List<User> followees, boolean hasMorePages);
        void addLoadingFooter();
        void removeLoadingFooter();
        void navigateToUser(User user);
        void displayInfoMessage(String message);
        void clearInfoMessage();
    }

    private FollowersView view;
    private FollowService followService;
    private UserService userService;


    private User lastFollowee;
    private boolean hasMorePages = true;
    private boolean isLoading = false;

    public boolean isLoading() {
        return isLoading;
    }

    public FollowersPresenter(FollowersView view)
    {
        this.view = view;
        followService = new FollowService();
        userService = new UserService();
    }

    public void loadMoreItems(User user)
    {
        if (!isLoading && hasMorePages) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.addLoadingFooter();

            followService.loadMoreItems(user, PAGE_SIZE, lastFollowee, new FollowService.FollowersObserver() {
                @Override
                public void handleSuccess(List followers, boolean hasMorePages) {
                    isLoading = false;
                    FollowersPresenter.this.lastFollowee = (followers.size() > 0) ? (User) followers.get(followers.size() - 1) : null;
                    FollowersPresenter.this.hasMorePages = hasMorePages;


                    view.removeLoadingFooter();
                    view.displayMoreItems(followers, hasMorePages);
                }

                @Override
                public void handleFailure(String message) {
                    isLoading = false;
                    view.removeLoadingFooter();
                    view.displayInfoMessage(message);
                }
            });
        }
    }

    public void getUser(String alias) {
        view.displayInfoMessage("Getting user's profile...");
        userService.getUser(alias, new UserService.GetUserObserver() {
            @Override
            public void handleSuccess(User user) {
                view.clearInfoMessage();
                view.navigateToUser(user);
            }

            @Override
            public void handleFailure(String message) {
                view.clearInfoMessage();
                view.displayInfoMessage(message);
            }
        });
    }

}
