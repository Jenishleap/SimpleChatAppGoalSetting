package com.example.leapfrog.simplechat_goalsetting.firebase.onetoone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leapfrog.simplechat_goalsetting.Config;
import com.example.leapfrog.simplechat_goalsetting.MyApplication;
import com.example.leapfrog.simplechat_goalsetting.R;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class Register extends AppCompatActivity {

    EditText username, password;
    Button registerButton;
    String user, pass;
    TextView login;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public static String TAG = "register";


    @Inject
    FirebaseService firebaseService;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth auth;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ((MyApplication) getApplication()).getApplicationComponent().inject(this);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        registerButton = (Button) findViewById(R.id.registerButton);
        login = (TextView) findViewById(R.id.login);

        Firebase.setAndroidContext(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user = username.getText().toString();
                pass = password.getText().toString();

                if (user.equals("")) {
                    username.setError("can't be blank");
                } else if (pass.equals("")) {
                    password.setError("can't be blank");
                } else if (!user.matches("[A-Za-z0-9]+")) {
                    username.setError("only alphabet or number allowed");
                } else if (user.length() < 5) {
                    username.setError("at least 5 characters long");
                } else if (pass.length() < 5) {
                    password.setError("at least 5 characters long");
                } else {


                    mDatabaseReference = FirebaseDatabase.getInstance().getReference(Config.USERS);


                    compositeDisposable.add(firebaseService.registerUser()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<JsonObject>() {


                                @Override
                                public void onNext(JsonObject obj) {
                                    Log.d(TAG, "value of S: " + obj);
                                    if (!obj.has(user)) {
                                        mDatabaseReference.child(user).child("password").setValue(pass);
                                        Toast.makeText(Register.this, "registration successful", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(Register.this, "username already exists", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d(TAG, e.getMessage().toString());

                                }

                                @Override
                                public void onComplete() {
                                    Log.d(TAG, "onComplete");
                                }
                            })
                    );

                }


            }
        });


    }
}
