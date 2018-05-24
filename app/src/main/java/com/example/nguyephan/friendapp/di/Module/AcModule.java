package com.example.nguyephan.friendapp.di.Module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.example.nguyephan.friendapp.di.AcContext;
import com.example.nguyephan.friendapp.di.AcScope;
import com.example.nguyephan.friendapp.ui.main.MainContract;
import com.example.nguyephan.friendapp.ui.main.MainPresenter;
import com.example.nguyephan.friendapp.util.scheduler.Schedule;
import com.example.nguyephan.friendapp.util.scheduler.ScheduleApp;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by nguye phan on 5/9/2018.
 */
@Module
public class AcModule {
    private AppCompatActivity mAppCompatActivity;

    public AcModule(AppCompatActivity activity) {
        this.mAppCompatActivity = activity;
    }


    @Provides
    @AcScope
    @AcContext
    Context providesContext(){
        return mAppCompatActivity;
    }

    @Provides
    @AcScope
    CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }

    @Provides
    @AcScope
    Schedule provideSchedule(){
        return new ScheduleApp();
    }

    @Provides
    @AcScope
    MainContract.Presenter<MainContract.View> provideMainPresenter(MainPresenter<MainContract.View> presenter){
        return presenter;
    }

}
