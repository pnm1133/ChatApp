package com.example.nguyephan.friendapp.data.api.firebase;

import android.support.annotation.NonNull;

import com.example.nguyephan.friendapp.data.pojo.ConversationResponse;

import io.reactivex.subjects.PublishSubject;

public interface FCFirestoreConnect {

    void readInforUser();

    void saveInfoUser();

    PublishSubject<ConversationResponse> getConversationMessage(@NonNull String idConversation);

    PublishSubject<ConversationResponse> getConversationMember(@NonNull String idConversation);

}
