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

import com.example.leapfrog.simplechat_goalsetting.MyApplication;
import com.example.leapfrog.simplechat_goalsetting.R;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class Login extends AppCompatActivity {

    TextView register;
    EditText username, password;
    Button loginButton;
    String user, pass;

    @Inject
    FirebaseService firebaseService;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public static String TAG = "login";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ((MyApplication) getApplication()).getApplicationComponent().inject(this);
        Log.d("login", "check dagger");

        register = (TextView) findViewById(R.id.register);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = username.getText().toString();
                pass = password.getText().toString();

                if (user.equals("")) {
                    username.setError("can't be blank");
                } else if (pass.equals("")) {
                    password.setError("can't be blank");
                } else {
                    compositeDisposable.add(firebaseService.registerUser()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<JsonObject>() {
                                @Override
                                public void onNext(JsonObject obj) {

                                    try {
                                        String json_string = obj.toString();
                                        JSONObject json_obj = new JSONObject(json_string);
                                        if (!json_obj.has(user)) {
                                            Toast.makeText(Login.this, "user not found", Toast.LENGTH_LONG).show();

                                        } else if (json_obj.getJSONObject(user).getString("password").equals(pass)) {
                                            UserDetails.username = user;
                                            UserDetails.password = pass;
                                            startActivity(new Intent(Login.this, Users.class));
                                        } else {
                                            Toast.makeText(Login.this, "incorrect password", Toast.LENGTH_LONG).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
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


            }
        });
    }
}
