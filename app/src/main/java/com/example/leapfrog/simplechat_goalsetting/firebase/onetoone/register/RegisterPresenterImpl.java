package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.register;

import android.util.Log;

import com.example.leapfrog.simplechat_goalsetting.BasePresenter;
import com.example.leapfrog.simplechat_goalsetting.R;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login.LoginRepository;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;

public class RegisterPresenterImpl extends BasePresenter<RegisterContract.RegisterView> implements RegisterContract.RegisterPresenter {

    public final static String TAG = "register";
    private LoginRepository loginRepository;
    String username, password;
    DatabaseReference mDatabaseReference;

    @Inject
    public RegisterPresenterImpl(LoginRepository loginRepository, DatabaseReference mDatabaseReference) {
        this.loginRepository = loginRepository;
        this.mDatabaseReference = mDatabaseReference;
    }

    @Override
    public void registerUser(String username, String password) {
        this.username = username;
        this.password = password;

        if (!validateUserNameAndPassword(username, password)) {
            return;
        }

        getView().showProgressBar(getContext().getString(R.string.registering_user));
        addSubscription(loginRepository.loginUser(username, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RegisterSubscriber())
        );
    }

    private class RegisterSubscriber extends DisposableObserver<JSONObject> {

        @Override
        public void onNext(JSONObject obj) {
            getView().hideProgressBar();
            if (!obj.has(username)) {
                mDatabaseReference.child(username).child("password").setValue(password);
                getView().clearUserInputs();
                getView().registrationSuccesful();
            } else {
                getView().userNameAlreadyExists();
            }
        }

        @Override
        public void onError(Throwable e) {
            getView().onFailure(getContext().getString(R.string.error_registering));
            getView().hideProgressBar();
        }

        @Override
        public void onComplete() {
            Log.d(TAG, "onComplete");
        }
    }

    private boolean validateUserNameAndPassword(String username, String password) {

        if (username.equals("")) {
            getView().showUserNameBlankError();
            return false;
        } else if (password.equals("")) {
            getView().showPasswordBlankError();
            return false;
        } else if (!username.matches("[A-Za-z0-9]+")) {
            getView().showInvalidUserNameError();
            return false;
        } else if (username.length() < 5) {
            getView().showUserNameLengthError();
            return false;
        } else if (password.length() < 5) {
            getView().showPasswordLengthError();
            return false;
        }
        return true;
    }

}