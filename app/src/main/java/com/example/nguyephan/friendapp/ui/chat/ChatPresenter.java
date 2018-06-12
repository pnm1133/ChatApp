package com.example.nguyephan.friendapp.ui.chat;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nguyephan.friendapp.data.api.firebase.FCAuthConnect;
import com.example.nguyephan.friendapp.data.api.firebase.FCManager;
import com.example.nguyephan.friendapp.data.api.firebase.FCStorageConnect;
import com.example.nguyephan.friendapp.data.pojo.Conversation;
import com.example.nguyephan.friendapp.data.pojo.ConversationResponse;
import com.example.nguyephan.friendapp.data.repository.DataManager;
import com.example.nguyephan.friendapp.data.repository.Pres;
import com.example.nguyephan.friendapp.ui.base.BasePresenter;
import com.example.nguyephan.friendapp.util.scheduler.Schedule;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ChatPresenter<V extends ChatContract.View> extends BasePresenter<V>
        implements ChatContract.Presenter<V> {
    private static final String TAG = ChatPresenter.class.getSimpleName();

    private FCManager mFCManager;

    @Inject
    public ChatPresenter(@NonNull DataManager dataManager,
                         @NonNull Schedule schedule,
                         @NonNull CompositeDisposable compositeDisposable,
                         FCManager fcManager,
                         FCAuthConnect FCAuthConnect,
                         FCStorageConnect fcStorageConnect,
                         Pres pres) {
        super(dataManager, schedule, compositeDisposable, fcManager, FCAuthConnect, fcStorageConnect, pres);
        this.mFCManager = fcManager;
    }


    @Override
    public void getListConversation() {
        mFCManager.listConversation().subscribe(new Observer<ConversationResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ConversationResponse conversationResponse) {
                List<Conversation> conversations = conversationResponse.getConversation();
//                getView().showListConversation(conversations);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.toString());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void singOut() {
        getFireBaseAuthConnect().signOut();
    }
}
