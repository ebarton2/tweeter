package edu.byu.cs.tweeter.client.presenter;

import java.net.MalformedURLException;
import java.util.List;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.view.interfaces.PagedViewInterface;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedAbstractPresenter<T extends PagedViewInterface> extends AbstractPresenter<T> {
    protected UserService userService;
    protected boolean isLoading = false;
    protected boolean hasMorePages = true;

    protected static final int PAGE_SIZE = 10;

    public PagedAbstractPresenter(T view) {
        super(view);
        userService = new UserService();
    }

    public boolean isLoading() { return isLoading; }

    public void getUser(String alias)
    {
        view.infoMessage("Getting user's profile...");
        userService.getUser(alias, new UserService.GetUserObserver()
        {
            @Override
            public void handleSuccess(User user)
            {
                view.clearInfoMessage();
                view.navigateToUser(user);
            }

            @Override
            public void handleFailure(String message)
            {
                view.clearInfoMessage();
                view.infoMessage(message);
            }
        });
    }

    public void loadMoreItems(User user) throws MalformedURLException {
        if (!isLoading && hasMorePages) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.addLoadingFooter();
            useService(user);
        }
    }

    protected abstract void useService(User user);

    protected void pagedFailure(String message)
    {
        isLoading = false;
        view.removeLoadingFooter();
        view.infoMessage(message);
    }
}
