package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.leapfrog.simplechat_goalsetting.MvpBaseActivity;
import com.example.leapfrog.simplechat_goalsetting.MyApplication;
import com.example.leapfrog.simplechat_goalsetting.R;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.chat.ChatActivity;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.users.UsersContract;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.users.UsersPresenterImpl;
import com.example.leapfrog.simplechat_goalsetting.utils.UiUtils;

import java.util.ArrayList;

import butterknife.BindView;


public class UsersActivity extends MvpBaseActivity<UsersPresenterImpl> implements UsersContract.UsersView {

    @BindView(R.id.usersList)
    ListView usersList;

    @BindView(R.id.noUsersAvailable)
    TextView noUsersAvailable;

    @BindView(R.id.linearlayout_loading_friends)
    LinearLayout linearLayoutLoadingFriends;

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
    protected void setActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getString(R.string.choose_friends));
    }


    @Override
    public void showProgressBar(String message) {
        linearLayoutLoadingFriends.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        linearLayoutLoadingFriends.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String message) {
        UiUtils.showToast(this, message);
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
        startActivity(new Intent(UsersActivity.this, ChatActivity.class));
    }

    @Override
    public void onBackPressed() {
        showLogOutDialog();
    }

    private void showLogOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.log_out))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

}
