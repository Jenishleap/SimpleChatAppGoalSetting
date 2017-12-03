package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.register;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.leapfrog.simplechat_goalsetting.MvpBaseActivity;
import com.example.leapfrog.simplechat_goalsetting.MyApplication;
import com.example.leapfrog.simplechat_goalsetting.R;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login.LoginActivity;
import com.example.leapfrog.simplechat_goalsetting.utils.UiUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class RegisterActivity extends MvpBaseActivity<RegisterPresenterImpl> implements RegisterContract.RegisterView {

    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.login)
    TextView login;

    @BindView(R.id.registerButton)
    Button registerButton;

    public static String TAG = "register";

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.login)
    public void startLoginActivity() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    @OnClick(R.id.registerButton)
    public void register() {
        presenter.registerUser(UiUtils.getString(username), UiUtils.getString(password));
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
    public void showUserNameBlankError() {
        username.setError("Username can't be blank");
    }

    @Override
    public void showPasswordBlankError() {
        password.setError("Password can't be blank");
    }

    @Override
    public void showInvalidUserNameError() {
        username.setError("Only alphabet or number allowed");
    }

    @Override
    public void showUserNameLengthError() {
        username.setError("Username should be at least 5 characters long");
    }

    @Override
    public void showPasswordLengthError() {
        password.setError("Password should be at least 5 characters long");
    }

}
