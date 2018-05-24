package com.example.nguyephan.friendapp.data.api.firebase;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nguyephan.friendapp.di.AcContext;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

public class FCStorageConnectImp implements FCStorageConnect {


    private static final String TAG = "FCStorageConnectImp";
    private FirebaseStorage storage = FirebaseStorage.getInstance();


    private Context context;

    @Inject
    public FCStorageConnectImp(@AcContext Context context) {
        this.context = context;
    }


    @Override
    public void addAvatar() {
        StorageReference reference = storage.getReference();
        StorageReference childReference =  reference.child("albumsAvatar/chatLogo.png");
        UploadTask uploadTask = null;

        Log.e(TAG,"addAvatar");

        try {
            InputStream stream = context.getAssets().open("ChatLogo.png");
            uploadTask = childReference.putStream(stream);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Log.e(TAG,"error");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    Log.e(TAG,"success");
                    Task<Uri> uriTask = childReference.getDownloadUrl();
                    uriTask.addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){
                                Log.e(TAG,"Path : "+task.getResult().getEncodedSchemeSpecificPart());

                            }else {
                                Log.e(TAG,"Path : faile ");
                            }
                        }
                    });
                }
            });

        } catch (IOException e) {
            Log.e(TAG,e.toString());
        }
    }
}
