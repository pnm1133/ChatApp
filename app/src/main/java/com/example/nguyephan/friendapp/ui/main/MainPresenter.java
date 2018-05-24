package com.example.nguyephan.friendapp.ui.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.example.nguyephan.friendapp.data.api.firebase.FCAuthConnect;
import com.example.nguyephan.friendapp.data.api.firebase.FCAuthImp;
import com.example.nguyephan.friendapp.data.pojo.firebase.FireResponse;
import com.example.nguyephan.friendapp.data.pojo.firebase.FireRequestAuth;
import com.example.nguyephan.friendapp.data.repository.DataManager;
import com.example.nguyephan.friendapp.ui.base.BasePresenter;
import com.example.nguyephan.friendapp.ui.login.LoginFrView;
import com.example.nguyephan.friendapp.ui.login.RegisterFrView;
import com.example.nguyephan.friendapp.util.TextConstrant.TextConstant;
import com.example.nguyephan.friendapp.util.page.Page;
import com.example.nguyephan.friendapp.util.scheduler.Schedule;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by nguye phan on 5/9/2018.
 */

public class MainPresenter<V extends MainContract.View> extends BasePresenter<V>
        implements MainContract.Presenter<V>,FCAuthImp.ListenerFirebase{

    private static final String TAG = "MainPresenter";

    private LoginFrView mLoginFrView;
    
    private RegisterFrView mRegisterFrView;

    @Inject
    public MainPresenter(@NonNull DataManager dataManager,
                         @NonNull Schedule schedule,
                         @NonNull CompositeDisposable compositeDisposable,
                         @Nullable FCAuthConnect FCAuthConnect,
                         @Nullable LoginFrView loginFrView, 
                         @Nullable RegisterFrView registerFrFr) {
        super(dataManager, schedule, compositeDisposable, FCAuthConnect);

        this.mLoginFrView = loginFrView;
        this.mRegisterFrView = registerFrFr;
    }

    @Override
    public void checkUserLogin() {
        if (getFireBaseAuthConnect().isCurrentUser()) {
            getView().replaceStartPage();
        } else {
            getView().replaceLoginPage();
        }
    }

    @Override
    public void startLogin(FireRequestAuth fireRequestAuth) {
        if (checkFillInformation(fireRequestAuth,Page.Login)) {
            return;
        }
        if (getLoginView().isInternetConnection()) {
            getLoginView().showLoadDialog();
            getFireBaseAuthConnect().signIn(fireRequestAuth);
            getFireBaseAuthConnect().setListenerFirebase(this);
        } else {
            getLoginView().errorInternetConnection();
        }
    }

    @Override
    public void cacheUser() {

    }

    @Override
    public void startRegister(FireRequestAuth fireRequestAuth) {
        if (checkFillInformation(fireRequestAuth, Page.Register)) {
            return;
        }
        if(getRegisterFrView().isInternetConnection()){
            getRegisterFrView().showLoadDialog();
            getFireBaseAuthConnect().newAccCount(fireRequestAuth);
            getFireBaseAuthConnect().setListenerFirebase(this);
        }else {
            getRegisterFrView().errorInternetConnection();
        }
    }

    @Override
    public void listenResponse(FireResponse response) {
        showResponse(response,Page.Login);
        showResponse(response,Page.Register);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private boolean checkFillInformation(@NonNull FireRequestAuth fireRequestAuth, Page page) {
        Log.e(TAG,"startLogin");
        String email = fireRequestAuth.getEmail();
        String password = fireRequestAuth.getPassword();
        String name = fireRequestAuth.getUserName();

        switch (page){
            case Login:
                if (TextUtils.isEmpty(email)) {
                    getLoginView().showErrorEmail(TextConstant.ERROR_NULL_EMAIL);
                    return true;
                }

                if (!TextConstant.regexEmail(email)) {
                    getLoginView().showErrorEmail(TextConstant.ERROR_EMAIL_BADLY_FORMAT);
                    return true;
                }

                if (TextUtils.isEmpty(password)) {
                    getLoginView().showErrorPassword(TextConstant.ERROR_NULL_PASSWORD);
                    return true;
                }

                if (!TextConstant.regexPassword(password)) {
                    getLoginView().showErrorPassword(TextConstant.ERROR_PASSWORD_BADLY_FORMAT);
                    return true;
                }

                break;
            case Register:

                if (TextUtils.isEmpty(email)) {
                    getRegisterFrView().showErrorEmail(TextConstant.ERROR_NULL_EMAIL);
                    return true;
                }

                if (!TextConstant.regexEmail(email)) {
                    getRegisterFrView().showErrorEmail(TextConstant.ERROR_EMAIL_BADLY_FORMAT);
                    return true;
                }

                if (TextUtils.isEmpty(password)) {
                    getRegisterFrView().showErrorPassword(TextConstant.ERROR_NULL_PASSWORD);
                    return true;
                }

                if (!TextConstant.regexPassword(password)) {
                    getRegisterFrView().showErrorPassword(TextConstant.ERROR_PASSWORD_BADLY_FORMAT);
                    return true;
                }

                if(TextUtils.isEmpty(name)){
                    getRegisterFrView().showErrorName(TextConstant.ERROR_NULL_NAME);
                    return true;
                }

                break;
        }

        return false;

    }

    private void showResponse(@NonNull FireResponse fireResponse, Page page) {

        String message = fireResponse.getMessage();

        getLoginView().hideLoadDialog();

        switch (page){
            case Login:
                if (TextUtils.equals(message, TextConstant.ERROR_NULL_RECORD)) {
                    getLoginView().showErrorEmail(TextConstant.ERROR_NULL_RECORD_VE);
                    return;
                }

                if (TextUtils.equals(message, TextConstant.ERROR_WRONG_INVALID)) {
                    getLoginView().showErrorPassword(TextConstant.ERROR_WRONG_PASSWORD_INVALID_VE);
                    return;
                }

                if (TextUtils.equals(message, TextConstant.RESPONSE_OK)) {
                    getLoginView().showChatPage();
                }
                break;
            case Register:
                Log.e(TAG, fireResponse.getMessage());
                break;
        }

    }

    private LoginFrView getLoginView() {
        return mLoginFrView;
    }

    private RegisterFrView getRegisterFrView(){
        return mRegisterFrView;
    }
}
