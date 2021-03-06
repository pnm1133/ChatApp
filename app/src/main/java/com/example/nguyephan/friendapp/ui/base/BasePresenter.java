package com.example.nguyephan.friendapp.ui.base;

import android.support.annotation.NonNull;

import com.example.nguyephan.friendapp.data.api.firebase.FCAuthConnect;
import com.example.nguyephan.friendapp.data.api.firebase.FCManager;
import com.example.nguyephan.friendapp.data.api.firebase.FCStorageConnect;
import com.example.nguyephan.friendapp.data.repository.DataManager;
import com.example.nguyephan.friendapp.data.repository.Pres;
import com.example.nguyephan.friendapp.util.scheduler.Schedule;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by nguye phan on 5/9/2018.
 */

public class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {

    private V v;

    private DataManager mDataManager;
    private Schedule mScheduler;
    private CompositeDisposable mCompositeDisposable;
    private FCAuthConnect mFCAuthConnect;
    private FCStorageConnect mFCStorageConnect;
    private FCManager mFCManager;
    private Pres pres;

    @Inject
    public BasePresenter(@NonNull DataManager dataManager,
                         @NonNull Schedule schedule,
                         @NonNull CompositeDisposable compositeDisposable,
                         FCManager fcManager,
                         FCAuthConnect FCAuthConnect,
                         FCStorageConnect fcStorageConnect,
                         Pres pres) {
        this.mDataManager = dataManager;
        this.mScheduler = schedule;
        this.mCompositeDisposable = compositeDisposable;
        this.mFCAuthConnect = FCAuthConnect;
        this.mFCStorageConnect = fcStorageConnect;
        this.mFCManager =  fcManager;
        this.pres = pres;

    }

    @Override
    public void onAttach(V v) {
        this.v = v;
    }

    @Override
    public void onDetach() {
        v = null;
        mFCAuthConnect = null;
    }

    @Override
    public boolean isAttachView() {
        return v != null;
    }

    @Override
    public V getView() {
        if(v == null){

            return null;
        }
        return v;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public Schedule getScheduler() {
        return mScheduler;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    protected FCAuthConnect getFireBaseAuthConnect() {
        return mFCAuthConnect;
    }

    protected FCStorageConnect getFCStorageConnect(){
        return mFCStorageConnect;
    }

    protected FCManager getFCManager(){
        return mFCManager;
    }
    protected Pres getSharePreference(){
        return pres;
    }

}
