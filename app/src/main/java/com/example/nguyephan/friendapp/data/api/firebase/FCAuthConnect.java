package com.example.nguyephan.friendapp.data.api.firebase;

import android.support.annotation.NonNull;

import com.example.nguyephan.friendapp.data.pojo.firebase.FireRequestAuth;

/**
 * Created by nguye phan on 5/11/2018.
 */

public interface FCAuthConnect {

    boolean isCurrentUser();

    void signIn(@NonNull FireRequestAuth fireRequestAuth);

    void newAccCount(@NonNull FireRequestAuth fireRequestAuth);

    void updateUser(FireRequestAuth fireRequestAuth);

    void setListenerFirebase(FCAuthImp.ListenerFirebase listenerFirebase);

    void signOut();


}
