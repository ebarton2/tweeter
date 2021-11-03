package edu.byu.cs.tweeter.model.net.response;

public class IsFollowerResponse extends Response {

    private boolean isFollowing;

    public IsFollowerResponse(boolean success, boolean isFollowing) {
        super(success);
        this.isFollowing = isFollowing;
    }

    public IsFollowerResponse(boolean success, String message) {
        super(success, message);
        this.isFollowing = false;
    }

    public void setFollowing(boolean following) {
        isFollowing = following;
    }

    public boolean isFollowing() {
        return isFollowing;
    }
}
