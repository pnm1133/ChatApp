package com.example.nguyephan.friendapp.ui.chat.message_fr;

import android.accounts.AuthenticatorException;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nguyephan.friendapp.data.api.firebase.FCAuthConnect;
import com.example.nguyephan.friendapp.data.api.firebase.FCManager;
import com.example.nguyephan.friendapp.data.api.firebase.FCStorageConnect;
import com.example.nguyephan.friendapp.data.pojo.ConversationResponse;
import com.example.nguyephan.friendapp.data.pojo.Friend;
import com.example.nguyephan.friendapp.data.pojo.FriendResponse;
import com.example.nguyephan.friendapp.data.repository.DataManager;
import com.example.nguyephan.friendapp.data.repository.Pres;
import com.example.nguyephan.friendapp.ui.base.BasePresenter;
import com.example.nguyephan.friendapp.util.scheduler.Schedule;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;

public class DialogChatFrPresenter<V extends DialogChatContract.view> extends BasePresenter<V>
        implements DialogChatContract.presenter<V> {
    private static final String TAG = DialogChatFrPresenter.class.getSimpleName();
    private static final int DELAY_REPEAT_TIME = 1000;
    private static final int INTERVAL_TIME = 10000;
    private FCManager mFCManager;

    @Inject
    DialogChatFrPresenter(@NonNull DataManager dataManager,
                          @NonNull Schedule schedule,
                          @NonNull CompositeDisposable compositeDisposable,
                          FCManager fcManager,
                          FCAuthConnect FCAuthConnect,
                          FCStorageConnect fcStorageConnect,
                          Pres pres) {
        super(dataManager, schedule, compositeDisposable, fcManager, FCAuthConnect, fcStorageConnect, pres);

    }

    @Override
    public void onAttach(V v) {
        super.onAttach(v);
        getView().showChatShimmer();
    }

    @Override
    public void getListConversation() {
        getCompositeDisposable().add(getFCManager()
                .listConversation()
                .subscribeOn(getScheduler().io())
                .retryWhen(throwableObservable -> throwableObservable.flatMap((Function<Throwable, ObservableSource<?>>)
                        throwable -> {
                            if (throwable instanceof AuthenticatorException) {
                                Log.e(TAG, throwable.toString() + Thread.currentThread());
                                return Observable.just(getFCManager().listConversation())
                                        .delay(DELAY_REPEAT_TIME, TimeUnit.MILLISECONDS);
                            } else {
                                return Observable.error(throwable);
                            }
                        }
                ))
                .timeout(INTERVAL_TIME, TimeUnit.MILLISECONDS)
                .onErrorResumeNext(throwable -> {
                    ConversationResponse conversationResponse = new ConversationResponse();
                    conversationResponse.setError(throwable);
                    return Observable.just(conversationResponse);
                })
                .doOnError(throwable -> {
                    if (throwable instanceof SocketTimeoutException) {
                        //Handle socket timeout in here
                    } else {
                        //Handle orthers error in here
                    }
                })
                .observeOn(getScheduler().main())
                .doOnNext(conversationResponse -> {
                    if (conversationResponse.getError() != null) {
                        Log.e(TAG, "error");
                        //Handle when getToken ger Error
                    } else if (conversationResponse.getConversation() == null) {
                        Log.e(TAG, "null conversation");
                        // Handle when user not have conversation
                        getView().showDialogChat(conversationResponse.getConversation());
                    } else {
                        Log.e(TAG, "ok");
                        getView().showDialogChat(conversationResponse.getConversation());
                    }
                })
                .subscribe());
    }

    @Override
    public void getListFriends() {
        getCompositeDisposable().add(getFCManager()
                .listFriend()
                .subscribeOn(getScheduler().io())
                .retryWhen(throwableObservable -> throwableObservable.flatMap((Function<Throwable, ObservableSource<?>>)
                        throwable -> {
                            if (throwable instanceof AuthenticatorException) {
                                Log.e(TAG, throwable.toString() + Thread.currentThread());
                                return Observable.just(getFCManager().listConversation())
                                        .delay(DELAY_REPEAT_TIME, TimeUnit.MILLISECONDS);
                            } else {
                                return Observable.error(throwable);
                            }
                        }
                ))
                .timeout(INTERVAL_TIME, TimeUnit.MILLISECONDS)
                .onErrorResumeNext(throwable -> {
                    FriendResponse friendResponse = new FriendResponse();
                    friendResponse.setError(throwable);
                    return Observable.just(friendResponse);
                })
                .doOnError(throwable -> {
                    if (throwable instanceof SocketTimeoutException) {
                        //Handle socket timeout in here
                    } else {
                        //Handle orthers error in here
                    }
                })
                .observeOn(getScheduler().main())
                .doOnError(throwable -> {
                    if (throwable instanceof SocketTimeoutException) {
                        Log.e(TAG, "socket Time out error");
                        //Handle socket timeout in here
                    } else {
                        Log.e(TAG, throwable.toString());
                        //Handle orthers error in here
                    }
                })
                .doOnNext(friendResponse -> {
                    if(friendResponse.getFriends() == null){
                        getView().stopChatShimmer();
                        return;
                    }

                    for (Friend friend : friendResponse.getFriends()){
                        if(friend.getAvatar() == null){
                            //defalt image
                            friend.setAvatar("http://i.imgur.com/pv1tBmT.png");
                        }
                    }
                    getView().showFriendDialog(friendResponse.getFriends());
                })
                .subscribe());
    }

    @Override
    public boolean isHaveConversation() {

        return false;
    }

    @Override
    public boolean isHaveFriends() {
        return false;
    }

}
