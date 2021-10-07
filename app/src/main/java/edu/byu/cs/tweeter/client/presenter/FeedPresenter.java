package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.view.interfaces.PagedViewInterface;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter extends StatusPagedPresenter<FeedPresenter.FeedView>
{
    public interface FeedView extends PagedViewInterface<Status> {}

    public FeedPresenter(FeedView view)
    {
        super(view);
    }

    @Override
    protected void useService(User user) {
        statusService.getFeed(user, PAGE_SIZE, lastStatus, new StatusService.FeedObserver() {
            @Override
            public void handleSuccess(List<Status> statuses, boolean hasMorePages) {
                statusSuccessHandler(statuses, hasMorePages);
            }

            @Override
            public void handleFailure(String message) {
                pagedFailure(message);
            }
        });
    }
}