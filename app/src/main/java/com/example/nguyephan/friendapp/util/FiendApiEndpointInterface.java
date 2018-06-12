package com.example.nguyephan.friendapp.util;

import com.example.nguyephan.friendapp.data.pojo.ConversationResponse;
import com.example.nguyephan.friendapp.data.pojo.FriendResponse;
import com.example.nguyephan.friendapp.data.pojo.UserRequest;
import com.example.nguyephan.friendapp.data.pojo.chat.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FiendApiEndpointInterface {
    @POST("getInforUser/user/create/")
    Call<ResponseBody> uploadUser(@Body UserRequest userRequest);

    @POST("getInforUser/user/listconversation/")
    Call<ConversationResponse> conversation(@Body UserRequest userRequest);

    @POST("getInforUser/user/list-friend-online/")
    Call<FriendResponse> friend(@Body UserRequest userRequest);

    @POST("getInforUser/user/get-conversation-member/{conversationId}")
    Call<ConversationResponse> member(@Path("conversationId")String conversationId, @Body UserRequest userRequest);
}
