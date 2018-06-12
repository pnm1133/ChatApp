package com.example.nguyephan.friendapp.util.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.nguyephan.friendapp.data.pojo.Conversation;
import com.example.nguyephan.friendapp.data.pojo.chat.User;
import com.example.nguyephan.friendapp.di.AcContext;
import com.example.nguyephan.friendapp.ui.chat.ChatAc;
import com.example.nguyephan.friendapp.ui.chat.message_ac.MessageAc;
import com.example.nguyephan.friendapp.ui.main.MainAc;
import com.stfalcon.chatkit.commons.models.IDialog;

import javax.inject.Inject;

public class Navigation {

    private Context context;

    @Inject
    public Navigation(@AcContext Context context) {
        this.context = context;
    }


    public void startMainAc(@Nullable Bundle bundle,
                            @Nullable String key){
        Intent intent = new Intent(context, MainAc.class);
        intent.putExtra(key,bundle);
        context.startActivity(intent);
    }


    public void startChatAc(@Nullable Bundle bundle,
                            @Nullable String key){
        Intent intent = new Intent(context, ChatAc.class);
        intent.putExtra(key,bundle);
        context.startActivity(intent);
    }

    public void startMessageAc(Conversation conversation){
        Intent intent = new Intent(context, MessageAc.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("conversation",conversation);
        intent.putExtra("conversations",bundle);
        context.startActivity(intent);
    }
}
