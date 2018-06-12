package com.example.nguyephan.friendapp.ui.chat.message_fr;

import android.graphics.drawable.Drawable;

import com.example.nguyephan.friendapp.data.pojo.Conversation;
import com.example.nguyephan.friendapp.data.pojo.Friend;
import com.example.nguyephan.friendapp.ui.base.BaseContract;

import java.util.List;

public interface DialogChatContract {

    interface view extends BaseContract.View{

        void showDialogChat(List<Conversation> conversations);

        void showChatShimmer();

        void stopChatShimmer();

        void showFriendDialog(List<Friend> friends);

    }

    interface presenter<V extends view> extends BaseContract.Presenter<V>{

        void getListConversation();

        void getListFriends();

        boolean isHaveConversation();

        boolean isHaveFriends();

    }
}
