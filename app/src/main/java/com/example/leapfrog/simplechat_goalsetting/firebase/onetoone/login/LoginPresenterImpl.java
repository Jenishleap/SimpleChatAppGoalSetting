package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login;


import android.widget.Toast;

import com.example.leapfrog.simplechat_goalsetting.BasePresenter;
import com.example.leapfrog.simplechat_goalsetting.R;
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

        getView().showProgressBar(getContext().getString(R.string.loggin_in));
        addSubscription(loginRepository.loginUser(username, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new LoginSubscriber()));
    }


    private class LoginSubscriber extends DisposableObserver<JSONObject> {

        @Override
        public void onNext(JSONObject jsonObject) {
            getView().hideProgressBar();
            try {
                if (!jsonObject.has(username)) {
                    getView().userNotFound();
                } else if (jsonObject.getJSONObject(username).getString("password").equals(password)) {
                    UserDetails.username = username;
                    UserDetails.password = password;
                    getView().emptyUserInputFields();
                    getView().onLoginSuccess();
                } else {
                    getView().passwordIncorrect();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onError(Throwable e) {
            getView().onFailure(getContext().getString(R.string.error_loggin));
            getView().hideProgressBar();
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
