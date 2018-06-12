package com.example.nguyephan.friendapp.ui.main;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.nguyephan.friendapp.R;
import com.example.nguyephan.friendapp.di.Component.AcComponent;
import com.example.nguyephan.friendapp.ui.base.BaseAc;
import com.example.nguyephan.friendapp.ui.chat.ChatAc;
import com.example.nguyephan.friendapp.ui.login.LoginFr;
import com.example.nguyephan.friendapp.ui.login.RegisterFr;
import com.example.nguyephan.friendapp.util.navigation.Navigation;
import com.example.nguyephan.friendapp.util.page.Page;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAc extends BaseAc implements MainContract.View,LoginFr.OnLoginFrListener,RegisterFr.OnRegisterFrListener,LifecycleOwner {

    private static final String TAG = "MainAc";
    @BindView(R.id.continer)
    FrameLayout mContainer;

    @Inject
    public MainContract.Presenter<MainContract.View> presenter;
    @Inject
    public LoginFr mLoginFr;
    @Inject
    public RegisterFr mRegisterFr;
    @Inject
    public Navigation mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        MainModel mainModel = ViewModelProviders.of(this).get(MainModel.class);

        if(mainModel.getPresenter() == null){
            mainModel.setPresenter(presenter);
        }else {
            presenter = mainModel.getPresenter();
        }
        presenter.onAttach(this);
        presenter.checkUserLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoginFr.setBtnRegisterClick(this);
        mRegisterFr.setRegisterFrListener(this);
    }

    @Override
    public void build(AcComponent acComponent) {
        acComponent.inject(this);
    }


    @Override
    public void replaceLoginPage() {
        Fragment login = getSupportFragmentManager().findFragmentByTag(Page.Login.getTag());
        Fragment register = getSupportFragmentManager().findFragmentByTag(Page.Register.getTag());
        if(login != null){
            if(register != null ){
                Log.e(TAG,"register not null");
                replaceRegisterPage();
            }
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(mContainer.getId(), mLoginFr,Page.Login.getTag());
        transaction.commit();
    }

    @Override
    public void replaceChatPage() {
        mNavigation.startChatAc(null,null);
        finish();
    }

    @Override
    public void replaceRegisterPage() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment register = getSupportFragmentManager().findFragmentByTag(Page.Register.getTag());
        if(register != null){
            return;
        }
        transaction.setCustomAnimations(R.anim.face_in,R.anim.face_out);
        transaction.replace(mContainer.getId(), mRegisterFr,Page.Register.getTag());
        transaction.addToBackStack(Page.Register.getTag());
        transaction.commit();
    }

    @Override
    public void onLoginNewAccListener() {
        Fragment register = getSupportFragmentManager().findFragmentByTag(Page.Register.getTag());
        if(register != null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(mContainer.getId(), register);
            transaction.commit();
        }else {
            replaceRegisterPage();
        }
    }

    @Override
    public void closeRegisterPage() {
        Fragment login = getSupportFragmentManager().findFragmentByTag(Page.Login.getTag());
        if(login != null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(mContainer.getId(), login);
            transaction.commit();
        }else {
            replaceLoginPage();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void checkFragment(){

    }
}
