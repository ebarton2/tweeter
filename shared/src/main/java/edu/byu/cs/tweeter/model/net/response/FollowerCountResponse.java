package edu.byu.cs.tweeter.model.net.response;

public class FollowerCountResponse extends CountResponse{

    public FollowerCountResponse(boolean success, int count) {
        super(success, count);
    }

    public FollowerCountResponse(boolean success, String message, int count) {
        super(success, message, count);
    }
}
