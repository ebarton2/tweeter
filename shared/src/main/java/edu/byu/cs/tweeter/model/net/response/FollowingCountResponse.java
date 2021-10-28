package edu.byu.cs.tweeter.model.net.response;

public class FollowingCountResponse extends CountResponse {

    public FollowingCountResponse(boolean success, int count) {
        super(success, count);
    }

    public FollowingCountResponse(boolean success, String message, int count) {
        super(success, message, count);
    }

}
