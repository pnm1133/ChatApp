package com.example.nguyephan.friendapp;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.nguyephan.friendapp.di.Component.AppComponent;

import com.example.nguyephan.friendapp.di.Component.DaggerAppComponent;
import com.example.nguyephan.friendapp.di.Module.ApiModule;
import com.example.nguyephan.friendapp.di.Module.AppModule;
import com.example.nguyephan.friendapp.di.Module.RepositoryModule;

/**
 * Created by nguye phan on 5/9/2018.
 */

public class FriendApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private static AppComponent mAppComponent;

    public static AppComponent appComponent(){
        return mAppComponent;
    }

    public FriendApp() {
        mAppComponent = buildAppComponent();
    }

    private AppComponent buildAppComponent(){
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .repositoryModule(new RepositoryModule())
                .apiModule(new ApiModule())
                .build();
    }

}
