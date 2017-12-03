package com.example.leapfrog.simplechat_goalsetting;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import javax.inject.Inject;


public class MyApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Inject
    Context context;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ApplicationComponent getApplicationComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

}
