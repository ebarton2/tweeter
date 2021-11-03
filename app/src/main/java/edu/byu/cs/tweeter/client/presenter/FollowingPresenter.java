package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.view.interfaces.PagedViewInterface;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter extends UserPagedPresenter<FollowingPresenter.FollowingView>
{
    public interface FollowingView extends PagedViewInterface<User> {}


    public FollowingPresenter(FollowingView view)
    {
        super(view);
        followService = new FollowService();
    }

    @Override
    protected void useService(User user) {
        followService.loadMoreFollowing(user, PAGE_SIZE, lastUser, new FollowService.FollowingObserver() {
            @Override
            public void handleSuccess(List<User> followees, boolean hasMorePages) {
                userSuccessHandler(followees, hasMorePages);
            }

            @Override
            public void handleFailure(String message) {
                pagedFailure(message);
            }
        });
    }
}
