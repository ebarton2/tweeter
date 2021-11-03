package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;

/**
 * Background task that logs out a user (i.e., ends a session).
 */
public class LogoutTask extends BackgroundTask {
    private static final String LOG_TAG = "LogoutTask";
    private static final String URL_PATH = "/logout";


    /**
     * Auth token for logged-in user.
     */
    private AuthToken authToken;

    private LogoutRequest request;

    public LogoutTask(AuthToken authToken, Handler messageHandler) {
        super(messageHandler);
        this.request = new LogoutRequest(authToken);
        this.authToken = authToken;
    }


    @Override
    protected void runTask() throws IOException {
        try{
            LogoutResponse response = getServerFacade().logout(request, URL_PATH);
            if (response.isSuccess()){
                System.out.println("I logged out today!");
            }
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        //TODO
    }
}
