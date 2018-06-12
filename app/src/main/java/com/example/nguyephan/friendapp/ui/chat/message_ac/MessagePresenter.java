package com.example.nguyephan.friendapp.ui.chat.message_ac;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.nguyephan.friendapp.data.api.firebase.FCAuthConnect;
import com.example.nguyephan.friendapp.data.api.firebase.FCManager;
import com.example.nguyephan.friendapp.data.api.firebase.FCStorageConnect;
import com.example.nguyephan.friendapp.data.pojo.ConversationResponse;
import com.example.nguyephan.friendapp.data.pojo.chat.Message;
import com.example.nguyephan.friendapp.data.pojo.chat.User;
import com.example.nguyephan.friendapp.data.repository.DataManager;
import com.example.nguyephan.friendapp.data.repository.Pres;
import com.example.nguyephan.friendapp.ui.base.BasePresenter;
import com.example.nguyephan.friendapp.util.scheduler.Schedule;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;

public class MessagePresenter<V extends MessageContact.View> extends BasePresenter<V>
        implements MessageContact.Presenter<V> {
    private static final String TAG = MessagePresenter.class.getSimpleName();

    @Inject
    MessagePresenter(@NonNull DataManager dataManager,
                     @NonNull Schedule schedule,
                     @NonNull CompositeDisposable compositeDisposable,
                     FCManager fcManager,
                     FCAuthConnect FCAuthConnect,
                     FCStorageConnect fcStorageConnect,
                     Pres pres) {
        super(dataManager, schedule, compositeDisposable, fcManager, FCAuthConnect, fcStorageConnect, pres);
    }


    @Override
    public void messageConversation() {
        String conversationId
                = getView().getConversation().getId();

        getCompositeDisposable().add(getFCManager().getMessage(conversationId)
                .doOnNext(this::getMember)
                .doOnError(throwable -> Log.e(TAG, throwable.toString()))
                .subscribe());

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "detach");
    }

    private void getMember(ConversationResponse conversationResponseMessage) {
        getCompositeDisposable().add(getFCManager().getMember(getView().getConversation().getId())
                .doOnNext(conversationResponseMember -> {
                    if (conversationResponseMessage == null) {
                        return;
                    }
                    List<Message> list = new ArrayList<>();
                    for (int i = 0; i < conversationResponseMessage.getMessages().size(); i++) {
                        Message message = conversationResponseMessage.getMessages().get(i);
                        for (int j = 0; j < conversationResponseMember.getUsers().size(); j++) {
                            User user = conversationResponseMember.getUsers().get(j);
                            if (TextUtils.equals(message.getSenderId(), user.getId())) {
                                message.setUser(user);
                            }
                        }
                        if (message.getUser() != null) {
                            list.add(message);
                        }
                    }
                    getView().showMessage(list);
                })
                .doOnError(throwable -> {
                    Log.e(TAG, throwable.toString());
                })
                .subscribe());
    }
}
