package edu.byu.cs.tweeter.model.net.response;

import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.model.domain.Status;

public class StoryResponse extends PagedResponse{

    private List<Status> statuses;

    public StoryResponse(List<Status> statuses, boolean success, boolean hasMorePages) {
        super(success, hasMorePages);
        this.statuses = statuses;
    }

    public StoryResponse(boolean success, String message, boolean hasMorePages) {
        super(success, message, hasMorePages);
    }

    public List<Status> getStatuses() { return statuses; }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        StoryResponse that = (StoryResponse) param;

        return (Objects.equals(statuses, that.statuses) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(statuses);
    }
}
