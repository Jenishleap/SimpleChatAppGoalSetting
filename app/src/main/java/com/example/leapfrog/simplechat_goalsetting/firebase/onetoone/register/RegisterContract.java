package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.register;

import com.example.leapfrog.simplechat_goalsetting.BaseView;
import com.example.leapfrog.simplechat_goalsetting.Presenter;

public class RegisterContract {

    public interface RegisterView extends BaseView {

        void showUserNameBlankError();

        void showPasswordBlankError();

        void showInvalidUserNameError();

        void showUserNameLengthError();

        void showPasswordLengthError();

        void clearUserInputs();

        void userNameAlreadyExists();

        void registrationSuccesful();

    }

    public interface RegisterPresenter extends Presenter<RegisterView> {

        void registerUser(String username, String password);

    }

}
