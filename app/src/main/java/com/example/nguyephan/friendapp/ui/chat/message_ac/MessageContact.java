package com.example.nguyephan.friendapp.ui.chat.message_ac;

import com.example.nguyephan.friendapp.data.pojo.Conversation;
import com.example.nguyephan.friendapp.data.pojo.chat.Message;
import com.example.nguyephan.friendapp.ui.base.BaseContract;

import java.util.List;

public interface MessageContact {

    interface View extends BaseContract.View{

        void replaceHomePage();

        Conversation getConversation();

        void showMessage(List<Message> messages);

    }

    interface Presenter<V extends View> extends BaseContract.Presenter<V>{

        void messageConversation();

    }

}
