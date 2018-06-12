package com.example.nguyephan.friendapp.ui.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.example.nguyephan.friendapp.data.api.firebase.FCAuthConnect;
import com.example.nguyephan.friendapp.data.api.firebase.FCAuthImp;
import com.example.nguyephan.friendapp.data.api.firebase.FCManager;
import com.example.nguyephan.friendapp.data.api.firebase.FCStorageConnect;
import com.example.nguyephan.friendapp.data.pojo.firebase.FireResponse;
import com.example.nguyephan.friendapp.data.pojo.firebase.FireRequest;
import com.example.nguyephan.friendapp.data.repository.DataManager;
import com.example.nguyephan.friendapp.data.repository.Pres;
import com.example.nguyephan.friendapp.ui.base.BasePresenter;
import com.example.nguyephan.friendapp.ui.login.LoginFrView;
import com.example.nguyephan.friendapp.ui.login.RegisterFrView;
import com.example.nguyephan.friendapp.util.TextConstrant.TextConstant;
import com.example.nguyephan.friendapp.util.page.Page;
import com.example.nguyephan.friendapp.util.scheduler.Schedule;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by nguye phan on 5/9/2018.
 */

public class MainPresenter<V extends MainContract.View> extends BasePresenter<V>
        implements MainContract.Presenter<V>, FCAuthImp.ListenerFirebase {

    private static final String TAG = "MainPresenter";
    private LoginFrView mLoginFrView;
    private RegisterFrView mRegisterFrView;

    @Inject
    public MainPresenter(@NonNull DataManager dataManager,
                         @NonNull Schedule schedule,
                         @NonNull CompositeDisposable compositeDisposable,
                         @Nullable FCAuthConnect fcAuthConnect,
                         FCManager fcManager,
                         FCStorageConnect fcStorageConnect,
                         @Nullable LoginFrView loginFrView,
                         @Nullable RegisterFrView registerFrFr,
                         Pres pres) {
        super(dataManager, schedule, compositeDisposable, fcManager, fcAuthConnect, fcStorageConnect, pres);
        this.mLoginFrView = loginFrView;
        this.mRegisterFrView = registerFrFr;
    }

    @Override
    public void checkUserLogin() {
        if (getFireBaseAuthConnect().isCurrentUser()) {
            saveTokenSession(Page.ManAc);
        } else {
            getView().replaceLoginPage();
        }
    }

    @Override
    public void startLogin(FireRequest fireRequest) {
        if (checkFillInformation(fireRequest, Page.Login)) {
            return;
        }
        if (getLoginView().isInternetConnection()) {
            getLoginView().showLoadDialog();
            getFireBaseAuthConnect().signIn(fireRequest);
            getFireBaseAuthConnect().setListenerFirebase(this);
        } else {
            getLoginView().errorInternetConnection();
        }
    }

    @Override
    public void cacheUser() {

    }

    @Override
    public void startRegister(FireRequest fireRequest) {
        if (checkFillInformation(fireRequest, Page.Register)) {
            return;
        }
        if (getRegisterFrView().isInternetConnection()) {
            getRegisterFrView().showLoadDialog();
            getFireBaseAuthConnect().newAccCount(fireRequest)
                    .subscribe(new Observer<FireResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(FireResponse fireResponse) {
                            String result = fireResponse.getMessage();
                            if (TextUtils.equals(result, TextConstant.RESPONSE_OK)) {
                                // update information user when create successed
                                getFireBaseAuthConnect().updateUser(fireRequest);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            showErrorWhenCreateUser(e.toString(), Page.Register);
                        }

                        @Override
                        public void onComplete() {
                            Log.e(TAG, "onComplete");
                            getRegisterFrView().startUploadService();
                            saveTokenSession(Page.Register);
                        }
                    });

        } else {
            getRegisterFrView().errorInternetConnection();
        }
    }

    @Override
    public void saveTokenSession(Page page) {
        getCompositeDisposable().add(
                getSharePreference()
                        .saveToken()
                        .doOnError(throwable -> Log.e(TAG, throwable.toString()))
                        .doOnComplete(() -> {
                            Log.d(TAG, "complete save token");
                        })
                        .doOnNext(aBoolean -> {
                            if (aBoolean) {
                                switch (page) {
                                    case Register:
                                        getRegisterFrView().showChatPage();
                                        break;
                                    case Login:
                                        getLoginView().showChatPage();
                                        break;
                                    case ManAc:
                                        getView().replaceChatPage();
                                        break;
                                }
                            }
                        })
                        .subscribe()
        );
    }

    @Override
    public void listenResponse(FireResponse response) {
        showResponse(response, Page.Login);
        showResponse(response, Page.Register);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private boolean checkFillInformation(@NonNull FireRequest fireRequest, Page page) {
        String email = fireRequest.getEmail();
        String password = fireRequest.getPassword();
        String name = fireRequest.getUserName();
        switch (page) {
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
                if (TextUtils.isEmpty(name)) {
                    getRegisterFrView().showErrorName(TextConstant.ERROR_NULL_NAME);
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
                break;
        }
        return false;
    }

    private void showResponse(@NonNull FireResponse fireResponse, Page page) {
        String message = fireResponse.getMessage();
        switch (page) {
            case Login:
                getLoginView().hideLoadDialog();
                if (TextUtils.equals(message, TextConstant.ERROR_NULL_RECORD)) {
                    getLoginView().showErrorEmail(TextConstant.ERROR_NULL_RECORD_VE);
                    return;
                }
                if (TextUtils.equals(message, TextConstant.ERROR_WRONG_INVALID)) {
                    getLoginView().showErrorPassword(TextConstant.ERROR_WRONG_PASSWORD_INVALID_VE);
                    return;
                }
                if (TextUtils.equals(message, TextConstant.RESPONSE_OK)) {
                    saveTokenSession(Page.Login);
                }
                break;
            case Register:
                Log.e(TAG, fireResponse.getMessage());
                break;
        }
    }

    private void showErrorWhenCreateUser(String error, Page page) {
        switch (page) {
            case Login:
                break;
            case Register:
                getRegisterFrView().hideLoadDialog();
                if (TextUtils.equals(error, TextConstant.ERROR_EMAIL_REGISTER)) {
                    getRegisterFrView().showErrorEmail(TextConstant.ERROR_EMAIL_REGISTER_VIE);
                } else {
                    getRegisterFrView().showErrorUnknown(error);
                }
                break;
        }
    }


    private LoginFrView getLoginView() {
        return mLoginFrView;
    }

    private RegisterFrView getRegisterFrView() {
        return mRegisterFrView;
    }
}
