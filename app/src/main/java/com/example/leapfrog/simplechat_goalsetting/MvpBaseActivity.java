package com.example.leapfrog.simplechat_goalsetting;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.firebase.client.Firebase;

import javax.inject.Inject;

public abstract class MvpBaseActivity<T extends BasePresenter> extends BaseActivity implements BaseView {

    @Inject
    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);//required from ChatActivity
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.deAttachView();
        }
    }

}
