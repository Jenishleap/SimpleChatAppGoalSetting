package com.example.leapfrog.simplechat_goalsetting;


import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class BasePresenter<T extends BaseView> implements Presenter<T> {

    private T t;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void attachView(T t) {
        this.t = t;
    }

    @Override
    public void deAttachView() {
        compositeDisposable.dispose();
        this.t = null;
    }

    @Override
    public boolean isViewAttached() {
        return t != null;
    }

    @Override
    public T getView() {
        return t;
    }

    @Override
    public void addSubscription(DisposableObserver subscription) {
        this.compositeDisposable.add(compositeDisposable);
    }

    @Override
    public Context getContext() {
        return getView().getContext();
    }

}
