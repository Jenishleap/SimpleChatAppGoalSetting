package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.leapfrog.simplechat_goalsetting.MvpBaseActivity;
import com.example.leapfrog.simplechat_goalsetting.MyApplication;
import com.example.leapfrog.simplechat_goalsetting.R;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.users.UsersContract;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.users.UsersPresenterImpl;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class Users extends MvpBaseActivity<UsersPresenterImpl> implements UsersContract.UsersView {

    @BindView(R.id.usersList)
    ListView usersList;

    @BindView(R.id.noUsersAvailable)
    TextView noUsersAvailable;

    @Override
    protected int getLayout() {
        return R.layout.activity_users;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.fetchUsers();
    }

    @Override
    protected void injectDagger() {
        ((MyApplication) getApplication()).getApplicationComponent().inject(this);
    }


    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showUserNoAvailable(boolean show) {
        noUsersAvailable.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showUsersList(boolean show) {
        usersList.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showUsers(final ArrayList<String> users) {
        usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, users));
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = users.get(position);
                startChatActivity();
            }
        });
    }

    private void startChatActivity() {
        startActivity(new Intent(Users.this, Chat.class));
    }

}
