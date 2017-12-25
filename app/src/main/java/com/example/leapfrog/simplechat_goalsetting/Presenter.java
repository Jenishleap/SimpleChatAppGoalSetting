package com.example.leapfrog.simplechat_goalsetting;


import android.content.Context;

import io.reactivex.observers.DisposableObserver;

public interface Presenter<T extends BaseView> {

    /**
     * Attach View to the presenter
     *
     * @param t {@link BaseView}
     */
    void attachView(T t);

    /**
     * De-attach view
     */
    void deAttachView();

    /**
     * Check if view is attached to the presenter
     *
     * @return {@code true} if view is attached to the presenter; else {@code false}
     */
    boolean isViewAttached();

    /**
     * Return the attached view
     *
     * @return {@link BaseView} attached to the presenter
     */
    T getView();

    /**
     * Add RxJava Subscription
     *
     * @param subscription {@link DisposableObserver}
     */
    void addSubscription(DisposableObserver subscription);

    /**
     * Return the context of the view
     *
     * @return {@link Context}
     */
    Context getContext();

}
