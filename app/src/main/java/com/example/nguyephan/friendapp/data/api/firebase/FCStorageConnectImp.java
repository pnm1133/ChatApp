package com.example.nguyephan.friendapp.data.api.firebase;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.example.nguyephan.friendapp.data.pojo.firebase.FireRequest;
import com.example.nguyephan.friendapp.data.pojo.firebase.FireResponse;
import com.example.nguyephan.friendapp.di.AcContext;
import com.example.nguyephan.friendapp.di.AppContext;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.Objects;
import javax.inject.Inject;
import io.reactivex.subjects.PublishSubject;

public class FCStorageConnectImp implements FCStorageConnect {
    private static final String TAG = "FCStorageConnectImp";
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private PublishSubject<FireResponse> subject = PublishSubject.create();
    private FireResponse mFireResponse;

    @Inject
    public FCStorageConnectImp() {
    }

    @Override
    public PublishSubject<FireResponse> saveAvatarToRemote(FireRequest fireRequest) {
        if(fireRequest.getUriAvatar() == null){
            return  null;
        }
        uploadAvatarToRemote(fireRequest);

        return subject;
    }

    private void uploadAvatarToRemote(FireRequest fireRequest) {
        mFireResponse = new FireResponse();
        StorageReference reference = storage.getReference();
        StorageReference childReference = reference.child("albumsAvatar/" + firebaseAuth.getUid());

        Uri avatar = Uri.parse(fireRequest.getUriAvatar());
        UploadTask uploadTask = childReference.putFile(avatar);

        uploadTask.addOnFailureListener(exception -> {
            subject.onError(exception);
        }).addOnSuccessListener(taskSnapshot -> {
            Task<Uri> uriTask = childReference.getDownloadUrl();
            uriTask.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.e(TAG, "Path : " + task.getResult().getEncodedSchemeSpecificPart());
                    mFireResponse.setMessage(task.getResult().getEncodedSchemeSpecificPart());
                    subject.onNext(mFireResponse);
                    subject.onComplete();
                } else {
                    subject.onError(Objects.requireNonNull(task.getException()));
                }
            });
        });
    }
}
