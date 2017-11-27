package com.example.leapfrog.simplechat_goalsetting.firebase.group.adapter;


import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leapfrog.simplechat_goalsetting.R;
import com.example.leapfrog.simplechat_goalsetting.firebase.group.ChatMessage;
import com.example.leapfrog.simplechat_goalsetting.firebase.group.FireBaseActivity;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;

public class MessageAdapter extends FirebaseListAdapter<ChatMessage> {

    private FireBaseActivity activity;

    public MessageAdapter(FireBaseActivity activity, Class<ChatMessage> modelClass, int modelLayout, DatabaseReference ref) {
        super(activity, modelClass, modelLayout, ref);
        this.activity = activity;
    }

    @Override
    protected void populateView(View v, ChatMessage model, int position) {
        // Get references to the views of message.xml
        TextView messageText = (TextView) v.findViewById(R.id.message_text);
        TextView messageUser = (TextView) v.findViewById(R.id.message_user);
        TextView messageTime = (TextView) v.findViewById(R.id.message_time);

        // Set their text
        messageText.setText(model.getMessageText());
        messageUser.setText(model.getMessageUser());
        messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                model.getMessageTime()));
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ChatMessage chatMessage = getItem(position);
        if (chatMessage.getMessageUserId().equals(activity.getLoggedInUserName()))
            view = activity.getLayoutInflater().inflate(R.layout.message_out, viewGroup, false);
        else
            view = activity.getLayoutInflater().inflate(R.layout.message_in, viewGroup, false);
        //generating view
        populateView(view, chatMessage, position);
        return view;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }

}
