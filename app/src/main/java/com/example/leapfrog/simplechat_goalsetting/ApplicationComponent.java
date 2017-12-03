package com.example.leapfrog.simplechat_goalsetting;


import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.chat.ChatActivity;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login.LoginActivity;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.register.RegisterActivity;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.UsersActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, PresenterModule.class})
public interface ApplicationComponent {

    void inject(LoginActivity login);

    void inject(RegisterActivity register);

    void inject(UsersActivity users);

    void inject(ChatActivity chat);

}
