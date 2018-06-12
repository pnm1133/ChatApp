package com.example.nguyephan.friendapp.di.Module;

import android.app.Application;
import android.content.Context;

import com.example.nguyephan.friendapp.data.repository.Pres;
import com.example.nguyephan.friendapp.data.repository.PresLocalIMP;
import com.example.nguyephan.friendapp.di.AppContext;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;

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

    @Provides
    @Singleton
    FirebaseJobDispatcher providesFirebaseJobDispatcher(){
        return new FirebaseJobDispatcher(new GooglePlayDriver(mApplication));
    }

}
