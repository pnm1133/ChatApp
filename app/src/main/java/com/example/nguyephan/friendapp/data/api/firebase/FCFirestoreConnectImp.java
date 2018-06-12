package com.example.nguyephan.friendapp.data.api.firebase;

import android.accounts.AuthenticatorException;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nguyephan.friendapp.data.pojo.ConversationResponse;
import com.example.nguyephan.friendapp.data.pojo.UserRequest;
import com.example.nguyephan.friendapp.data.pojo.chat.Message;
import com.example.nguyephan.friendapp.data.repository.Pres;
import com.example.nguyephan.friendapp.di.AcContext;
import com.example.nguyephan.friendapp.util.FiendApiEndpointInterface;
import com.example.nguyephan.friendapp.util.TextConstrant.HttpConstrant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.subjects.PublishSubject;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FCFirestoreConnectImp
        implements FCFirestoreConnect {

    private static final String TAG = FCFirestoreConnectImp.class.getSimpleName();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static final String BASE_URL = "https://us-central1-android-code-lab.cloudfunctions.net/";
    private Context context;
    private static final String CONVERSATION_COLLECTION = "conversations";
    private static final String MESSAGE_COLLECTION = "messages";
    private static final String CREATE_DATE_FIELD = "createAt";
    private static final String MEMBER_COLLECTION = "members";
    private Pres mPresLocal;


    @Inject
    FCFirestoreConnectImp(@AcContext Context context, Pres pres) {
        this.context = context;
        this.mPresLocal = pres;
    }

    @Override
    public PublishSubject<ConversationResponse> getConversationMessage(@NonNull String idConversation) {

        PublishSubject<ConversationResponse> subject = PublishSubject.create();

        firestore.collection(CONVERSATION_COLLECTION)
                .document(idConversation)
                .collection(MESSAGE_COLLECTION)
                .orderBy(CREATE_DATE_FIELD, Query.Direction.DESCENDING)
                .limit(10)
                .addSnapshotListener((snapshot, e) -> {
                    if (e != null) {
                        Log.e(TAG, e.toString());
                        return;
                    }

                    if (snapshot != null && !snapshot.isEmpty()) {
                        ConversationResponse conversationResponse = new ConversationResponse();
                        Message[] messages = new Message[snapshot.size()];

                        for (int i = 0; i < snapshot.size(); i++) {
                            int j = snapshot.size() - (i + 1);
                            DocumentSnapshot snap = snapshot.getDocuments().get(i);

                            Message message = new Message();
                            message.setCreatedAt(snap.getDate("createAt"));
                            message.setText(snap.getString("text"));
                            message.setStatus(snap.getString("status"));
                            message.setType(snap.getString("type"));
                            message.setSenderId(snap.getString("senderId"));
                            message.setId(snap.getId());
                            messages[j] = message;
                        }
                        conversationResponse.setMessages(Arrays.asList(messages));
                        subject.onNext(conversationResponse);
                        subject.onComplete();
                    }
                });


        return subject;
    }

    @Override
    public PublishSubject<ConversationResponse> getConversationMember(@NonNull String idConversation) {

        PublishSubject<ConversationResponse> subject = PublishSubject.create();

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

        UserRequest userRequest =
                new UserRequest(mPresLocal.currentToken(), null);

        FiendApiEndpointInterface apiService = retrofit.create(FiendApiEndpointInterface.class);
        Call<ConversationResponse> call = apiService.member(idConversation, userRequest);
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
    public void readInforUser() {
        if (firebaseAuth.getCurrentUser() == null) {
            Log.e(TAG, "null user");
            return;
        }

        xacThuc();
    }

    @Override
    public void saveInfoUser() {

    }


    private void xacThuc() {
        Objects.requireNonNull(firebaseAuth.getCurrentUser()).getIdToken(true).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String idToken = task.getResult().getToken();
                Log.e(TAG, idToken);
            } else {
                Log.e(TAG, "errrrrrrrrrrrrrrrrrrrrrro");
            }
        });
    }

}
