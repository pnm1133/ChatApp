package com.example.nguyephan.friendapp.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nguyephan.friendapp.di.AppContext;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by nguye phan on 5/9/2018.
 */

public class PresLocalIMP implements Pres {
    private static final String PRES_APP = "pre_app";
    private static final String TAG = PresLocalIMP.class.getSimpleName();
    private SharedPreferences preferences;


    @Inject
    public PresLocalIMP(@AppContext Context context) {
        preferences = context.getSharedPreferences(PRES_APP, Context.MODE_PRIVATE);
    }

    @Override
    public String currentToken() {
        return preferences.getString("token", "");
    }

    @Override
    public PublishSubject<Boolean> saveToken() {
        PublishSubject<Boolean> subject = PublishSubject.create();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Objects.requireNonNull(auth.getCurrentUser())
                .getIdToken(true).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.e(TAG,"Token => "+task.getResult().getToken());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("token", task.getResult().getToken()).apply();
                        subject.onNext(true);
                        subject.onComplete();
                    } else {
                        //error when delete account
                        subject.onError(Objects.requireNonNull(task.getException()));
                    }
                });

        return subject;
    }

    @Override
    public void resetToken() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", null).apply();
    }
}
