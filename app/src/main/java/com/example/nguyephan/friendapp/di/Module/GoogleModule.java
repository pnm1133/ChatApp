package com.example.nguyephan.friendapp.di.Module;

import com.example.nguyephan.friendapp.data.api.firebase.FCAuthConnect;
import com.example.nguyephan.friendapp.data.api.firebase.FCAuthImp;
import com.example.nguyephan.friendapp.data.api.firebase.FCFirestoreConnect;
import com.example.nguyephan.friendapp.data.api.firebase.FCFirestoreImp;
import com.example.nguyephan.friendapp.data.api.firebase.FCStorageConnect;
import com.example.nguyephan.friendapp.data.api.firebase.FCStorageConnectImp;
import com.example.nguyephan.friendapp.di.AcScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nguye phan on 5/11/2018.
 */
@Module
public class GoogleModule {

    @Provides
    @AcScope
    FCAuthConnect provideFireBaseConnectAuth(FCAuthImp fireBaseConnectAuthApp){
        return fireBaseConnectAuthApp;
    }

    @Provides
    @AcScope
    FCFirestoreConnect provideFCFirestoreConnect(FCFirestoreImp mCFirestoreImp){
        return mCFirestoreImp;
    }

    @Provides
    @AcScope
    FCStorageConnect provideFCStorageConnect(FCStorageConnectImp mFCStorageConnect){
        return mFCStorageConnect;
    }

}
