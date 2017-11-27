package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.leapfrog.simplechat_goalsetting.MyApplication;
import com.example.leapfrog.simplechat_goalsetting.R;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class Users extends AppCompatActivity {


    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public static String TAG = "register";

    @Inject
    FirebaseService firebaseService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        ((MyApplication) getApplication()).getApplicationComponent().inject(this);

        usersList = (ListView) findViewById(R.id.usersList);
        noUsersText = (TextView) findViewById(R.id.noUsersText);


        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = al.get(position);
                startActivity(new Intent(Users.this, Chat.class));
            }
        });


        compositeDisposable.add(firebaseService.registerUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<JsonObject>() {
                    @Override
                    public void onNext(JsonObject obj) {
                        String json_string = obj.toString();
                        doOnSuccess(json_string);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );


    }


    public void doOnSuccess(String s) {
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while (i.hasNext()) {
                key = i.next().toString();

                if (!key.equals(UserDetails.username)) {
                    al.add(key);
                }

                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (totalUsers <= 1) {
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        } else {
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        }

    }
}
