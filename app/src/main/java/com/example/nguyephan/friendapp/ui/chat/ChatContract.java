package com.example.nguyephan.friendapp.ui.chat;

import com.example.nguyephan.friendapp.data.pojo.Conversation;
import com.example.nguyephan.friendapp.di.AcScope;
import com.example.nguyephan.friendapp.ui.base.BaseContract;

import java.util.List;

public interface ChatContract {

    interface View extends BaseContract.View{

        void startMainAc();

//        void showListConversation(List<Conversation> conversations);

        void signOut();

    }

    @AcScope
    interface Presenter<V extends View> extends BaseContract.Presenter<V>{

        void getListConversation();

        void singOut();

    }
}
