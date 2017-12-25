package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login;


import com.google.gson.JsonObject;

import org.json.JSONObject;

import io.reactivex.Observable;

public interface LoginRepository {

    Observable<JSONObject> loginUser(String username, String password);
}
