package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.view.interfaces.PagedViewInterface;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class UserPagedPresenter<T extends PagedViewInterface> extends PagedAbstractPresenter<T> {
    protected FollowService followService;
    protected User lastUser;

    public UserPagedPresenter(T view) {
        super(view);
    }

    protected void userSuccessHandler(List<User> users, boolean hasMorePages) {
        isLoading = false;
        lastUser = (users.size() > 0) ? users.get(users.size() - 1) : null;
        this.hasMorePages = hasMorePages;
        view.removeLoadingFooter();
        view.loadMoreItems(users);
    }

}
