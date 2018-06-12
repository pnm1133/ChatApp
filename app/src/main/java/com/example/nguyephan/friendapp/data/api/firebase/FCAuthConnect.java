package com.example.nguyephan.friendapp.data.api.firebase;

import android.support.annotation.NonNull;

import com.example.nguyephan.friendapp.data.pojo.firebase.FireRequest;
import com.example.nguyephan.friendapp.data.pojo.firebase.FireResponse;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by nguye phan on 5/11/2018.
 */

public interface FCAuthConnect {

    boolean isCurrentUser();

    void signIn(@NonNull FireRequest fireRequest);

    PublishSubject<FireResponse> newAccCount(@NonNull FireRequest fireRequest);

    void updateUser(FireRequest fireRequest);

    void setListenerFirebase(FCAuthImp.ListenerFirebase listenerFirebase);

    void signOut();
}
