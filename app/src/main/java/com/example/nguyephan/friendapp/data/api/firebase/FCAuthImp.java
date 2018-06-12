package com.example.nguyephan.friendapp.data.api.firebase;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.nguyephan.friendapp.data.pojo.firebase.FireResponse;
import com.example.nguyephan.friendapp.data.pojo.firebase.FireRequest;
import com.example.nguyephan.friendapp.data.repository.Pres;
import com.example.nguyephan.friendapp.di.AcContext;
import com.example.nguyephan.friendapp.util.TextConstrant.TextConstant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by nguye phan on 5/11/2018.
 */

public class FCAuthImp implements FCAuthConnect {

    private static final String TAG = FCAuthImp.class.getSimpleName();
    private FirebaseAuth mFireBaseAuth;
    private AppCompatActivity mContext;
    private FireResponse mFirebaseResponse = new FireResponse();
    private PublishSubject<FireResponse> subject;
    private Pres pres;

    @Inject
    public FCAuthImp(@AcContext Context context, Pres pres) {
        if (context instanceof AppCompatActivity) {
            mContext = (AppCompatActivity) context;
        }
        this.pres = pres;
    }

    @Override
    public boolean isCurrentUser() {
        mFireBaseAuth = FirebaseAuth.getInstance();
        if(mFireBaseAuth.getCurrentUser() != null){
            pres.saveToken();
        }
        return mFireBaseAuth.getCurrentUser() != null;
    }

    @Override
    public void signIn(@NonNull FireRequest fireRequest) {
        PublishSubject<FireResponse> subject = PublishSubject.create();
        mFireBaseAuth
                .signInWithEmailAndPassword(fireRequest.getEmail(), fireRequest.getPassword())
                .addOnCompleteListener(mContext, task -> {
                    if (task.isSuccessful()) {
                        mFirebaseResponse.setMessage(TextConstant.RESPONSE_OK);
                        listenerFirebase.listenResponse(mFirebaseResponse);
                        subject.onNext(mFirebaseResponse);
                    } else {
                        try {
                            String s = Objects.requireNonNull(task.getException()).getMessage();
                            mFirebaseResponse.setMessage(s);
                            subject.onNext(mFirebaseResponse);
                            subject.onError(task.getException());
                            listenerFirebase.listenResponse(mFirebaseResponse);
                        } catch (NullPointerException e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                });
    }

    @Override
    public PublishSubject<FireResponse> newAccCount(@NonNull FireRequest fireRequest) {
        subject = PublishSubject.create();
        mFireBaseAuth
                .createUserWithEmailAndPassword(fireRequest.getEmail(), fireRequest.getPassword())
                .addOnCompleteListener(mContext, task -> {
                    Log.e(TAG, fireRequest.getEmail());
                    if (task.isSuccessful()) {
                        mFirebaseResponse.setMessage(TextConstant.RESPONSE_OK);
                        subject.onNext(mFirebaseResponse);
                    } else {
                        subject.onError(Objects.requireNonNull(task.getException()));
                    }
                });
        return subject;
    }

    @Override
    public void updateUser(FireRequest fireRequest) {
        if(subject == null){
            return;
        }
        FirebaseUser user = mFireBaseAuth.getCurrentUser();
        Uri uriAvatar;
        if (fireRequest.getUriAvatar() == null) {
            uriAvatar = null;
        } else {
            uriAvatar = Uri.parse(fireRequest.getUriAvatar());
        }
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(fireRequest.getUserName())
                .setPhotoUri(uriAvatar)
                .build();
        if (user != null) {
            user.updateProfile(profileUpdate)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            subject.onComplete();
                        } else {
                            Exception exception = Objects.requireNonNull(task.getException());
                            subject.onError(exception);
                        }
                    });
        }
    }

    @Override
    public void signOut() {
        mFireBaseAuth = FirebaseAuth.getInstance();
        mFireBaseAuth.signOut();
        pres.resetToken();
    }

    private ListenerFirebase listenerFirebase;

    public interface ListenerFirebase {
        void listenResponse(FireResponse response);
    }

    @Override
    public void setListenerFirebase(ListenerFirebase listenerFirebase) {
        this.listenerFirebase = listenerFirebase;
    }
}
