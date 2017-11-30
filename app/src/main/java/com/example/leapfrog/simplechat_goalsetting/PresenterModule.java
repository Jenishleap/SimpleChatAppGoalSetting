package com.example.leapfrog.simplechat_goalsetting;

import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.FirebaseService;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login.LoginRepository;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login.LoginRepositoryImpl;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dagger.Module;
import dagger.Provides;


@Module
public class PresenterModule {

    @Provides
    LoginRepository getLoginRepository(FirebaseService firebaseService) {
        return new LoginRepositoryImpl(firebaseService);
    }

    @Provides
    public DatabaseReference getDataBaseReference() {
        return FirebaseDatabase.getInstance().getReference(Config.USERS);
    }

}


