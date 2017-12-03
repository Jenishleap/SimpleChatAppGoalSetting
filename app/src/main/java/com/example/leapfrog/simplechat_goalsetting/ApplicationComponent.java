package com.example.leapfrog.simplechat_goalsetting;


import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.chat.ChatActivity;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login.Login;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.register.Register;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.Users;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, PresenterModule.class})
public interface ApplicationComponent {

    void inject(Login login);

    void inject(Register register);

    void inject(Users users);

    void inject(ChatActivity chat);

}
