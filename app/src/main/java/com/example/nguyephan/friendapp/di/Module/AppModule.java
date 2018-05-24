package com.example.nguyephan.friendapp.di.Module;

import android.app.Application;
import android.content.Context;

import com.example.nguyephan.friendapp.di.AppContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nguye phan on 5/9/2018.
 */
@Module
public class AppModule {
    private Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }

    @Provides
    @AppContext
    Context providesContext(){
        return mApplication;
    }

}
