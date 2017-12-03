package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login;

import android.content.Context;
import android.content.Intent;
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

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void injectDagger() {
        ((MyApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @OnClick(R.id.register)
    public void startRegisterActivity() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    @OnClick(R.id.loginButton)
    public void login() {
        presenter.loginWithUsernameAndPassword(UiUtils.getString(username), UiUtils.getString(password));
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

}
