package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login;


import com.example.leapfrog.simplechat_goalsetting.BaseView;
import com.example.leapfrog.simplechat_goalsetting.Presenter;

public class LoginContract {

    public interface LoginView extends BaseView {

        void showInvalidUserName();

        void showInvalidPasswordError();

        void onLoginSuccess();

    }

    public interface LoginPresenter extends Presenter<LoginView> {

        void loginWithUserName(String username, String password);

    }

}
