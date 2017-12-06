package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.chat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.leapfrog.simplechat_goalsetting.BaseActivity;
import com.example.leapfrog.simplechat_goalsetting.Config;
import com.example.leapfrog.simplechat_goalsetting.MyApplication;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.UserDetails;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.example.leapfrog.simplechat_goalsetting.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static java.security.AccessController.getContext;


public class ChatActivity extends BaseActivity {

    @BindView(R.id.layout1)
    LinearLayout layout;

    @BindView(R.id.sendButton)
    ImageView sendButton;

    @BindView(R.id.messageArea)
    EditText messageArea;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    Firebase firebaseYouAndFriend, firebaseFriendAndYou;


    @Override
    protected int getLayout() {
        return R.layout.activity_chat;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(this);

        firebaseYouAndFriend = new Firebase(Config.BASE_URL + Config.MESSAGES + "/" + UserDetails.username + "_" + UserDetails.chatWith);
        firebaseFriendAndYou = new Firebase(Config.BASE_URL + Config.MESSAGES + "/" + UserDetails.chatWith + "_" + UserDetails.username);

        addEventListener();
    }

    private void addEventListener() {
        firebaseYouAndFriend.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if (userName.equals(UserDetails.username)) {
                    addMessageBox("You:-\n" + message, 1);
                } else {
                    addMessageBox(UserDetails.chatWith + ":-\n" + message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


    @Override
    protected void injectDagger() {
        ((MyApplication) getApplication()).getApplicationComponent().inject(this);
    }


    @OnClick(R.id.sendButton)
    public void sendMessage() {
        String messageText = messageArea.getText().toString();

        if (!messageText.equals("")) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("message", messageText);
            map.put("user", UserDetails.username);
            firebaseYouAndFriend.push().setValue(map);
            firebaseFriendAndYou.push().setValue(map);
            clearMessageInput();
        }
    }

    private void addMessageBox(String message, int type) {
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

    public void clearMessageInput() {
        messageArea.setText("");
    }

}
