package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;

/**
 * Background task that posts a new status sent by a user.
 */
public class PostStatusTask extends AuthorizedTask {
    private static final String LOG_TAG = "PostStatusTask";
    private static final String URL_PATH = "/poststatus";


    /**
     * The new status being sent. Contains all properties of the status,
     * including the identity of the user sending the status.
     */
    private Status status;

    private PostStatusRequest request;


    public PostStatusTask(AuthToken authToken, Status status, Handler messageHandler) {
        super(messageHandler, authToken);
        this.authToken = authToken;
        this.status = status;
        this.request = new PostStatusRequest(authToken, status);
    }

    @Override
    protected void runTask() throws IOException {
        try {
            PostStatusResponse response = getServerFacade().postStatus(request, URL_PATH);
            if (response.isSuccess()) {
                System.out.println("I posted something!");
            } else {
                throw new IOException(response.getMessage());
            }
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {

    }
}
