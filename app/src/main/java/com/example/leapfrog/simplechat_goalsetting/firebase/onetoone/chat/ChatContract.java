package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.chat;


import com.example.leapfrog.simplechat_goalsetting.BaseView;
import com.example.leapfrog.simplechat_goalsetting.Presenter;

public class ChatContract {

    public interface ChatView extends BaseView {

        void addMessageBox(String message, int type);

    }

    public interface ChatPresenter extends Presenter<ChatView> {

        void addChildEventListener();

    }

}
