package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.User;

public class GetUserResponse extends Response {
    private User targetUser;
    public GetUserResponse(boolean success, User targetUser) {
        super(success);
        this.targetUser = targetUser;
    }

    public GetUserResponse(boolean success, String message) {
        super(success, message);
    }

    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }
}
