package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

public abstract class PagedTask<T> extends AuthorizedTask {
    public static final String ITEMS_KEY = "items";
    public static final String MORE_PAGES_KEY = "more-pages";

    /**
     * Maximum number of followed users to return (i.e., page size).
     */
    private int limit;
    /**
     * The last person being followed returned in the previous page of results (can be null).
     * This allows the new page to begin where the previous page ended.
     */
    private T lastItem;

    private List<T> items;
    private boolean hasMorePages;

    public PagedTask(Handler messageHandler, AuthToken authToken, int limit, T lastItem) {
        super(messageHandler, authToken);
        this.limit = limit;
        this.lastItem = lastItem;
    }

    public T getLastItem() {
        return lastItem;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    protected void runTask() throws IOException {
        Pair<List<T>, Boolean> pageOfUsers = getItems();

        items = pageOfUsers.getFirst();
        hasMorePages = pageOfUsers.getSecond();

        loadImages(items);
    }

    protected abstract Pair<List<T>, Boolean> getItems();

    protected abstract List<User> convertItemsToUsers(List<T> items);

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        msgBundle.putSerializable(ITEMS_KEY, (Serializable) items);
        msgBundle.putBoolean(MORE_PAGES_KEY, hasMorePages);
    }

    private void loadImages(List<T> items) throws IOException {
        for (User u : convertItemsToUsers(items)) {
            BackgroundTaskUtils.loadImage(u);
        }
    }
}
