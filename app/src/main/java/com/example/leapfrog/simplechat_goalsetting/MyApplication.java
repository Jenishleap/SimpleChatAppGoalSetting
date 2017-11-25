package com.example.leapfrog.simplechat_goalsetting;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;


/**
 * Created by leapfrog on 11/17/17.
 */

public class MyApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Inject
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        /*mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();*/

//        initializeRealm();
    }

    private void initializeRealm() {
        /*RealmConfiguration config = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(config);*/
    }
}
