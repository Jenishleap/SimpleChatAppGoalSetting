package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login;

import android.util.Log;

import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.FirebaseService;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginRepositoryImpl implements LoginRepository {

    private FirebaseService firebaseService;

    @Inject
    public LoginRepositoryImpl(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @Override
    public Observable<JSONObject> loginUser(String username, String password) {
        return firebaseService.registerUser()
                .map(new Function<JsonObject, JSONObject>() {
                    @Override
                    public JSONObject apply(JsonObject jsonObject) throws Exception {
                        String responseString = jsonObject.toString();
                        JSONObject responseJson = new JSONObject(responseString);
                        return responseJson;
                    }
                })
                .subscribeOn(Schedulers.io());
    }

}
