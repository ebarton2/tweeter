package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter
{

    private static final int PAGE_SIZE = 10;

    public interface StoryView
    {
        void loadMoreItems(List<Status> statuses);
        void infoMessage(String message);
        void clearInfoMessage();
        void addLoadingFooter();
        void removeLoadingFooter();
        void navigateToUser(User user);
    }

    private StoryView view;
    private UserService userService;
    private StatusService statusService;

    private Status lastStatus;
    private boolean hasMorePages = true;
    private boolean isLoading = false;

    public StoryPresenter(StoryView view)
    {
        this.view = view;
        userService = new UserService();
        statusService = new StatusService();
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void loadMoreItems(User user)
    {
        if (!isLoading && hasMorePages) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.addLoadingFooter();
            statusService.getStory(user, PAGE_SIZE, lastStatus, new StatusService.StoryObserver() {
                @Override
                public void handleSuccess(List<Status> statuses, boolean hasMorePages) {
                    isLoading = false;
                    lastStatus = (statuses.size() > 0 ? statuses.get(statuses.size() - 1) : null);
                    StoryPresenter.this.hasMorePages = hasMorePages;
                    view.removeLoadingFooter();
                    view.loadMoreItems(statuses);
                }

                @Override
                public void handleFailure(String message) {
                    isLoading = false;
                    view.removeLoadingFooter();
                    view.infoMessage(message);
                }
            });


        }
    }

    public void getUser(String alias)
    {
        view.infoMessage("Getting user's profile...");
        userService.getUser(alias, new UserService.GetUserObserver() {
            @Override
            public void handleSuccess(User user) {
                view.clearInfoMessage();
                view.navigateToUser(user);
            }

            @Override
            public void handleFailure(String message) {
                view.clearInfoMessage();
                view.infoMessage(message);
            }
        });

    }
}
