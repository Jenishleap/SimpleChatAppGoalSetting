package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.chat;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.leapfrog.simplechat_goalsetting.MvpBaseActivity;
import com.example.leapfrog.simplechat_goalsetting.MyApplication;
import com.example.leapfrog.simplechat_goalsetting.R;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatActivity extends MvpBaseActivity<ChatPresenterImpl> implements ChatContract.ChatView {

    @BindView(R.id.layout1)
    LinearLayout layout;

    @BindView(R.id.sendButton)
    ImageView sendButton;

    @BindView(R.id.messageArea)
    EditText messageArea;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @Override
    protected int getLayout() {
        return R.layout.activity_chat;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.addChildEventListener();
    }

    @Override
    protected void injectDagger() {
        ((MyApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @OnClick(R.id.sendButton)
    public void sendMessage() {
        String messageText = messageArea.getText().toString();
        presenter.sendMessage(messageText);
    }

    @Override
    public void addMessageBox(String message, int type) {
        TextView textView = new TextView(ChatActivity.this);
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        textView.setLayoutParams(lp);

        if (type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1);
        } else {
            textView.setBackgroundResource(R.drawable.rounded_corner2);
        }

        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    @Override
    public void clearMessageInput() {
        messageArea.setText("");
    }

    @Override
    public void showProgressBar(String message) {

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

}
