package edu.byu.cs.tweeter.model.net.response;

public class IsFollowerResponse extends Response {

    private boolean following;

    public IsFollowerResponse(boolean success, boolean isFollowing) {
        super(success);
        this.following = isFollowing;
    }

    public IsFollowerResponse(boolean success, String message) {
        super(success, message);
        this.following = false;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public boolean isFollowing() {
        return following;
    }
}
