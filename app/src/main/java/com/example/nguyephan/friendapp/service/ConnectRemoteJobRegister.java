package com.example.nguyephan.friendapp.service;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.nguyephan.friendapp.FriendApp;
import com.example.nguyephan.friendapp.R;
import com.example.nguyephan.friendapp.data.api.firebase.FCStorageConnect;
import com.example.nguyephan.friendapp.data.pojo.UserRequest;
import com.example.nguyephan.friendapp.data.pojo.chat.User;
import com.example.nguyephan.friendapp.data.pojo.firebase.FireRequest;
import com.example.nguyephan.friendapp.data.pojo.firebase.FireResponse;
import com.example.nguyephan.friendapp.di.Component.DaggerServiceComponent;
import com.example.nguyephan.friendapp.di.Module.ServiceMoudle;
import com.example.nguyephan.friendapp.util.FiendApiEndpointInterface;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectRemoteJobRegister extends JobService {
    private static final String TAG = ConnectRemoteJobRegister.class.getSimpleName();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    private Disposable disposable;
    private User user;

    @Inject
    public FCStorageConnect mFCStorageConnect;

    @Override
    public boolean onStartJob(JobParameters job) {
        DaggerServiceComponent
                .builder()
                .appComponent(FriendApp.appComponent())
                .serviceMoudle(new ServiceMoudle())
                .build()
                .inject(this);

        uploadAvatarUser(job);

        return firebaseAuth == null;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        Log.e(TAG, "stop job");
        return firebaseAuth == null;
    }

    private void uploadAvatarUser(JobParameters job) {
        String fireRequest =
                Objects.requireNonNull(job.getExtras())
                        .getString(String.valueOf(R.string.firebaseRequest_key));
        Gson gson = new Gson();
        FireRequest request = gson.fromJson(fireRequest, FireRequest.class);

        mFCStorageConnect
                .saveAvatarToRemote(request)
                .subscribe(new Observer<FireResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(FireResponse fireResponse) {
                        if (fireResponse.getMessage() != null) {
                            String uid = firebaseAuth.getUid();
                            String name = request.getUserName();
                            String avatar = String.format("https:%s", fireResponse.getMessage());
                            user = new User(uid, name, avatar, true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        updateUserInfoToRemote();
                    }
                });
    }

    private void updateUserInfoToRemote() {
        if (currentUser == null) {
            return;
        }
        Gson gson = new Gson();
        PublishSubject<String> subject = PublishSubject.create();
        currentUser
                .getIdToken(true)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        subject.onNext(Objects.requireNonNull(task.getResult().getToken()));
                    } else {
                        subject.onError(Objects.requireNonNull(task.getException()));
                    }
                });

        subject.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                UserRequest userRequest = new UserRequest(s, user);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getString(R.string.firebase_cloud_function_http))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                FiendApiEndpointInterface apiService =
                        retrofit.create(FiendApiEndpointInterface.class);
                Call<okhttp3.ResponseBody> call =
                        apiService.uploadUser(userRequest);
                call.enqueue(new Callback<okhttp3.ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<okhttp3.ResponseBody> call,
                                           @NonNull Response<okhttp3.ResponseBody> response) {
                        onComplete();
                    }

                    @Override
                    public void onFailure(@NonNull Call<okhttp3.ResponseBody> call,
                                          @NonNull Throwable t) {
                        onError(t);
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.toString());
            }

            @Override
            public void onComplete() {
                saveToCache();
            }
        });
    }


    private void saveToCache() {

    }

}
