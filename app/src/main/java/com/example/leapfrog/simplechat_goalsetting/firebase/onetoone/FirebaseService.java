package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone;


import com.google.gson.JsonObject;

import org.json.JSONObject;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;


public interface FirebaseService {


    @GET("users.json")
    Observable<JsonObject> registerUser();
}
