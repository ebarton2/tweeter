package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;

/**
 * Background task that logs in a user (i.e., starts a session).
 */
public class LoginTask extends AuthenticationTask {
    private static final String LOG_TAG = "LoginTask";
    private static final String URL_PATH = "/login"; //TODO: implement Login

    private LoginRequest request;

    public LoginTask(String username, String password, Handler messageHandler) {
        super(username, password, messageHandler);
        this.request = new LoginRequest(username, password);
    }

    @Override
    protected void runTask() throws IOException {
        try{
            LoginResponse response = getServerFacade().login(request, URL_PATH);
            if(response.isSuccess()){
                currentUser = response.getUser();
                authToken = response.getAuthToken();
                BackgroundTaskUtils.loadImage(currentUser);
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
    }
}
