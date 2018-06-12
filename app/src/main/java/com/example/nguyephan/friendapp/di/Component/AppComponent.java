package com.example.nguyephan.friendapp.di.Component;

import android.content.Context;

import com.example.nguyephan.friendapp.data.repository.DataManager;
import com.example.nguyephan.friendapp.data.repository.Pres;
import com.example.nguyephan.friendapp.di.AppContext;
import com.example.nguyephan.friendapp.di.Module.ApiModule;
import com.example.nguyephan.friendapp.di.Module.AppModule;
import com.example.nguyephan.friendapp.di.Module.RepositoryModule;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nguye phan on 5/9/2018.
 */
@Singleton
@Component(modules = {AppModule.class, ApiModule.class, RepositoryModule.class})
public interface AppComponent {

    @AppContext
    Context getAppContext();

    DataManager getDataManager();


    FirebaseJobDispatcher getFirebaseDispatcher();

    Pres getPres();

}
