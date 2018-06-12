package com.example.nguyephan.friendapp.data.api.firebase;

import com.example.nguyephan.friendapp.data.pojo.Conversation;
import com.example.nguyephan.friendapp.data.pojo.ConversationResponse;
import com.example.nguyephan.friendapp.data.pojo.FriendResponse;
import com.example.nguyephan.friendapp.data.pojo.firebase.FireRequest;

import io.reactivex.subjects.PublishSubject;

public interface FCManager  {


    PublishSubject<ConversationResponse> listConversation();

    PublishSubject<FriendResponse> listFriend();

    PublishSubject<ConversationResponse> getMessage(String conversationId);

    PublishSubject<ConversationResponse> getMember(String conversationId);

}
