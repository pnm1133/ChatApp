package com.example.nguyephan.friendapp.ui.login;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.nguyephan.friendapp.ui.base.BaseContract;

import java.io.IOException;

/**
 * Created by nguye phan on 5/13/2018.
 */

public interface RegisterFrView extends BaseContract.View {

    void showErrorEmail(@NonNull String error);

    void showErrorUnknown(String error);

    void showErrorPassword(@NonNull String error);

    void showErrorName(@NonNull String error);

    void setAvatar() throws IOException;

    void getStarted();

    void showChatPage();

    void startUploadService();

    void startSaveInforUser();
    
}
