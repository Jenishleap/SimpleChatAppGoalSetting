package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.chat;

import android.widget.Toast;

import com.example.leapfrog.simplechat_goalsetting.BasePresenter;
import com.example.leapfrog.simplechat_goalsetting.Config;
import com.example.leapfrog.simplechat_goalsetting.R;
import com.example.leapfrog.simplechat_goalsetting.annotations.FriendAndYou;
import com.example.leapfrog.simplechat_goalsetting.annotations.YouAndFriend;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.UserDetails;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class ChatPresenterImpl extends BasePresenter<ChatContract.ChatView> implements ChatContract.ChatPresenter {

    private Firebase firebaseYouAndFriend, firebaseFriendAndYou;

    @Inject
    public ChatPresenterImpl(@YouAndFriend Firebase firebaseYouAndFriend, @FriendAndYou Firebase firebaseFriendAndYou) {
        this.firebaseYouAndFriend = firebaseYouAndFriend;
        this.firebaseFriendAndYou = firebaseFriendAndYou;
    }

    @Override
    public void addChildEventListener() {
        firebaseYouAndFriend.addChildEventListener(new ChildEventListener() {
            //this is for retrieving old chat messages
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get(Config.MESSAGE).toString();
                String userName = map.get(Config.USER).toString();

                if (userName.equals(UserDetails.username)) {
                    getView().addMessageBox("You:-\n" + message, 1);
                } else {
                    getView().addMessageBox(UserDetails.chatWith + ":-\n" + message, 2);
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
    public void sendMessage(String message) {
        if (message.equals("")) {
            Toast.makeText(getContext(), getContext().getString(R.string.cannot_send_empty_message), Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put(Config.MESSAGE, message);
        map.put(Config.USER, UserDetails.username);
        firebaseYouAndFriend.push().setValue(map);
        firebaseFriendAndYou.push().setValue(map);
        getView().clearMessageInput();
    }

}
