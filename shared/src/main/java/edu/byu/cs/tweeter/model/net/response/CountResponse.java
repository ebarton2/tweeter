package edu.byu.cs.tweeter.model.net.response;

public class CountResponse extends Response {

    private int count;

    CountResponse(boolean success, int count) {
        super(success);
        this.count = count;
    }

    CountResponse(boolean success, String message, int count) {
        super(success, message);
        this.count = count;
    }

    public int getCount() { return count; }

}
