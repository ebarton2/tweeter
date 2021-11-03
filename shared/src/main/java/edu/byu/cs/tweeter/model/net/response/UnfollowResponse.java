package edu.byu.cs.tweeter.model.net.response;

import sun.security.krb5.internal.crypto.RsaMd5CksumType;

public class UnfollowResponse extends Response {
    public UnfollowResponse(boolean success) {
        super(success);
    }

    public UnfollowResponse(boolean success, String message) {
        super(success, message);
    }
}
