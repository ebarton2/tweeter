package edu.byu.cs.tweeter.client.model.net;

import edu.byu.cs.tweeter.model.net.response.Response;

public class AbstractServerFacade { // TODO: get rid of this
    <T extends Response> T getSuccess(T response) {
        if (response.isSuccess()){
            return response;
        } else {
            return response;
        }
    }
}
