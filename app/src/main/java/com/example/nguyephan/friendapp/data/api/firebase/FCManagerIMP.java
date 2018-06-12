package com.example.nguyephan.friendapp.data.api.firebase;

import android.accounts.AuthenticatorException;
import android.app.AuthenticationRequiredException;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nguyephan.friendapp.data.pojo.ConversationResponse;
import com.example.nguyephan.friendapp.data.pojo.FriendResponse;
import com.example.nguyephan.friendapp.data.pojo.UserRequest;
import com.example.nguyephan.friendapp.data.repository.Pres;
import com.example.nguyephan.friendapp.util.FiendApiEndpointInterface;
import com.example.nguyephan.friendapp.util.TextConstrant.HttpConstrant;
import com.example.nguyephan.friendapp.util.TextConstrant.TextConstant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FCManagerIMP implements FCManager {
    private static final String TAG = FCManager.class.getSimpleName();
    private static final String BASE_URL = "https://us-central1-android-code-lab.cloudfunctions.net/";
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private Pres mPresLocal;
    private FCFirestoreConnect mFirebaseStorage;

    @Inject
    FCManagerIMP(@NonNull Pres presLocal, FCFirestoreConnect fcFirestoreConnect) {
        this.mPresLocal = presLocal;
        this.mFirebaseStorage = fcFirestoreConnect;

    }

    @Override
    public PublishSubject<ConversationResponse> listConversation() {
        PublishSubject<ConversationResponse> subject = PublishSubject.create();

        UserRequest userRequest =
                new UserRequest(mPresLocal.currentToken(), null);

        OkHttpClient client =
                new OkHttpClient.Builder()
                        .connectTimeout(100, TimeUnit.SECONDS)
                        .readTimeout(100, TimeUnit.SECONDS)
                        .build();

        Gson gson =
                new Gson();

        Retrofit retrofit =
                new Retrofit.Builder().baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(client)
                        .build();

        FiendApiEndpointInterface apiService = retrofit.create(FiendApiEndpointInterface.class);

        Call<ConversationResponse> call = apiService.conversation(userRequest);

        call.enqueue(new Callback<ConversationResponse>() {
            @Override
            public void onResponse(@NonNull Call<ConversationResponse> call,
                                   @NonNull Response<ConversationResponse> response) {
                ConversationResponse conversationResponse = response.body();

                if (response.code() == HttpConstrant.RESPONSE_OK_STATUS) {
                    if (conversationResponse != null) {
                        subject.onNext(conversationResponse);
                        subject.onComplete();
                    }
                } else if (response.code() == HttpConstrant.ERROR_AUTH_STATUS) {
                    //reload token from firebase
                    mPresLocal.saveToken();
                    subject.onError(new AuthenticatorException());
                    subject.onComplete();
                } else {
                    //status 500 , then should repeat
                    Log.e(TAG, "token error response code : " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ConversationResponse> call,
                                  @NonNull Throwable t) {
                subject.onError(t);
                subject.onComplete();
            }
        });


        return subject;
    }

    @Override
    public PublishSubject<FriendResponse> listFriend() {
        PublishSubject<FriendResponse> subject = PublishSubject.create();
        if (firebaseAuth == null) {
            return subject;
        }
        UserRequest userRequest =
                new UserRequest(mPresLocal.currentToken(), null);

        OkHttpClient client =
                new OkHttpClient.Builder()
                        .connectTimeout(100, TimeUnit.SECONDS)
                        .readTimeout(100, TimeUnit.SECONDS)
                        .build();
        Gson gson =
                new Gson();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        FiendApiEndpointInterface apiService = retrofit.create(FiendApiEndpointInterface.class);

        Call<FriendResponse> call = apiService.friend(userRequest);
        call.enqueue(new Callback<FriendResponse>() {
            @Override
            public void onResponse(@NonNull Call<FriendResponse> call,
                                   @NonNull Response<FriendResponse> response) {
                FriendResponse friend = response.body();
                if (response.code() == HttpConstrant.RESPONSE_OK_STATUS) {
                    if (friend != null) {
                        subject.onNext(friend);
                        subject.onComplete();
                    }
                } else if (response.code() == HttpConstrant.ERROR_AUTH_STATUS) {
                    mPresLocal.saveToken();
                    subject.onError(new AuthenticatorException());
                    subject.onComplete();
                } else {
                    Log.e(TAG, "token error response code : " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<FriendResponse> call,
                                  @NonNull Throwable t) {
                subject.onError(t);
                subject.onComplete();
            }
        });

        return subject;
    }

    @Override
    public PublishSubject<ConversationResponse> getMessage(String conversationId) {
        return mFirebaseStorage.getConversationMessage(conversationId);
    }

    @Override
    public PublishSubject<ConversationResponse> getMember(String conversationId) {
        return mFirebaseStorage.getConversationMember(conversationId);
    }


}
