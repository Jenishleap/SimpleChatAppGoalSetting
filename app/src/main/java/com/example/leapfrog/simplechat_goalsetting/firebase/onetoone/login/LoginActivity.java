package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.leapfrog.simplechat_goalsetting.MvpBaseActivity;
import com.example.leapfrog.simplechat_goalsetting.MyApplication;
import com.example.leapfrog.simplechat_goalsetting.R;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.register.RegisterActivity;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.UsersActivity;
import com.example.leapfrog.simplechat_goalsetting.utils.UiUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginActivity extends MvpBaseActivity<LoginPresenterImpl> implements LoginContract.LoginView {

    @BindView(R.id.register)
    TextView register;

    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.loginButton)
    Button loginButton;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new ProgressDialog(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void injectDagger() {
        ((MyApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    protected void setActionBar() {

    }

    @OnClick(R.id.register)
    public void startRegisterActivity() {
        finish();
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    @OnClick(R.id.loginButton)
    public void login() {
        presenter.loginWithUsernameAndPassword(UiUtils.getString(username), UiUtils.getString(password));
    }


    @Override
    public void showProgressBar(String message) {
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void hideProgressBar() {
        dialog.dismiss();
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
    public void showInvalidUserName() {
        username.setError("User name cannot be blank");
    }

    @Override
    public void showInvalidPasswordError() {
        password.setError("Password cannot be blank");
    }

    @Override
    public void onLoginSuccess() {
        startActivity(new Intent(LoginActivity.this, UsersActivity.class));
    }

    @Override
    public void emptyUserInputFields() {
        username.setText("");
        password.setText("");
    }

    @Override
    public void userNotFound() {
        String errorMessage = getString(R.string.user_not_found);
        username.setError(errorMessage);
        UiUtils.showToast(this, errorMessage);
    }

    @Override
    public void passwordIncorrect() {
        String errorMessage = getString(R.string.incorrect_password);
        password.setError(errorMessage);
        UiUtils.showToast(this, errorMessage);
    }

}
