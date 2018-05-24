package com.example.nguyephan.friendapp.data.api.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nguyephan.friendapp.di.AcContext;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.grpc.okhttp.OkHttpChannelBuilder;
import io.grpc.okhttp.OkHttpChannelProvider;

public class FCFirestoreImp implements FCFirestoreConnect {

    private static final String TAG = "FCFirestore";

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Inject
    public FCFirestoreImp(@AcContext Context context) {

    }


    @Override
    public void readInforUser() {
        if(firebaseAuth.getCurrentUser() == null){
            Log.e(TAG, "null user");
            return;
        }

        xacThuc();

//        firestore
//                .collection("users")
//                .whereEqualTo("userId",firebaseAuth.getUid())
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            Log.e(TAG,  " => xcxcx " + document.getData());
//                        }
//                    } else {
//                        Log.e(TAG, "Error getting documents.", task.getException());
//                    }
//
//                });
    }

    private void xacThuc(){
        Objects.requireNonNull(firebaseAuth.getCurrentUser()).getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if(task.isSuccessful()){
                    String idToken = task.getResult().getToken();
                    Log.e(TAG,idToken);
                }else {
                    Log.e(TAG,"errrrrrrrrrrrrrrrrrrrrrro");
                }
            }
        });
    }


    private void getFriends(QueryDocumentSnapshot queryDocumentSnapshot){
        firestore
                .collection("users")
                .document(queryDocumentSnapshot.getId())
                .collection("Friend")
                .document("InC7culUtkCYm9kYjg2s")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG, task.getResult().getId() + " => " + task.getResult().getData());
                        } else {
                            Log.e(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }



    private void addUser(){
        Map<String, Object> user = new HashMap<>();
        user.put("age",20);
        user.put("name","Julia Goldman");
        user.put("avatar","http://i.imgur.com/Qn9UesZ.png");

        firestore.collection("users")
                .add(user)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG, task.getResult().getId() + " => " + task.getResult().getPath());
                        } else {
                            Log.e(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }


    public void postData(String token) {
        
        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON,"{\"idToken\":\""+token+"}");

        Request request = new Request.Builder()
                .url("https://us-central1-android-code-lab.cloudfunctions.net/getInforUser/")
                .post(requestBody)
                .addHeader("Authorization", "header value")
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

                Log.e("HttpService", "onFailure() Request was: " + request);

                e.printStackTrace();
            }

            @Override
            public void onResponse(Response r) throws IOException {

                String response = r.message();

                Log.e("response ", "onResponse(): " + response );

            }
        });

    }

}
