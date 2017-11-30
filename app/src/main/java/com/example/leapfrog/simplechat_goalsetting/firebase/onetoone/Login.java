package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leapfrog.simplechat_goalsetting.MvpBaseActivity;
import com.example.leapfrog.simplechat_goalsetting.MyApplication;
import com.example.leapfrog.simplechat_goalsetting.R;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login.LoginContract;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login.LoginPresenterImpl;
import com.example.leapfrog.simplechat_goalsetting.utils.UiUtils;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class Login extends MvpBaseActivity<LoginPresenterImpl> implements LoginContract.LoginView {


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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void injectDagger() {
        ((MyApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @OnClick(R.id.register)
    public void startRegisterActivity() {
        startActivity(new Intent(Login.this, Register.class));
    }

    @OnClick(R.id.loginButton)
    public void login() {
        presenter.loginWithUserName(UiUtils.getString(username), UiUtils.getString(password));
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
        startActivity(new Intent(Login.this, Users.class));
    }

}
