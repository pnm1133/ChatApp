package com.example.nguyephan.friendapp.data.repository;

import android.content.Context;

import com.example.nguyephan.friendapp.data.repository.DataManager;
import com.example.nguyephan.friendapp.data.repository.prefs.Preferences;
import com.example.nguyephan.friendapp.di.AppContext;

import javax.inject.Inject;

/**
 * Created by nguye phan on 5/9/2018.
 */

public class DataManagerApp implements DataManager {

    @Inject
    public DataManagerApp(Preferences preferences) {

    }
}
