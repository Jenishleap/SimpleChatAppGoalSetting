package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.users;


import com.example.leapfrog.simplechat_goalsetting.BaseView;
import com.example.leapfrog.simplechat_goalsetting.Presenter;

import java.util.ArrayList;

public class UsersContract {

    public interface UsersView extends BaseView {

        void showUserNoAvailable(boolean show);

        void showUsersList(boolean show);

        void showUsers(ArrayList<String> users);

    }

    public interface UsersPresenter extends Presenter<UsersView> {

        void fetchUsers();

    }

}
