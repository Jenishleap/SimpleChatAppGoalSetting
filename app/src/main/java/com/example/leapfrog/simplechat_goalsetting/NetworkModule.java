package com.example.leapfrog.simplechat_goalsetting;


import android.util.Log;

import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.FirebaseService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Prepares environment for making http network request
 * <ul>
 * <li>Creates instance of OkHttp client {@link OkHttpClient}</li>
 * <li>Provides instance of retrofit service{@link FirebaseService} </li>
 * </ul>
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    public OkHttpClient getOkHttpClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("HTTP", message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

    }

    @Provides
    @Singleton
    public Gson getGSON() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    public GsonConverterFactory getGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    public Retrofit getRetrofit(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public FirebaseService getSchService(Retrofit retrofit) {
        return retrofit.create(FirebaseService.class);
    }

}