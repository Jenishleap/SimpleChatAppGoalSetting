package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.users;

import com.example.leapfrog.simplechat_goalsetting.BasePresenter;
import com.example.leapfrog.simplechat_goalsetting.R;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.UserDetails;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login.LoginRepository;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;

public class UsersPresenterImpl extends BasePresenter<UsersContract.UsersView> implements UsersContract.UsersPresenter {

    private LoginRepository loginRepository;

    ArrayList<String> users = new ArrayList<>();

    int totalUsers = 0;

    @Inject
    public UsersPresenterImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public void fetchUsers() {

        getView().showProgressBar(null);

        addSubscription(loginRepository.loginUser("chandra", "chandra")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new UsersSubscriber())
        );
    }

    private class UsersSubscriber extends DisposableObserver<JSONObject> {

        @Override
        public void onNext(JSONObject jsonObject) {
            getView().hideProgressBar();
            doOnSuccess(jsonObject);

        }

        @Override
        public void onError(Throwable e) {
            getView().onFailure(getContext().getString(R.string.error_loading_users));
            getView().hideProgressBar();
        }

        @Override
        public void onComplete() {

        }
    }

    public void doOnSuccess(JSONObject obj) {

        Iterator i = obj.keys();
        String key = "";

        while (i.hasNext()) {
            key = i.next().toString();

            if (!key.equals(UserDetails.username)) {
                users.add(key);
            }
            totalUsers++;
        }

        if (totalUsers <= 1) {
            getView().showUserNoAvailable(true);
            getView().showUsersList(false);

        } else {
            getView().showUserNoAvailable(false);
            getView().showUsersList(true);
            getView().showUsers(users);
        }
    }

}
