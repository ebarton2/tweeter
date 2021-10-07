package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.view.interfaces.PagedViewInterface;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter extends UserPagedPresenter<FollowersPresenter.FollowersView>
{
    public interface FollowersView extends PagedViewInterface<User> {}

    public FollowersPresenter(FollowersView view)
    {
        super(view);
        followService = new FollowService();
    }

    @Override
    protected void useService(User user) {
        followService.loadMoreItems(user, PAGE_SIZE, lastUser, new FollowService.FollowersObserver() {
            @Override
            public void handleSuccess(List<User> followers, boolean hasMorePages) {
                userSuccessHandler(followers, hasMorePages);
            }

            @Override
            public void handleFailure(String message) {
                pagedFailure(message);
            }
        });
    }
}