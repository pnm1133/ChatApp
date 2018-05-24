package com.example.nguyephan.friendapp.di.Module;

import com.example.nguyephan.friendapp.data.repository.DataManager;
import com.example.nguyephan.friendapp.data.repository.DataManagerApp;
import com.example.nguyephan.friendapp.data.repository.prefs.PreferenceApp;
import com.example.nguyephan.friendapp.data.repository.prefs.Preferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nguye phan on 5/9/2018.
 */
@Module
public class RepositoryModule {


    @Provides
    @Singleton
    Preferences providesPreference(PreferenceApp preferenceApp){
        return preferenceApp;
    }

    @Provides
    @Singleton
    DataManager providesDataManager(DataManagerApp dataManagerApp){
        return dataManagerApp;
    }
}
