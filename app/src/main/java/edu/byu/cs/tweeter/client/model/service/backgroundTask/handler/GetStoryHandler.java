package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.model.domain.Status;

/**
 * Message handler (i.e., observer) for GetStoryTask.
 */
public class GetStoryHandler extends Handler {
    private StatusService.StoryObserver observer;

    public GetStoryHandler(StatusService.StoryObserver observer)
    {
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        /*isLoading = false;
        removeLoadingFooter();*/

        boolean success = msg.getData().getBoolean(GetStoryTask.SUCCESS_KEY);
        if (success) {
            List<Status> statuses = (List<Status>) msg.getData().getSerializable(GetStoryTask.STATUSES_KEY);
            boolean hasMorePages = msg.getData().getBoolean(GetStoryTask.MORE_PAGES_KEY);
            observer.handleSuccess(statuses, hasMorePages);
            //lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;

            //storyRecyclerViewAdapter.addItems(statuses);
        } else if (msg.getData().containsKey(GetStoryTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(GetStoryTask.MESSAGE_KEY);
            observer.handleFailure("Failed to get story: " + message);
            //Toast.makeText(getContext(), , Toast.LENGTH_LONG).show();
        } else if (msg.getData().containsKey(GetStoryTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(GetStoryTask.EXCEPTION_KEY);
            observer.handleFailure("Failed to get story because of exception: " + ex.getMessage());
            //Toast.makeText(getContext(), , Toast.LENGTH_LONG).show();
        }
    }
}