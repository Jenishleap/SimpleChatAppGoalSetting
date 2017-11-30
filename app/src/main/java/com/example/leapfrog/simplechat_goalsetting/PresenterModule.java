package com.example.leapfrog.simplechat_goalsetting;

import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.FirebaseService;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login.LoginRepository;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login.LoginRepositoryImpl;

import dagger.Module;
import dagger.Provides;


@Module
public class PresenterModule {

    @Provides
    LoginRepository getLoginRepository(FirebaseService firebaseService) {
        return new LoginRepositoryImpl(firebaseService);
    }

}


