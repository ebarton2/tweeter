package edu.byu.cs.tweeter.client.view.interfaces;

import java.net.MalformedURLException;
import java.util.List;

public interface PagedViewInterface<T> extends NavigateUserViewInterface {
    void loadMoreItems(List<T> items);
    void addLoadingFooter() throws MalformedURLException;
    void removeLoadingFooter();
}
