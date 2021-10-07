package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;

/**
 * Message handler (i.e., observer) for GetUserTask.
 */
public class GetUserHandler extends UserNotificationHandler {

    public GetUserHandler(UserService.GetUserObserver observer) {
        super(observer);
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "Failed to get user's profile";
    }

    @Override
    protected String GetUserKey() {
        return GetUserTask.USER_KEY;
    }
}
