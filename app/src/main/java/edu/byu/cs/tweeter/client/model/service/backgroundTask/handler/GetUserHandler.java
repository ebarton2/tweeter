package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Message handler (i.e., observer) for GetUserTask.
 */
public class GetUserHandler extends Handler {
    private UserService.GetUserObserver observer;

    public GetUserHandler(UserService.GetUserObserver observer) {
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(GetUserTask.SUCCESS_KEY);
        if (success) {
            User user = (User) msg.getData().getSerializable(GetUserTask.USER_KEY);
            observer.handleSuccess(user);
        } else if (msg.getData().containsKey(GetUserTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(GetUserTask.MESSAGE_KEY);
            observer.handleFailure(message);
        } else if (msg.getData().containsKey(GetUserTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(GetUserTask.EXCEPTION_KEY);
            String message = "Failed to get user's profile because of exception: " + ex.getMessage();
            observer.handleFailure(message);
        }
    }
}
