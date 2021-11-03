package edu.byu.cs.tweeter.model.net.response;

import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowersResponse extends PagedResponse {

    private List<User> followers;


    public FollowersResponse(String message) { super(false, message, false);}

    public FollowersResponse(List<User> followers, boolean success, boolean hasMorePages) {
        super(success, hasMorePages);
        this.followers = followers;
    }

    public List<User> getFollowers() { return followers; }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        FollowersResponse that = (FollowersResponse) param;

        return (Objects.equals(followers, that.followers) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(followers);
    }
}
