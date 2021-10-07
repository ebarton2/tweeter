package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.view.interfaces.PagedViewInterface;
import edu.byu.cs.tweeter.model.domain.Status;

public abstract class StatusPagedPresenter<T extends PagedViewInterface> extends PagedAbstractPresenter<T> {
    protected Status lastStatus;
    protected StatusService statusService;

    public StatusPagedPresenter(T view) {
        super(view);
        statusService = new StatusService();
    }

    protected void statusSuccessHandler(List<Status> statuses, boolean hasMorePages) {
        isLoading = false;
        lastStatus = (statuses.size() > 0 ? statuses.get(statuses.size() - 1) : null);
        this.hasMorePages = hasMorePages;
        view.removeLoadingFooter();
        view.loadMoreItems(statuses);
    }

}
