package com.example.nguyephan.friendapp.data.repository.prefs;

import android.content.Context;

import com.example.nguyephan.friendapp.di.AppContext;

import javax.inject.Inject;

/**
 * Created by nguye phan on 5/9/2018.
 */

public class PreferenceApp implements Preferences{

    @Inject
    public PreferenceApp(@AppContext Context context) {

    }
}
