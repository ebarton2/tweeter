package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.view.interfaces.ViewInterface;

public abstract class AbstractPresenter<T extends ViewInterface> {
    protected T view;

    public AbstractPresenter(T view) {
        this.view = view;
    }
}
