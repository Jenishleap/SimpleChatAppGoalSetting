package com.example.leapfrog.simplechat_goalsetting;

import com.example.leapfrog.simplechat_goalsetting.annotations.Reference1;
import com.example.leapfrog.simplechat_goalsetting.annotations.Reference2;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.FirebaseService;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.UserDetails;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login.LoginRepository;
import com.example.leapfrog.simplechat_goalsetting.firebase.onetoone.login.LoginRepositoryImpl;
import com.firebase.client.Firebase;
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

    @Reference1
    @Provides
    public Firebase getFirebaseReference1() {
        return new Firebase(Config.BASE_URL + Config.MESSAGES + "/" + UserDetails.username + "_" + UserDetails.chatWith);
    }

    @Reference2
    @Provides
    public Firebase getFirebaseReference2() {
        return new Firebase(Config.BASE_URL + Config.MESSAGES + "/" + UserDetails.chatWith + "_" + UserDetails.username);
    }

}


