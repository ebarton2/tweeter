package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter {

    private static final int PAGE_SIZE = 10;

    public interface FollowingView {
        void displayErrorMessage(String message);
        void displayMoreItems(List<User> followees, boolean hasMorePages);
        void addLoadingFooter();
        void removeLoadingFooter();
        void navigateToUser(User user);
        void displayInfoMessage(String message);
        void clearInfoMessage();
    }

    private FollowingView view;
    private FollowService followService;
    private UserService userService;


    private User lastFollowee;
    private boolean hasMorePages = true;
    private boolean isLoading = false;

    public boolean isLoading() {
        return isLoading;
    }

    public FollowingPresenter(FollowingView view) {
        this.view = view;
        followService = new FollowService();
        userService = new UserService();
    }


    public void loadMoreItems(User user) {
        if (!isLoading && hasMorePages) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.addLoadingFooter();

            followService.loadMoreItems(user, PAGE_SIZE, lastFollowee, new FollowService.FollowingObserver() {
                @Override
                public void handleSuccess(List<User> followees, boolean hasMorePages) {
                    FollowingPresenter.this.lastFollowee = (followees.size() > 0) ? followees.get(followees.size() - 1) : null;
                    FollowingPresenter.this.hasMorePages = hasMorePages;

                    isLoading = false;
                    view.removeLoadingFooter();
                    view.displayMoreItems(followees, hasMorePages);
                }

                @Override
                public void handleFailure(String message) {
                    isLoading = false;
                    view.removeLoadingFooter();
                    view.displayErrorMessage(message);
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
                view.displayErrorMessage(message);
            }
        });
    }
}
