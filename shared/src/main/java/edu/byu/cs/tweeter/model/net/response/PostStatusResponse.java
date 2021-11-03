package edu.byu.cs.tweeter.model.net.response;

public class PostStatusResponse extends Response {
    public PostStatusResponse(boolean success) {
        super(success);
    }

     public PostStatusResponse(boolean success, String message) {
        super(success, message);
    }
}
