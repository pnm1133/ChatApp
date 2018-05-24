package com.example.nguyephan.friendapp.ui.base;

import android.app.Activity;
import android.content.Context;
import android.hardware.input.InputManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.nguyephan.friendapp.FriendApp;
import com.example.nguyephan.friendapp.R;
import com.example.nguyephan.friendapp.di.Component.AcComponent;
import com.example.nguyephan.friendapp.di.Component.DaggerAcComponent;
import com.example.nguyephan.friendapp.di.Component.DaggerAppComponent;
import com.example.nguyephan.friendapp.di.Module.AcModule;
import com.example.nguyephan.friendapp.util.dilog.LoadDialog;
import com.example.nguyephan.friendapp.util.page.Page;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by nguye phan on 5/9/2018.
 */

public abstract class BaseAc extends AppCompatActivity implements BaseContract.View {

    private AcComponent mAcComponent;

    private LoadDialog loadDialog;

    private static final String TAG = "BaseAc";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAcComponent = DaggerAcComponent.builder()
                .appComponent(FriendApp.appComponent())
                .acModule(new AcModule(this))
                .build();

         build(mAcComponent);

    }

    public AcComponent getAcComponent(){
        return mAcComponent;
    }

    @Override
    public void errorMessage(String string) {
        Toast.makeText(getBaseContext(), string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
        }

        return false;
    }

    @Override
    public void errorInternetConnection() {

    }

    @Override
    public void showKeyboard() {
        AndroidSchedulers
                .mainThread()
                .scheduleDirect(() -> {
                    View view = getCurrentFocus();
                    InputMethodManager inputService = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    if (inputService != null) {
                        inputService.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                    }
                }, 500, TimeUnit.MILLISECONDS);

    }

    @Override
    public void showLoadDialog() {
        loadDialog = LoadDialog.newInstance();
        loadDialog.show(getSupportFragmentManager(), Page.LoadDialog.getTag());
        loadDialog.setCancelable(false);

    }

    @Override
    public void hideLoadDialog() {
        if(loadDialog != null && !loadDialog.isHidden()){
            loadDialog.dismiss();
        }
    }

    public abstract void build(AcComponent acComponent);

}
