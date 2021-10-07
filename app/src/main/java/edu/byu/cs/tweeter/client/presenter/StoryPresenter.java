package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.view.interfaces.PagedViewInterface;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends StatusPagedPresenter<StoryPresenter.StoryView>
{
    public interface StoryView extends PagedViewInterface<Status> {}

    public StoryPresenter(StoryView view)
    {
        super(view);
    }

    @Override
    protected void useService(User user) {
        statusService.getStory(user, PAGE_SIZE, lastStatus, new StatusService.StoryObserver() {
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
