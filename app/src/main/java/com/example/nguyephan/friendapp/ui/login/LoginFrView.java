package com.example.nguyephan.friendapp.ui.login;

import android.support.annotation.NonNull;

import com.example.nguyephan.friendapp.data.pojo.firebase.FireRequestAuth;
import com.example.nguyephan.friendapp.ui.base.BaseContract;

/**
 * Created by nguye phan on 5/11/2018.
 */

public interface LoginFrView extends BaseContract.View{

    void showChatPage();

    void showErrorEmail(@NonNull String error);

    void showErrorPassword(@NonNull String error);

    FireRequestAuth getFirebaseRequest();


}
