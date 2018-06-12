package com.example.nguyephan.friendapp.di.Module;

import com.example.nguyephan.friendapp.data.api.firebase.FCStorageConnect;
import com.example.nguyephan.friendapp.data.api.firebase.FCStorageConnectImp;
import com.example.nguyephan.friendapp.di.ServiceScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceMoudle {

    @Provides
    @ServiceScope
    FCStorageConnect providesFcStorageConnect(){
        return new FCStorageConnectImp();
    }
}
