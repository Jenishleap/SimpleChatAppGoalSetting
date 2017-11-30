package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login;


import android.widget.Toast;

import com.example.leapfrog.simplechat_goalsetting.BasePresenter;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;

public class LoginPresenterImpl extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenter {

    private LoginRepository loginRepository;
    String username, password;

    @Inject
    public LoginPresenterImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public void loginWithUsernameAndPassword(String username, String password) {
        this.username = username;
        this.password = password;

        if (!validateUsernamePassword(username, password)) {
            return;
        }

        addSubscription(loginRepository.loginUser(username, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new LoginSubscriber()));
    }


    private class LoginSubscriber extends DisposableObserver<JSONObject> {

        @Override
        public void onNext(JSONObject jsonObject) {

            try {
                if (!jsonObject.has(username)) {
                    Toast.makeText(getView().getContext(), "user not found", Toast.LENGTH_LONG).show();

                } else if (jsonObject.getJSONObject(username).getString("password").equals(password)) {
                    UserDetails.username = username;
                    UserDetails.password = password;
                    getView().onLoginSuccess();
                } else {
                    Toast.makeText(getView().getContext(), "incorrect password", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

    private boolean validateUsernamePassword(String username, String password) {
        if (username.equals("")) {
            getView().showInvalidUserName();
            return false;
        } else if (password.equals("")) {
            getView().showInvalidPasswordError();
            return false;
        }
        return true;
    }

}
