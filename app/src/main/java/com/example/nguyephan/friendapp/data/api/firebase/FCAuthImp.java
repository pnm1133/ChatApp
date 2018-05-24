package com.example.nguyephan.friendapp.data.api.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.nguyephan.friendapp.data.pojo.firebase.FireResponse;
import com.example.nguyephan.friendapp.data.pojo.firebase.FireRequestAuth;
import com.example.nguyephan.friendapp.di.AcContext;
import com.example.nguyephan.friendapp.util.TextConstrant.TextConstant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import javax.inject.Inject;

/**
 * Created by nguye phan on 5/11/2018.
 */

public class FCAuthImp implements FCAuthConnect {

    private static final String TAG = "FireBaseConnectAuth";
    private FirebaseAuth mFireBaseAuth;
    private AppCompatActivity mContext;
    private FireResponse mFirebaseResponse = new FireResponse();

    @Inject
    public FCAuthImp(@AcContext Context context) {
        if (context instanceof AppCompatActivity) {
            mContext = (AppCompatActivity) context;
        }
    }

    @Override
    public boolean isCurrentUser() {
        mFireBaseAuth = FirebaseAuth.getInstance();
        return mFireBaseAuth.getCurrentUser() != null;
    }

    @Override
    public void signIn(@NonNull FireRequestAuth fireRequestAuth) {

        mFireBaseAuth
                .signInWithEmailAndPassword(fireRequestAuth.getEmail(), fireRequestAuth.getPassword())
                .addOnCompleteListener(mContext, task -> {
                    if (task.isSuccessful()) {
                        mFirebaseResponse.setMessage(TextConstant.RESPONSE_OK);
                        listenerFirebase.listenResponse(mFirebaseResponse);
                    } else {
                        try {
                            String s = task.getException().getMessage();
                            mFirebaseResponse.setMessage(s);
                            listenerFirebase.listenResponse(mFirebaseResponse);
                        } catch (NullPointerException e) {
                            e.toString();
                        }
                    }
                });
    }

    @Override
    public void newAccCount(@NonNull FireRequestAuth fireRequestAuth) {

        mFireBaseAuth
                .createUserWithEmailAndPassword(fireRequestAuth.getEmail(), fireRequestAuth.getPassword())
                .addOnCompleteListener(mContext, task -> {
                    if (task.isSuccessful()) {
                        updateUser(fireRequestAuth);
                    } else {
                        try {
                            String s = task.getException().getMessage();
                            mFirebaseResponse.setMessage(s);
                            listenerFirebase.listenResponse(mFirebaseResponse);
                        } catch (NullPointerException e) {
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void updateUser(FireRequestAuth fireRequestAuth) {
        FirebaseUser user = mFireBaseAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(fireRequestAuth.getUserName())
                .setPhotoUri(fireRequestAuth.getUriAvatar())
                .build();
        if (user != null) {
            user.updateProfile(profileUpdate)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            mFirebaseResponse.setMessage(TextConstant.RESPONSE_OK);
                            listenerFirebase.listenResponse(mFirebaseResponse);
                        } else {
                            String s = task.getException().getMessage();
                            mFirebaseResponse.setMessage(s);
                            listenerFirebase.listenResponse(mFirebaseResponse);
                        }
                    });
        }
    }

    @Override
    public void signOut() {

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
